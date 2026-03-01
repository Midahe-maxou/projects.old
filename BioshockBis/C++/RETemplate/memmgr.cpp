#include "memmgr.h"

#include <iostream>
#include <vector>
#include <array>
#include <stdexcept>
#include <list>

#include <memoryapi.h>
#include <Windows.h>
#include <sysinfoapi.h> // GetSystemInfo


DWORD pageSize = 0;
int nbInt64ForPage = 0;

void memmgrInit()
{
	SYSTEM_INFO sysInfos = SYSTEM_INFO();
	GetSystemInfo(&sysInfos);
	pageSize = sysInfos.dwPageSize;
	nbInt64ForPage = pageSize / bsizeof(int64_t);
}



MultiUInt64 MultiUInt64::truncate() const noexcept
{
	return MultiUInt64();
}


// TODO: remove
#include <iostream>
void MultiUInt64::PrintMemoryRepresentation()
{
	for (auto val : this->m_int)
		std::cout << bin(val) << " ";
}

bool MultiUInt64::operator==(const MultiUInt64& other) const noexcept
{
	MultiUInt64 largeUInt = (size() > other.size()) ? *this : other;
	MultiUInt64 smallUInt = (size() > other.size()) ? other : *this;

	size_t largeSize = largeUInt.size();
	size_t smallSize = smallUInt.size();
	size_t diff = largeSize - smallSize;

	// Check if the larger have all additionnal data set to 0.
	for (UINT i = 0; i <= diff; i++)
		if (largeUInt.m_int[i] != 0) return false;

	// Check for equality
	for (UINT i = 0; i < smallSize; i++)
		if (largeUInt.m_int[i + diff] != smallUInt.m_int[i]) return false;

	return true;
}

MultiUInt64 MultiUInt64::operator<<(UINT shift) const noexcept
{
	MultiUInt64 result = *this;
	if (!size()) return result;

	int nbMovedChunk = shift / 64U;
	int offset = shift % 64U;

	if (nbMovedChunk >= (int)size()) // shift so high everything's 0.
	{
		result.m_int.assign(size(), 0);
		return result;
	}

	if (nbMovedChunk)
	{
		for (int i = nbMovedChunk; i < (int)size(); i++)
			result.m_int[i - nbMovedChunk] = result.m_int[i]; // Move chunks by nbMovedChunk to the left.

		for (int i = 1; i <= nbMovedChunk; i++)
			result.m_int[size() - i] = 0;
	}

	uint64_t temp = 0;
	result.m_int[0] <<= offset;
	for (UINT i = 1; i < size(); i++)
	{
		temp = result.m_int[i];
		result.m_int[i] <<= offset;
		temp >>= bsizeof(uint64_t) - offset;
		result.m_int[i - 1] |= temp;
	}
	return result;
}

MultiUInt64& MultiUInt64::operator<<=(UINT shift) noexcept
{
	*this = *this << shift;
	return *this;
}


MultiUInt64 MultiUInt64::operator>>(UINT shift) const noexcept
{
	MultiUInt64 result = *this;
	if (!size()) return result;

	UINT nbMovedChunk = shift / 64U;
	UINT offset = shift % 64U;

	if (nbMovedChunk >= size()) // shift so high everything's 0.
	{
		result.m_int.assign(size(), 0);
		return result;
	}

	if (nbMovedChunk)
	{
		for (UINT i = size() - 1; i > nbMovedChunk; i--)
			result.m_int[i] = result.m_int[i - nbMovedChunk]; // Move chunks by nbMovedChunk to the right.

		for (UINT i = 0; i <= nbMovedChunk; i++)
			result.m_int[i] = 0;
	}

	uint64_t temp = 0;
	result.m_int[size() - 1] >>= offset;
	for (int i = size() - 2; i >= 0; i--)
	{
		temp = result.m_int[i];
		result.m_int[i] >>= offset;
		temp <<= bsizeof(uint64_t) - offset;
		result.m_int[i + 1] |= temp;
	}
	return result;
}

MultiUInt64& MultiUInt64::operator>>=(UINT shift) noexcept
{
	*this = *this >> shift;
	return *this;
}

uint64_t& MultiUInt64::operator[](UINT index) noexcept
{
	return m_int[index];
}

MultiUInt64 MultiUInt64::operator|(const MultiUInt64& other) const noexcept
{
	MultiUInt64 largeUInt = (size() > other.size()) ? *this : other;
	MultiUInt64 smallUInt = (largeUInt == *this) ? other : *this;

	size_t largeSize = largeUInt.size();
	size_t smallSize = smallUInt.size();
	size_t diff = largeSize - smallSize;

	for (UINT i = 0; i < smallSize; i++)
		largeUInt.m_int[i + diff] |= smallUInt.m_int[i];
	return largeUInt;
}

MultiUInt64& MultiUInt64::operator|=(const MultiUInt64& other) noexcept
{
	*this = *this | other;
	return *this;
}

MultiUInt64 MultiUInt64::operator&(const MultiUInt64& other) const noexcept
{
	MultiUInt64 largeUInt = (size() > other.size()) ? *this : other;
	MultiUInt64 smallUInt = (largeUInt == *this) ? other : *this;

	size_t largeSize = largeUInt.size();
	size_t smallSize = smallUInt.size();
	size_t diff = largeSize - smallSize;

	for (UINT i = 0; i < smallSize; i++)
		largeUInt.m_int[i + diff] |= smallUInt.m_int[i];
	return largeUInt;

	for (UINT i = 0; i < size(); i++)
		largeUInt.m_int[i] &= smallUInt.m_int[i];
	return largeUInt;
}

MultiUInt64& MultiUInt64::operator&=(const MultiUInt64& other) noexcept
{
	*this = *this & other;
	return *this;
}


_MemRepr::_MemRepr(_In_ DWORD pageAddress, _In_ HANDLE hProc, _In_opt_ bool debug)
	:m_pageAddress(pageAddress), m_hProc(hProc)
{
	if (!nbInt64ForPage) memmgrInit(); // Init if not already

	if (!pageAddress) throw std::invalid_argument("pageAddress should not be 0x0");
	if (pageAddress % pageSize != 0) throw std::invalid_argument("pageAddress should be the first address of the page");

	m_repr = MultiUInt64(nbInt64ForPage);

	if (!debug) return;

	std::array<BYTE, 64> allocatedData {}; // chunks of 64 bytes data.

	for (int i = 0; i < nbInt64ForPage; i++) {
		ReadProcessMemory(hProc, reinterpret_cast<void*>(pageAddress + i*64), allocatedData.data(), 64, NULL); // Read chunks of 64 bytes to save stack memory.

		for (BYTE byte : allocatedData)
		{
			m_repr[i] <<= 1; // Offset m_repr by 1 bit.
			m_repr[i] |= (BYTE)(bool)byte; // Set the m_repr bit to 0 if the byte is 0, or to 1 otherwise.
		}
	}
}


DWORD _MemRepr::findSuitableMemory(_In_ size_t size, _In_ bool aligned)
{
	MultiUInt64 mask = 1;
	return NULL;
}

bool _MemRepr::reserveMemory(_In_ DWORD address, _In_ size_t size)
{
	DWORD relativeAddress = (DWORD)(m_pageAddress - address);
	if (relativeAddress > pageSize) return false; // If address is before of after (note: 64U - 65U > 64)

	
	/*
	unsigned int chunk = relativeAddress / 64U;
	unsigned offset = relativeAddress % 64U;

	unsigned int nbChunks = size / 64U;
	*/


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

DWORD MemoryManager::reserveMemory(_In_ size_t size, _In_opt_ bool aligned)
{
	if (size > 4095) return (DWORD)0;

	DWORD address;
	for (auto it = std::rbegin(m_allocatedMem); it != std::rend(m_allocatedMem); it++)
	{
		address = it->findSuitableMemory(size, aligned);
		if (address) return address;
	}

	address = reinterpret_cast<DWORD>(VirtualAllocEx(m_hProc, nullptr, 4096, MEM_RESERVE | MEM_COMMIT, PAGE_EXECUTE_READWRITE));
	if (!address) return (DWORD)0;

	m_allocatedMem.push_back({ address, m_hProc });
	return address;
}

DWORD MemoryManager::insertData(_In_ std::vector<BYTE> data, _In_opt_ bool aligned)
{
	return NULL;
}

DWORD MemoryManager::insertData(_In_ BYTE data)
{
	std::vector<BYTE> d = { data };
	return this->insertData(d);
}

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