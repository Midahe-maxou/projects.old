

#include <iostream>
#include <Windows.h>

#include "Reveng.h"
#include "Bioshock.h"
#include "Memmgr.h"

int main()
{

	DWORD bioshockAddress = bioshockInit();
	HANDLE hProc = getHProc();

	_MemRepr repr = _MemRepr(0x00820000, hProc, true);
	std::cout << std::hex << repr.FindSuitableMemory(40) << std::endl;


	return 0x0;
}
