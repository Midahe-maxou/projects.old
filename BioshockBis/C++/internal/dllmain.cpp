// dllmain.cpp : Définit le point d'entrée de l'application DLL.

#include <iostream>

#include "../RETemplate/template.h"
#include "../RETemplate/template_int.h"

#include "Bioshock.h"

#define PROCNAME L"Bioshock.exe"

DWORD WINAPI HackThread(HMODULE hModule);


static std::vector<Function<void()>> hookCalls{};



BOOL APIENTRY DllMain( HMODULE hModule,
					   DWORD  ul_reason_for_call,
					   LPVOID lpReserved)
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
	{
		HANDLE thread = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)HackThread, hModule, 0, nullptr);
		if (thread) CloseHandle(thread);
		break;
	}
	case DLL_THREAD_ATTACH:
		[[fallthrough]];
	case DLL_THREAD_DETACH:
		break;
	case DLL_PROCESS_DETACH:
		break;
	}
	return TRUE;
}

class ItemLootSpecification{};
class ItemSlot{};

std::vector<Addr> allocs{};

/*
void doThing() {

	Addr baseAddr = (Addr)GetModuleHandle(PROCNAME);
	auto player = Pointer<Player>(baseAddr, PLAYER_OFFSET_LIST);

	InventoryManager* invMgr = FindAddrWithOffsets<InventoryManager*>((Addr)player.getAddr(), { 0x904 });

	Inventory* inventory = FindAddrWithOffsets<Inventory*>((Addr)player.getAddr(), { 0x904, 0x3c });
	
	Addr ppWindowErrorTable = 0x1114542c;
	
	Item* result = (Item*)0x378B3390;

	Function<Item* (ItemLootSpecification::*)()> itemCreation(0x10c6e100);
	Function<void (Inventory::*)(Item*)> addItemToInv(0x10c785c0);
	Function<void (ItemSlot::*)(uintptr_t caBe0)> createItemFromSlot(0x10c7fa20);
	Function<void (InventoryManager::*)(Item*)> addItemViaInvManager(0x10c73c60);
	Function<void (Player::*)(UClass*, int)> giveItem(0x10c0b890);
	Function<UClass* (UClass::*)()> getPackage(0x10cbfe60);
	Function<Item* __cdecl (UClass* itemUClass, UClass* package, int, int, int, int, int, Addr ppWindowErrorVTable, int)> createItemViaUClass(0x10ccc7c0);

	//result = itemCreation(itemLootSpecification);
	//addItemToInv(inventory, result);
	//addItemViaInvManager(invMgr, result);
	//createItemFromSlot(itemSlot, 0);

	//giveItem(player.getAddr(), itemUClass, 0);

	
	UClass* package = getPackage((UClass*)player.getAddr());
	result = createItemViaUClass(*(UClass**)0x111df8fc, // base ItemUClass
				package, 0, 0, 0, 0, 0, ppWindowErrorTable, 0);
	result->amount = 3;
	result->itemUClass = (UClass*)0x1043D800;
	addItemViaInvManager(invMgr, result);
	

	std::cout << "Item given??" << std::endl;

}
*/

class Level {};

void doThing() {
	Function<bool (Level::*)(Player*, Vector3F, Vector3I, float one)> doSmthWithActor(0x10a92da0);
	Level* lvl = (Level*)0x154FA000;
	Player* p = (Player*)0x14CD1000;
	doSmthWithActor(lvl, p, { 2249.f, 619.f, -569.f }, { 0, 33562, 0 }, 1.0);
}

void doDamage() {
	Addr baseAddr = (Addr)GetModuleHandle(PROCNAME);
	Player* player = FindAddrWithOffsets<Player*>(baseAddr, PLAYER_OFFSET_LIST);

	player->dealDamage(17.f);

}

void getPistolAmmos() {
	Addr baseAddr = (Addr)GetModuleHandle(PROCNAME);
	Player* player = FindAddrWithOffsets<Player*>(baseAddr, PLAYER_OFFSET_LIST);

	UClass* ammo = FindAddrWithOffsets<UClass*>(baseAddr, AUTO_AMMO_OFFSET_LIST);
	UClass* armorAmmo = FindAddrWithOffsets<UClass*>(baseAddr, AUTO_ARMOR_PIERCING_AMMO_OFFSET_LIST);
	UClass* antiPersonelAmmo = FindAddrWithOffsets<UClass*>(baseAddr, AUTO_ANTI_PERSONNEL_AMMO_OFFSET_LIST);

	player->giveItem(ammo, 1);
	player->giveItem(armorAmmo, 1);
	player->giveItem(antiPersonelAmmo, 1);
}

void handleHook()
{
	Function<void()> func;
	while (!hookCalls.empty()) {
		func = hookCalls.back();
		hookCalls.pop_back();
		func();
	}
}

void addCallMessage(Function<void __cdecl ()> func)
{
	hookCalls.push_back(func);
}

void isHooked()
{
	std::cout << "hooked" << std::endl;
}


DWORD WINAPI HackThread(HMODULE hModule)
{ 
	AllocConsole();
	FILE* f;
	freopen_s(&f, "CONOUT$", "w", stdout);


	Addr baseAddr = (Addr)GetModuleHandle(PROCNAME);

	Player* player = FindAddrWithOffsets<Player*>(baseAddr, PLAYER_OFFSET_LIST);

	hijackCall(baseAddr + HOOK_CALL_OFFSET, handleHook);

	std::cout << doThing << std::endl;

	while (true) {
		if (GetAsyncKeyState(VK_NUMPAD0) & 1) {
			addCallMessage(doThing);
		}

		if (GetAsyncKeyState(VK_NUMPAD1) & 1) {
			std::cout << player->health << std::endl;
			// player->setHealth(150.f);
		}

		if (GetAsyncKeyState(VK_NUMPAD2) & 1) {
			//addCallMessage(doDamage);
		}

		if (GetAsyncKeyState(VK_NUMPAD3) & 1) {
			//addCallMessage(resetSlot);
		}

		if (GetAsyncKeyState(VK_END) & 1) {
			break;
		}
	}

	if(f) fclose(f);
	unhook(baseAddr + HOOK_CALL_OFFSET);
	for (Addr alloc : allocs)
		VirtualFree((void*)alloc, 0x50, MEM_FREE);
	FreeConsole();
	FreeLibraryAndExitThread(hModule, 0);
}