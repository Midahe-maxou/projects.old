#pragma once
#ifndef BIOSHOCK
#define BIOSHOCK

#include <bit>

#include "../RETemplate/template_int.h"
#include "../RETemplate/memmgr.h"


#define PLAYER_OFFSET_LIST					{ 0x9C4970, 0x38, 0x3C }

#define PISTOL_AMMO_OFFSET_LIST						{ 0x8DC520, 0x508, 0x330 }
#define PISTOL_ARMOR_PIERCING_AMMO_OFFSET_LIST		{ 0x8DC520, 0x508, 0x334 }
#define PISTOL_ANTI_PERSONNEL_AMMO_OFFSET_LIST		{ 0x8DC520, 0x508, 0x338 }

#define AUTO_AMMO_OFFSET_LIST						{ 0x8DAF84, 0x838, 0x928, 0x874 }
#define AUTO_ARMOR_PIERCING_AMMO_OFFSET_LIST		{ 0x8DAF84, 0x838, 0x928, 0x878 }
#define AUTO_ANTI_PERSONNEL_AMMO_OFFSET_LIST		{ 0x8DAF84, 0x838, 0x928, 0x87C } // TODO: pas bonne lol

#define LIVING_ENTITY_DAMAGE_FUNC_OFFSET	0x34FF40

// Code offset
#define ENTITY_DECREASE_HEALTH_CODE			0x350175
#define ENTITY_INFLICT_DAMAGE_CODE			0x350136
#define PLAYER_DECREASE_TOTAL_AMMO			0x378996
#define PLAYER_DECREASE_AMMO_IN_MAG			0x3DCB0C




#define HOOK_CALL_OFFSET					0x00A96A

class UClass;
class Item;
class Player;
class InventoryManager;
class Inventory;

/*
	float getHealth() {
		return *(float*)(this->getAddr() + 0x540);
	}
	
	void setHealth(float val) {
		*(float*)(this->getAddr() + 0x540) = val;
	};
*/

class Inventory {
public:
	union {
		Addr** vTable;
		PAD_VARIABLE(Player*, owner, 0x38);
		PAD_VARIABLE(Item**, items, 0x3C);
		PAD_VARIABLE(int, maxSize, 0x21C);
		PAD_VARIABLE(int, size, 0x220);
	};
};

class InventoryManager {
public:
	union {
		Addr** vTable;
		PAD_VARIABLE(Player*, Owner, 0x38);
		PAD_VARIABLE(Inventory*, CollectibleInventory, 0x3c);
		PAD_VARIABLE(Inventory*, SecondInventory, 0x40);
		PAD_VARIABLE(Inventory*, SpecialInventory, 0x44);
	};

	void addItem(Item* item) {
		Function<void (InventoryManager::*)(Item*)> _addItem{ 0x10c73c60 };
		_addItem(this, item);
	}
};

class Item {
public:

	static const OFFSET CONSTRUCTOR_OFFSET = 0x383340;

	union {
		Addr (*vTable)[56];
		PAD_VARIABLE(UClass*, itemUClass, 0x48);
		PAD_VARIABLE(int, amount, 0x4C);
		PAD_VARIABLE(unsigned int, nbRefs, 0x38);
	};
};

class Player
{
public:
	union {
		Addr (*vTable) [291];
		PAD_VARIABLE(UClass*, package, 0x18);
		PAD_VARIABLE(float, health, 0x540);
		PAD_VARIABLE(float, maxHealth, 0x734);
		PAD_VARIABLE(int, money, 0xAA8);
		PAD_VARIABLE(int, adam, 0xAB0);
		PAD_VARIABLE(InventoryManager*, inventoryManager, 0x904);
	};
	
	void dealDamage(float damage) {
		Function<void (Player::*)(float)> _damage{ (*vTable)[230] };
		_damage(this, damage);
	}

	void giveItem(Item* item) {
		Function<void (InventoryManager::*)(Item*)> _giveItem{ 0x10c73c60 };
		_giveItem(this->inventoryManager, item);
	}

	void giveItem(UClass* itemUClass, int amount) {

		Function<Item* __cdecl (UClass* itemUClass, UClass* package, int, int, int, int, int, Addr ppWindowErrorVTable, int)> createItemViaUClass(0x10ccc7c0);
		
		UClass* baseItemUClass = *(UClass**)0x111df8fc;
		Addr ppWindowErrorTable = 0x1114542c;

		UClass* package = this->package;
		Item* result = createItemViaUClass(baseItemUClass, package, 0, 0, 0, 0, 0, ppWindowErrorTable, 0);
		result->amount = amount;
		result->itemUClass = itemUClass;
		this->giveItem(result);
		if (result->nbRefs == 0) {
			Function<void (Item::*)(bool bFree)> freeObj{ 0x10c83270 };
			freeObj(result, true);
		}
	}
};



/*
class Player
{
public:

	union {
		Addr (*vTable) [291];
		PAD_VARIABLE(float, health, 0x540);
		PAD_VARIABLE(float, maxHealth, 0x734);
		PAD_VARIABLE(int, money, 0xAA8);
		PAD_VARIABLE(int, adam, 0xAB0);
	};

	Function<void (Player::*)(float)> damage = Function<void (Player::*)(float)>((*this->vTable)[230], (Player*)&this->vTable);
};

class Item {
public:

	static const OFFSET CONSTRUCTOR_OFFSET = 0x383340;

	union {
		Addr (*vTable)[56];
		PAD_VARIABLE(UClass*, itemUClass, 0x48);
		PAD_VARIABLE(int, amount, 0x4C);

	};
};
*/

#endif // BIOSHOCK