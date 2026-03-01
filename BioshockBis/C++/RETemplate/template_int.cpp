#include "template_int.h"

#ifdef RE_INT

#include <Windows.h>
#include <vector>

#include "template.h"



__declspec(naked)
inline void callInTheMiddle() {
	__asm pushad;

	middleFunc();

	__asm {
		popad
		jmp[originalFunc]
	}
}

bool hijackCall(Addr hookCallAddr, Function<void()> hackFunc) {

	DWORD oldProtect;
	VirtualProtect((void*)hookCallAddr, 5, PAGE_EXECUTE_READWRITE, &oldProtect);

	BYTE directive = *(BYTE*)hookCallAddr;
	if (directive != 0xE8) return false; // Not a call

	Addr* pCalledAddr = (Addr*)(hookCallAddr + 1);

	middleFunc = hackFunc;
	originalFunc = (*pCalledAddr + hookCallAddr) + 5;

	Addr relHackAddr = ((Addr)&callInTheMiddle - hookCallAddr) - 5;
	// *(BYTE*)hookCallAddr = 0xE8; // call, should be useless
	*pCalledAddr = relHackAddr;

	VirtualProtect((void*)hookCallAddr, 5, oldProtect, &oldProtect);

	return true;
}

void unhook(Addr hookCallAddr) {
	if (originalFunc == 0) return;

	DWORD oldProtect;
	VirtualProtect((void*)hookCallAddr, 5, PAGE_EXECUTE_READWRITE, &oldProtect);

	Addr* pCalledAddr = (Addr*)(hookCallAddr + 1);
	*pCalledAddr = (originalFunc - hookCallAddr) - 5;

	VirtualProtect((void*)hookCallAddr, 5, oldProtect, &oldProtect);
}


#endif // RE_INT
