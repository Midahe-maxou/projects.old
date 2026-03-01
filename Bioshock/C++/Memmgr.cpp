#include "Memmgr.h"

#include <vector>
#include <array>
#include <stdexcept>

#include <memoryapi.h>
#include <Windows.h>


//TODO: remove
#include <iostream>
#include <bitset>
#define bin(val) std::bitset<sizeof(val)*8>(val)

/**
 * @brief Return the number of 0's at the front.
*/
unsigned int CalculateMemOffset(uint64_t mask)
{
	if (mask == 0) return 64U;
	unsigned int zeros = 0;
	while ((mask & 1) == 0x0)
	{
		zeros += 1;
		mask >>= 1;
	}
	return zeros;
}

_MemRepr::_MemRepr(_In_ DWORD address, _In_ HANDLE hProc, _In_opt_ bool debug)
	:m_address(address)
{
	if (!address) throw std::invalid_argument("Address should not be 0x0");
	if (!debug) return;

	std::array<BYTE, 64> allocatedData; // 64 chunks of 64 bytes data.

	for (int i = 0; i < 64; i++) {
		ReadProcessMemory(hProc, reinterpret_cast<void*>(address + i*64), allocatedData.data(), 64, NULL); // Read chunks of 64 bytes to save stack memory.

		for (BYTE byte : allocatedData)
		{
			m_repr[i] <<= 1;
			m_repr[i] |= (BYTE)(byte != 0);
		}
	}
}

/**
 * @brief 
 * 
 * The idea is to consider a bit mask that represents the data : for exemple a 3 bytes-long data is represented as data = 0b1110000000000000.
 * Then compare it with the bit representation m_repr of the memory.
 * For exemple, for a 16-long byte memory, we could have mem = 0b1110000011111111.
 * If we do mem & data:
 * 
 *		1110000011111111
 *	&	1111100000000000
 *	=	1110000000000000
 * 
 * This is not 0 <=> the data cannot be placed. We continue to compare while shifting the mask by 1 bit (or 5 bit if aligned is true).
 * 
 *		1110000011111111
 *	&	0111110000000000
 *	=	0110000000000000
 * 
 * Still not 0.
 * 
 *		1110000011111111
 *	&	0011111000000000
 *	=	0010000000000000
 * 
 * Not 0.
 * 
 *		1110000011111111
 *	&	0001111100000000
 *	=	0000000000000000
 * 
 * All 0's <=> the data can be placed.
 * 
 * For multi-chunk memeory (more than 63 bytes), find the average amount of consecutive empty chucks needed: (0 for 64 <= data size < 96, 1 for 96 <= data size < 160, 2 for 160 <= data size < 224...)
 * The average amount of empty chunk is compliant because 65 bytes-long data rarely need one full chunk, whereas 127 bytes-long data need almost certainly one empty chunk. So cutting the limit at half (96 here) between zero and one chunk needed may get the best result.
 * The mask size = data size modulo 64.
 * Add 64 to the mask size if the data size modulo 32 is even.
 * Then look at the chunk before the first empty chunk, get the number of consecutive 0's at the end, and substract it to the mask size.
 * If mask size > 64 => the data cannot be placed.
 * Then create the mask and puts 1's at the front. AND it to the chunk after the last empty chunk. If it equals 0 <=> the data can be placed.
 * 
 * For exemple, with 8 bytes-long chunk, with data size = 21.
 * the memory could be like: 11100100   00000000   00000000   00000011   4 chunks of 8 bytes.
 *							|________| |________| |________| |________|
 *							    0		   1		  2			 3
 * 
 * 21 bytes memory needs 2 empty chunks:
 * 
 * 
 * The chunks 1-2 suits:
 *  The mask size is (21 % 8) = 5.
 *  2 bytes are available in the chunk 0 => mask size = 5-2 = 3.
 *  So mask = 11100000.
 *						00000011
 *					&	11100000
 *  Chunk 3 & mask  =	00000000
 * 
 *  The data can be placed.
 * 
 *  
 * 
 * @note: due to the data type used (_MemRepr), the code is a little bit more complicated.
 * 
 * @param size		The data size. Maximum allowed: 1024 (16 if aligned).
 * @param aligned	Is the data should be aligned.
 * 
 * @retval DWORD
 * @return the address where the data can be placed. 0 if it cannot be placed.
*/

DWORD _MemRepr::FindSuitableMemory(_In_ size_t size, _In_ bool aligned)
{
	if (size > 1024 or (size > 16 and aligned)) return (DWORD)0; // Yea ik but fck it.
	
	unsigned int nb64chunks = (size < 32) ? 0 : ((size / 32U) -1) >> 1;
	unsigned int extra = size % 64;
	if (((size % 32U) & 1) == 0) extra += 64;

	unsigned int consecutiveEmptyChunks = 0;

	// VERSION 3 hehe.

	for (unsigned int numChunk = 0; numChunk < 64; numChunk++)
	{
		if (m_repr[numChunk] == 0x0)
			consecutiveEmptyChunks += 1;
		else
			consecutiveEmptyChunks = 0;

		uint64_t mask = (1Ui64 << extra) - 1; // Mask of 1's where the data should be placed.

		if (consecutiveEmptyChunks >= nb64chunks)
		{
			unsigned int firstEmptyChunk = (consecutiveEmptyChunks == 0) ? numChunk : numChunk +1 - consecutiveEmptyChunks; // +1 because numChunk begins at 0 whereas consecutiveEmptyChunks begins at 1.
			unsigned int remainingData = extra;

			if (firstEmptyChunk != 0) // Avoid SIGSEGV.
			{
				unsigned int freeData = CalculateMemOffset(m_repr[firstEmptyChunk - 1]);
				if (freeData >= remainingData) // The data can be placed.
					return (DWORD)(m_address + firstEmptyChunk * 64 - freeData);

				remainingData -= freeData;
				mask = (1Ui64 << remainingData) - 1;
			}

			if (nb64chunks == 0)
			{
				mask <<= (64 - remainingData); // Place the bit at the front.
				if ((mask & m_repr[numChunk]) == 0x0)
				{
					return (DWORD)(m_address + numChunk * 64 - (extra - remainingData)); // Note: extra - remainingData = freeData.
				}
				else
				{
					mask = (1Ui64 << extra) - 1; // Recreate the mask.
					mask <<= (64 - remainingData); // Place the bit at the front.
					unsigned int step = (aligned ? extra : 1);
					for (unsigned int offset = 1; offset < 64; offset += step)
					{
						if ((m_repr[numChunk] & mask) == 0x0) // If the data can be placed somewhere in the chunk (the somewhere is offset).
							return (DWORD)(m_address + 64 * numChunk + offset); // Return the address that can hold the data.
						mask >>= step;
					}
				}
				continue;
			}

			if (numChunk != 63)
			{
				mask <<= (64 - remainingData); // Place the bit at the front.
				if ((m_repr[numChunk + 1] & mask) == 0x0)
				{
					return (DWORD)(m_address + firstEmptyChunk * 64 - (extra - remainingData)); // Note: (extra - remainingData) = freeData.
				}
			}
		}
	}
	return (DWORD)0; // The data cannot be placed.
	
	/** VERSION 2
	
	if (nb64chunks == 0) // The data is less than 64 bytes.
	{
		for (unsigned int numChunk = 0; numChunk < 64; numChunk++) {
			uint64_t mask = (1i64 << size) - 1; // Create a bit mask of 1's where the data should be placed.
			mask <<= (64 - size); // Place the bit in front.
			unsigned int step = aligned ? size : 1;
			for (unsigned int offset = 0; offset < 64; offset += step)
			{
				if ((m_repr[numChunk] & mask) == 0x0) // If the data can be placed somewhere in the chunk (the somewhere is offset).
					return (DWORD)(m_address + 64 * numChunk + offset); // return the address that can hold the data.
				mask >>= step;
			}
		}
	}
	else
	{
		unsigned int extra = size % 64;
		unsigned int consecutiveEmptyChunks = 0; // Needs to be nb64chunks-1 to place the data.

		for (unsigned int i = 0; i < 64; i++)
		{
			if (consecutiveEmptyChunks == nb64chunks) // Enough empty chunk to place de data. Note: there will never be more consecutiveEmptyChunk than nb64Chunk.
			{
				unsigned int firstChunk = i - consecutiveEmptyChunks; // First empty chunk. Last chunk = i.
				if (firstChunk == 0) // Avoid SIGSEGV.
				{
					unsigned int mask = (1Ui64 << extra) - 1; // Create a bit mask of 1's where the data should be placed.
					mask <<= (64 - extra); // Place the 1's at the front.
					
					if ((m_repr[i + 1] & mask) == 0x0) // If the chunk after the last empty can hold the remaining data.
					{
						return (DWORD)(m_address); // As the first chunk can hold the data, returns only the base address.
					}
				}
				else if (i == 63) // Avoid SIGSEGV.
				{
					unsigned int mask = (1Ui64 << extra) - 1; // Create a bit mask of 1's where the data should be placed.
					if ((m_repr[firstChunk - 1] & mask) == 0x0)
					{
						return (DWORD)(m_address + (firstChunk * 64) - extra); // The data can be placed at (firstChunk - extra), in the chunk right before firstChunk.
					}
				}
				else
				{
					if (extra == 0) return (DWORD)(m_address + firstChunk*64);

					unsigned int mask = (1Ui64 << extra) - 1; // Create a bit mask of 1's where the data should be placed.
					
					mask &= m_repr[firstChunk - 1];
					if (mask == 0) return (DWORD)(m_address + firstChunk*64 - extra);

					unsigned int availableConsecutiveBits = 0; // Number of bits that can hold data before the first mask.
					while ((mask & 1) == 0x0) // Count the number of consecutive 0's at the end of the mask 
					{
						availableConsecutiveBits += 1;
						mask >>= 1;
						break;
					}
					unsigned int remainingBits = extra - availableConsecutiveBits;
					mask = (1Ui64 << remainingBits) - 1; // Reset the mask accordingly to the remaining data that have to be placed a the i+1 chunk.
					mask <<= (64 - remainingBits); // Place the mask at the front.
					if ((m_repr[i + 1] & mask))
					{
						return (DWORD)(m_address + firstChunk*64 - availableConsecutiveBits); // The data can be placed at (firstChunk - availableConsecutiveBits), in the chunk right before firstChunk.
					}
				}
			}

			if (m_repr[i] == 0x0)
				consecutiveEmptyChunks += 1;
			else
				consecutiveEmptyChunks = 0;

			/* VERSION 1
			if (m_repr[i] == 0) // It is a 64 bits wide empty data
			{
				if (i == 0) // Avoid SIGSEGV
				{
					unsigned int mask = (1i64 << extra) - 1;
					mask <<= (64 - extra); // Place the bit in front.
					if ((m_repr[i + 1] & mask) == 0x0) // The data can be placed.
					{
						return (DWORD)(m_address + 64 * i);
					}
				}

				if (i == 63) // Avoid SIGSEGV
				{
					unsigned int mask = (1i64 << extra) - 1;
					if ((m_repr[i - 1] & mask) == 0x0) // The data can be placed.
					{
						return (DWORD)(m_address + 64 * i - extra);
					}
				}
				else
				{

				}

				if (i != 0) // Avoid SIGSEGV
				{
					unsigned int mask = (1i64 << extra) - 1;
					if ((m_repr[i - 1] & mask) == 0x0) // The data can be placed.
					{
						return (DWORD)(m_address + 64 * i - extra);
					}
				}
				if (i != 63) // Avoid SIGSEGV
				{
					unsigned int mask = (1i64 << extra) - 1;
					mask <<= (64 - extra); // Place the bit in front.
					if ((m_repr[i - 1] & mask) == 0x0) // The data can be placed. (also: i'm right and u'r wrong!)
					{
						return (DWORD)(m_address + 64 * i);
					}
				}
			}
		}
	}*/
}

bool _MemRepr::ReserveMemory(_In_ DWORD address, _In_ size_t size)
{
	DWORD relativeAddress = (DWORD)(m_address - address);
	if (relativeAddress > 4095) return false; // If address is before of after (note: 64U - 65U > 64)

	unsigned int chunk = relativeAddress / 64U;
	unsigned offset = relativeAddress % 64U;

	unsigned int nbChunks = size / 64U;


	return true;
}

void _MemRepr::PrintMemoryRepresentation()
{
	for (int i = 0; i < 64; i++)
	{
		std::cout << std::bitset<64>(m_repr[i]);
	}
}


MemoryManager::MemoryManager(HANDLE hProc)
	:m_hProc(hProc)
{}

DWORD MemoryManager::insertData(_In_ std::vector<BYTE> data, _In_opt_ bool aligned)
{
	size_t size = data.size();
	if (size > 4095) return (DWORD)0;
	DWORD address;
	for(auto it = std::rbegin(m_allocatedMem); it != std::rend(m_allocatedMem); it++)
	{
		address = it->FindSuitableMemory(size, aligned);
		if (address) return address;
	}

	address = reinterpret_cast<DWORD>(VirtualAllocEx(m_hProc, nullptr, 4096, MEM_RESERVE | MEM_COMMIT, PAGE_EXECUTE_READWRITE));
	if (!address) return (DWORD)0;

	m_allocatedMem.push_back({ address, m_hProc });
	return address;
}

DWORD MemoryManager::insertData(_In_ BYTE data)
{
	std::vector<BYTE> d = { data };
	return this->insertData(d);
}