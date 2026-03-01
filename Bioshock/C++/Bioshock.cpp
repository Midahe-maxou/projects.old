#include "Bioshock.h"

#include <Windows.h>

#include <iostream>

#include "Reveng.h"

DWORD bioshockAddress = NULL;


Pointer::Pointer(_In_ DWORD baseAddress)
	:m_baseAddress(baseAddress)
{}


float LivingEntity::getHealth() const
{
	return ReadMemory<float>(bioshockAddress, ENTITY_HEALTH_OFFSET);
}

void LivingEntity::setHealth(_In_ const float health)
{
	WriteMemory(bioshockAddress, ENTITY_HEALTH_OFFSET, health);
}

float LivingEntity::getMaxHealth() const
{
	return ReadMemory<float>(bioshockAddress, ENTITY_MAX_HEALTH_OFFSET);
}

void LivingEntity::setMaxHealth(_In_ const float maxHealth)
{
	WriteMemory(bioshockAddress, ENTITY_MAX_HEALTH_OFFSET, maxHealth);
}

Player::Player()
	:Player(getAddressWithOffsetList(bioshockAddress + PLAYER_BASE_OFFSET, PLAYER_OFFSET_LIST))
{}

Player::Player(_In_ const DWORD playerAddress)
	:LivingEntity(playerAddress)
	//m_inv(playerAddress)
{}

bool Player::InfiniteAmmo(_In_ bool active)
{
	if (m_infiniteAmmo == active) return m_infiniteAmmo;

	std::vector<BYTE> code;

	if (active)
	{
		code = NOP3;
		WriteCode(bioshockAddress, PLAYER_DECREASE_TOTAL_AMMO, code);

		code = NOP2;
		WriteCode(bioshockAddress, PLAYER_DECREASE_AMMO_IN_MAG, code);

		m_infiniteAmmo = true;
	}
	else
	{
		code = { 0x29, 0x48, 0x4C };		// sub [eax+4C], ecx
		WriteCode(bioshockAddress, PLAYER_DECREASE_TOTAL_AMMO, code);

		code = { 0x29, 0x07 };				// sub [edi], eax
		WriteCode(bioshockAddress, PLAYER_DECREASE_AMMO_IN_MAG, code);

		m_infiniteAmmo = false;
	}

	return m_infiniteAmmo;
}

bool Player::GodMod(_In_ bool active)
{
	if (m_godMod == active) return m_godMod;

	if (active)
	{
		std::vector<BYTE> code = {
			0x83, 0xBE, 0xD4, 00, 00, 00, 00,							// cmp [esi+D4], 0
			0x75, 0x03,													// jne 3
			0x0F, 0x28, 0xCA,											// movaps xmm1, xmm2
			0xF3, 0x0F, 0x11, 0x8E, 0x40, 0x05, 0x00, 0x00				// movss [esi+540], xmm1
		};
		m_godMod = static_cast<bool>(AllocateAndInjectCode(code, bioshockAddress + ENTITY_DECREASE_HEALTH_CODE, 8));
	}
	else
	{
		RetreiveAndDesallocateCode(bioshockAddress + ENTITY_DECREASE_HEALTH_CODE, 8);
		m_godMod = false;
	}

	return m_godMod;
}


bool Player::OneShot(_In_ bool active)
{
	if (m_oneShot == active) return m_oneShot;

	if (active)
	{
		std::vector<BYTE> code =
		{
			0x83, 0xBE, 0xD4, 0x00, 0x00, 0x00, 0x00,					// cmp [esi+D4], 0
			0x74, 0x08,													// je short $+8
			0xC7, 0x44, 0x24, 0x20, 0xFF, 0xFF, 0x7F, 0x7F,				// mov [esp+20], 7F7FFFFF
			0xD8, 0x64, 0x24, 0x20,										// fsub [esp+20]
			0xF3, 0x0F, 0x11, 0x54, 0x24, 0x0C							// movss [esp+0C], xmm2
		};

		m_oneShot = static_cast<bool>(AllocateAndInjectCode(code, bioshockAddress + ENTITY_INFLICT_DAMAGE_CODE, 10));
	}
	else
	{
		RetreiveAndDesallocateCode(bioshockAddress + ENTITY_INFLICT_DAMAGE_CODE, 10);
		m_godMod = false;
	}

	return m_godMod;
}


unsigned int Player::getMoney() const
{
	unsigned int money = ReadMemory<unsigned int>(getBaseAddress(), PLAYER_MONEY_OFFSET);
	return money;
}

void Player::setMoney(_In_ const unsigned int money)
{
	WriteMemory(getBaseAddress(), PLAYER_MONEY_OFFSET, money);
}

unsigned int Player::getAdam() const
{
	unsigned int adam = ReadMemory<unsigned int>(getBaseAddress(), PLAYER_ADAM_OFFSET);
	return adam;
}

void Player::setAdam(_In_ const unsigned int adam)
{
	WriteMemory(getBaseAddress(), PLAYER_ADAM_OFFSET, adam);
}



DWORD bioshockInit()
{
	bioshockAddress = init(WINDOW_NAME, MODULE_NAME);
	return bioshockAddress;
}