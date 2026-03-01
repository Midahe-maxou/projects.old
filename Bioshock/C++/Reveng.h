#pragma once
#ifndef REVENG_H
#define RENVENG_H


#include <vector>

#include <Windows.h>
#include <sal.h>


// Multibytes nop.

#define NOP 0x90
#define NOP1 { 0x90 }
#define NOP2 { 0x66, 0x90 }
#define NOP3 { 0x0F, 0x1F, 0x00 }
#define NOP4 { 0x0F, 0x1F, 0x40, 0x00 }
#define NOP5 { 0x0F, 0x1F, 0x44, 0x00, 0x00 }
#define NOP6 { 0x66, 0x0F, 0x1F, 0x44, 0x00, 0x00 }
#define NOP7 { 0x0F, 0x1F, 0x80, 0x00, 0x00, 0x00, 0x00 }
#define NOP8 { 0x0F, 0x1F, 0x84, 0x00, 0x00, 0x00, 0x00, 0x00 }


// Macros
#define writeMem(address, offset, val) WriteMemory(address, offset, val, sizeof(val))


typedef int OFFSET;
typedef std::vector<OFFSET> OFFSET_LIST;

// Global variables
static HWND hwnd = NULL;
static DWORD pId = NULL;
static HANDLE hProc = NULL;


DWORD init(_In_ LPCWSTR windowName, _In_ LPCWSTR moduleName);

HANDLE getHProc();

DWORD getModuleBaseAddress(_In_ const wchar_t* lpszModuleName, _In_ DWORD pID);

const DWORD getAddressWithOffsetList(_In_ DWORD baseAddress, _In_opt_ OFFSET_LIST offsets);

DWORD AllocateAndInjectCode(_In_ const std::vector<BYTE>& code, _In_ DWORD injectionAddress, _In_ BYTE opcodeLenght);

bool RetreiveAndDesallocateCode(_In_ DWORD injectedAddress, _In_ BYTE opcodeLenght);

std::vector<BYTE> revDwordToBytes(_In_ DWORD address);

DWORD revBytesAddressToDword(_In_ std::vector<BYTE> bytes);

void concat(_In_ std::vector<BYTE>& vec1, _In_ const std::vector<BYTE>& vec2);

std::vector<BYTE> getNops(_In_ int numOfNop);


std::vector<BYTE> ReadCode(_In_ DWORD address, _In_ DWORD offset);

std::vector<BYTE> ReadCode(_In_ DWORD address, _In_ DWORD offset, _In_ size_t size);

bool WriteCode(_In_ DWORD address, _In_ DWORD offset, _In_ std::vector<BYTE> code);


// Template functions

template <class T>
T ReadMemory(_In_ DWORD address, _In_ DWORD offset)
{
	T dest;
	ReadProcessMemory(hProc, reinterpret_cast<const void*>(address + offset), &dest, sizeof(dest), NULL);
	return dest;
}

template <class T>
bool WriteMemory(_In_ DWORD address, _In_ DWORD offset, _In_ T val)
{
	return WriteProcessMemory(hProc, reinterpret_cast<void*>(address + offset), &val, sizeof(val), NULL);
}


#endif //REVENG_H