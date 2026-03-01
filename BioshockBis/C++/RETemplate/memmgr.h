#pragma once
#ifndef MEMMGR_H
#define MEMMGR_H

#include <vector>
#include <list> // Double-linked list
#include <array>

#include <Windows.h>


#define bsizeof(val) sizeof(val)*8
#include <bitset>
#define bin(val) std::bitset<sizeof(val)*8>(val)



void memmgrInit(); // Must be called first.
unsigned int CalculateMemOffset(uint64_t mask);



class MultiUInt64
{
private:
	std::vector<uint64_t> m_int{ 0 };

public:
	MultiUInt64() noexcept = default;
	MultiUInt64(_In_ const std::vector<uint64_t>& i) noexcept
		:m_int(i) {};
	MultiUInt64(_In_ const uint64_t val)
		:m_int({ val }) {}


	inline size_t size() const noexcept { return m_int.size(); }
	MultiUInt64 truncate() const noexcept;

	void PrintMemoryRepresentation();


	bool operator==(const MultiUInt64& other) const noexcept;
	MultiUInt64 operator|(const MultiUInt64& other) const noexcept;
	MultiUInt64& operator|=(const MultiUInt64& other) noexcept;
	MultiUInt64 operator&(const MultiUInt64& other) const noexcept;
	MultiUInt64& operator&=(const MultiUInt64& other) noexcept;


	MultiUInt64 operator<<(UINT shift) const noexcept;
	MultiUInt64& operator<<=(UINT shift) noexcept;
	MultiUInt64 operator>>(UINT shift) const noexcept;
	MultiUInt64& operator>>=(UINT shift) noexcept;

	uint64_t& operator[](UINT index) noexcept;
};

class _MemRepr
{
private:
	MultiUInt64 m_repr;
	DWORD m_pageAddress; // Address of the page. Must be a multiple of the page size.
	HANDLE m_hProc;

public:
	_MemRepr(_In_ DWORD pageAddress, _In_ HANDLE hProc, _In_opt_ bool debug = false);
	DWORD findSuitableMemory(_In_ size_t size, _In_ bool aligned = false);
	bool reserveMemory(_In_ DWORD address, _In_ size_t size);
	void PrintMemoryRepresentation();
};

class MemoryManager
{
private:
	std::list<_MemRepr> m_allocatedMem; // _MemRepr could be too heavy to be in a vector, depending of the page size.
	HANDLE m_hProc;

public:
	MemoryManager(HANDLE hProc);
	DWORD reserveMemory(_In_ size_t size, _In_opt_ bool aligned = false);
	DWORD insertData(_In_ BYTE data);
	DWORD insertData(_In_ std::vector<BYTE> data, _In_opt_ bool aligned = false);
};


#endif //MEMMGR_H