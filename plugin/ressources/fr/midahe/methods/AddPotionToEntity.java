package fr.midahe.methods;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AddPotionToEntity {

	public AddPotionToEntity(Entity entity, String effect, int duration, int amplifier, boolean hideparticles) {
		
		
		switch(effect.toLowerCase()){
			case "absorption":
				effect(entity, PotionEffectType.ABSORPTION, duration, amplifier, hideparticles);
				break;
			
			case "blindness":
				effect(entity, PotionEffectType.BLINDNESS, duration, amplifier, hideparticles);
				break;
				
			case "fire_resistance":
				effect(entity, PotionEffectType.FIRE_RESISTANCE, duration, amplifier, hideparticles);
				break;
				
			case "haste":
				effect(entity, PotionEffectType.FAST_DIGGING, duration, amplifier, hideparticles);
				break;
				
			case "health_boost":
				effect(entity, PotionEffectType.HEALTH_BOOST, duration, amplifier, hideparticles);
				break;
				
			case "hunger":
				effect(entity, PotionEffectType.HUNGER, duration, amplifier, hideparticles);
				break;
				
			case "invisibility":
				effect(entity, PotionEffectType.INVISIBILITY, duration, amplifier, hideparticles);
				break;
				
			case "jump_boost":
				effect(entity, PotionEffectType.JUMP, duration, amplifier, hideparticles);
				break;
				
			case "mining_fatigue":
				effect(entity, PotionEffectType.SLOW_DIGGING, duration, amplifier, hideparticles);
				break;
				
			case "nausea":
				effect(entity, PotionEffectType.CONFUSION, duration, amplifier, hideparticles);
				break;
				
			case "night_vision":
				effect(entity, PotionEffectType.NIGHT_VISION, duration, amplifier, hideparticles);
				break;
				
			case "resistance":
				effect(entity, PotionEffectType.DAMAGE_RESISTANCE, duration, amplifier, hideparticles);
				
			case "regeneration":
				effect(entity, PotionEffectType.REGENERATION, duration, amplifier, hideparticles);
				
			case "slowness":
				effect(entity, PotionEffectType.SLOW, duration, amplifier, hideparticles);
				break;
				
			case "speed":
				effect(entity, PotionEffectType.SPEED, duration, amplifier, hideparticles);
				break;
				
			case "strengh":
				effect(entity, PotionEffectType.INCREASE_DAMAGE, duration, amplifier, hideparticles);
				break;
				
			case "water_breathing":
				effect(entity, PotionEffectType.WATER_BREATHING, duration, amplifier, hideparticles);
				break;
				
			case "weakness":
				effect(entity, PotionEffectType.WEAKNESS, duration, amplifier, hideparticles);
				break;
				
			case "wither":
				effect(entity, PotionEffectType.WITHER, duration, amplifier, hideparticles);
				break;
				
			default:
				fr.midahe.commands.CommandPlayer.isExistEffect = false;
				fr.midahe.commands.CommandMob.isExistEffect = false;
				break;
		}
	}

	public AddPotionToEntity(Entity entity, String effect, int duration, int amplifier) {
		new AddPotionToEntity(entity, effect, duration, amplifier, false);
	}
	
	public static void effect(Entity entity, PotionEffectType potion, int duration, int amplifier, boolean hideparticles) {
		((LivingEntity) entity).addPotionEffect(new PotionEffect(potion, duration*20, amplifier, true, hideparticles));
	}
}




