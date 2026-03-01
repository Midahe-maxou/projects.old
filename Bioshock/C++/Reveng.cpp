
#include "Reveng.h"

#include <iostream>

#include <Windows.h>
#include <processthreadsapi.h> // openProcess
#include <TlHelp32.h> // getModuleBaseAddress
#include <tchar.h> // _tcscmp



DWORD init(_In_ LPCWSTR windowName, _In_ LPCWSTR moduleName)
{
	hwnd = FindWindowW(NULL, windowName);
	if (!hwnd) return NULL;

	GetWindowThreadProcessId(hwnd, &pId);
	if (!pId) return NULL;

	hProc = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pId);
	if (!hProc || hProc == INVALID_HANDLE_VALUE) return NULL;

	return getModuleBaseAddress(moduleName, pId);
}

HANDLE getHProc()
{
	return hProc;
}



const DWORD getAddressWithOffsetList(_In_ DWORD baseAddress, _In_opt_ OFFSET_LIST offsets)
{
	DWORD address = ReadMemory<DWORD>(baseAddress, 0);
	for (int offset : offsets)
	{
		address = ReadMemory<DWORD>(address, offset);
	}
	return address;
}


std::vector<BYTE> ReadCode(_In_ DWORD address, _In_ DWORD offset)
{
	std::vector<BYTE> dest;
	ReadProcessMemory(hProc, reinterpret_cast<void*>(address + offset), dest.data(), dest.size(), NULL);
	return dest;
}

std::vector<BYTE> ReadCode(_In_ DWORD address, _In_ DWORD offset, _In_ size_t size)
{
	std::vector<BYTE> dest;
	ReadProcessMemory(hProc, reinterpret_cast<void*>(address + offset), dest.data(), size, NULL);
	return dest;
}

bool WriteCode(_In_ DWORD address, _In_ DWORD offset, _In_ std::vector<BYTE> code)
{
	return WriteProcessMemory(hProc, reinterpret_cast<void*>(address + offset), code.data(), code.size(), NULL);
}



DWORD getModuleBaseAddress(_In_ const wchar_t* lpszModuleName, _In_ DWORD pID) {
	DWORD dwModuleBaseAddress = 0;
	HANDLE hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, pID); // Make snapshot of all modules within the process.
	MODULEENTRY32 ModuleEntry32 = { 0 };
	ModuleEntry32.dwSize = sizeof(MODULEENTRY32);

	if (Module32First(hSnapshot, &ModuleEntry32)) // Store the first Module in ModuleEntry32.
	{
		do {
			if (_tcscmp(ModuleEntry32.szModule, lpszModuleName) == 0) // If found Module matches Module we look for -> done!
			{
				dwModuleBaseAddress = (DWORD)ModuleEntry32.modBaseAddr;
				break;
			}
		} while (Module32Next(hSnapshot, &ModuleEntry32)); // Go through Module entries in Snapshot and store in ModuleEntry32.


	}
	CloseHandle(hSnapshot);
	return dwModuleBaseAddress;
}

DWORD AllocateAndInjectCode(_In_ const std::vector<BYTE>& code, _In_ DWORD injectionAddress, _In_ BYTE opcodeLenght)
{
	if (opcodeLenght < 5) return NULL;

	DWORD allocatedAddress = reinterpret_cast<DWORD>(VirtualAllocEx(hProc, nullptr, 2048, MEM_RESERVE | MEM_COMMIT, PAGE_EXECUTE_READWRITE));
	if (!allocatedAddress) return NULL;

	DWORD jumpLength = allocatedAddress - injectionAddress;

	std::vector<BYTE> jmpCode =
	{
		0xE9		// jmp
	};

	concat(jmpCode, revDwordToBytes(jumpLength - 0x5UL + (DWORD)(opcodeLenght))); // Allocated address relative to injection address (+ reserved bytes).
	concat(jmpCode, getNops(opcodeLenght - 5)); // NOPs to fill the remaining bytes.

	std::vector<BYTE> initialCode = ReadCode(injectionAddress, 0, opcodeLenght); // Retreive initial code
	WriteCode(injectionAddress, 0, jmpCode);

	WriteCode(allocatedAddress, 0, initialCode); // Save initial code into reserved bytes.
	WriteCode(allocatedAddress, opcodeLenght, code); // Write injection code.

	jmpCode =
	{
		0xE9		// jmp
	};
	concat(jmpCode, revDwordToBytes((DWORD)(-(long)jumpLength) - code.size() - 0x5UL));	// Injection address relative to the end of injected code (+ reserved bytes).

	WriteCode(allocatedAddress, code.size() + opcodeLenght, jmpCode);
	return allocatedAddress;
}

bool RetreiveAndDesallocateCode(_In_ DWORD injectedAddress, _In_ BYTE opcodeLenght)
{
	std::vector<BYTE> jmpCode = ReadCode(injectedAddress, 0, 5);

	std::cout << "Jump code: ";
	for (BYTE b : jmpCode)
		std::cout << std::hex << (int)b << " ";
	std::cout << std::endl;

	if (jmpCode[0] != 0xE9) return false;

	jmpCode.erase(jmpCode.cbegin());
	DWORD allocatedAddress = revBytesAddressToDword(jmpCode) + (injectedAddress + 0x5) - (DWORD)opcodeLenght;

	std::cout << "Allocated address: " << std::hex << allocatedAddress << std::endl;


	std::vector<BYTE> initialCode = ReadCode(allocatedAddress, 0, opcodeLenght);

	std::cout << "Initial code: ";
	for (BYTE b : initialCode)
		std::cout << std::hex << (int)b << " ";
	std::cout << std::endl;

	if (!VirtualFreeEx(hProc, reinterpret_cast<void*>(allocatedAddress), NULL, MEM_RELEASE)) return false;
	WriteCode(injectedAddress, 0, initialCode);
	return true;
}


std::vector<BYTE> getNops(_In_ int numOfNop)
{
	if (numOfNop < 0) return {}; // Justin Case

	switch (numOfNop)
	{
	case 0:
		return {};
	case 1:
		return NOP1;
	case 2:
		return NOP2;
	case 3:
		return NOP3;
	case 4:
		return NOP4;
	case 5:
		return NOP5;
	case 6:
		return NOP6;
	case 7:
		return NOP7;
	case 8:
		return NOP8;

	default:
		std::vector<BYTE> nops = getNops(numOfNop - 8);
		nops.insert(nops.cbegin(), NOP8);
		return nops;
	}
}

std::vector<BYTE> revDwordToBytes(_In_ DWORD address)
{
	std::vector<BYTE> bytes;

	for (int i = 0; i < 4; i++)
		bytes.push_back(((address >> (i * 8)) & 0xFF));

	return bytes;
}

DWORD revBytesAddressToDword(_In_ std::vector<BYTE> bytes)
{
	if (bytes.size() != 4) return NULL;
	DWORD address = NULL;

	for (int i = 0; i < 4; i++)
		address += bytes[i] << (i * 8);
	return address;
}

void concat(_In_ std::vector<BYTE>& vec1, _In_ const std::vector<BYTE>& vec2)
{
	vec1.reserve(vec2.size());
	vec1.insert(vec1.cend(), vec2.cbegin(), vec2.cend());
}