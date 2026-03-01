#pragma once
#ifndef BIOSHOCK_H
#define BIOSHOCK_H

#include <Windows.h>

// Constants
#define MODULE_NAME L"Bioshock.exe"
#define WINDOW_NAME L"Bioshock"


// Base offsets
#define PLAYER_BASE_OFFSET					0x009C4970

// List offsets
#define PLAYER_OFFSET_LIST					{ 0x38, 0x3C }

// Single offset
#define ENTITY_HEALTH_OFFSET				0x540
#define ENTITY_MAX_HEALTH_OFFSET			0x734
#define PLAYER_MONEY_OFFSET					0xAA8
#define PLAYER_ADAM_OFFSET					0xAB0

// Code offset
#define ENTITY_DECREASE_HEALTH_CODE			0x350175
#define ENTITY_INFLICT_DAMAGE_CODE			0x350136
#define PLAYER_DECREASE_TOTAL_AMMO			0x378996
#define PLAYER_DECREASE_AMMO_IN_MAG			0x3DCB0C


class Pointer
{
private:
	const DWORD m_baseAddress;

protected:
	Pointer() : Pointer(0x0) {}

public:
	Pointer(_In_ const DWORD baseAddress);

	DWORD getBaseAddress() const { return m_baseAddress; }
};


class LivingEntity : public Pointer
{
protected:
	LivingEntity() : LivingEntity(0x0) {}

public:
	LivingEntity(_In_ const DWORD entityAddress)
		:Pointer(entityAddress) {}

	float getHealth() const;
	void setHealth(_In_ const float health);

	float getMaxHealth() const;
	void setMaxHealth(_In_ const float maxHealth);
};


class Player : public LivingEntity
{
private:
	//Inventory m_inv;

	bool m_infiniteAmmo		= false;
	bool m_godMod			= false;
	bool m_oneShot			= false;

public:
	Player(_In_ const DWORD playerAddress);
	Player();

	bool InfiniteAmmo(_In_ bool active);
	bool GodMod(_In_ bool active);
	bool OneShot(_In_ bool active);
	
	unsigned int getMoney() const;
	void setMoney(_In_ const unsigned int money);

	unsigned int getAdam() const;
	void setAdam(_In_ const unsigned int adam);
	
};


class Inventory : public Pointer
{
public:
	Inventory(_In_ const DWORD inventoryAddress)
		:Pointer(inventoryAddress) {}
};

DWORD bioshockInit();

#endif // BIOSHOCK_H