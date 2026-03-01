#pragma once
#ifndef MEMMGR_H
#define MEMMGR_H

#include <vector>
#include <array>
#include <stdexcept>

#include <Windows.h>


class _MemRepr
{
private:
	std::array<uint64_t, 64> m_repr{ 0 };
	DWORD m_address;

public:
	_MemRepr(_In_ DWORD address, _In_ HANDLE hProc, _In_opt_ bool debug = false);
	DWORD FindSuitableMemory(_In_ size_t size, _In_ bool aligned = false);
	bool ReserveMemory(_In_ DWORD address, _In_ size_t size);
	void PrintMemoryRepresentation();
};

class MemoryManager
{
private:
	std::vector<_MemRepr> m_allocatedMem;
	HANDLE m_hProc;

public:
	MemoryManager(HANDLE hProc);
	DWORD insertData(_In_ BYTE data);
	DWORD insertData(_In_ std::vector<BYTE> data, _In_opt_ bool aligned = false);
};


#endif //MEMMGR_H