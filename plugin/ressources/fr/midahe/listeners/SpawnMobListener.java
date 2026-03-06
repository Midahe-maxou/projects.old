package fr.midahe.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class SpawnMobListener implements Listener {

	@EventHandler
	public void onAnimalSpawn(CreatureSpawnEvent e) {

		if (e.getSpawnReason() == SpawnReason.CUSTOM || e.getSpawnReason() == SpawnReason.SPAWNER_EGG) {
			return;
		}

		if (e.getEntityType() == EntityType.VILLAGER || e.getEntityType() == EntityType.ARMOR_STAND) {
			return;
		}

		String customName;
		String number = "ceci est un test";
		int i = 0;
		String[] named;

		Location loc = e.getLocation();
		EntityType type = e.getEntityType();
		Chunk chunk = loc.getChunk();
		Entity[] allEntity = chunk.getEntities();

		for (Entity entity : allEntity) {

			if (entity.getLocation().getY() - 5 > e.getEntity().getLocation().getY()
					|| entity.getLocation().getY() + 5 < e.getEntity().getLocation().getY()) {
				continue;
			}

			if (entity.getType() == type) {

				try {
					customName = entity.getCustomName();

					if (customName.contains("§5")) {
						named = customName.split("x");
						number = named[1];
					}

					try {
						i = Integer.valueOf(number);
						i++;
					} catch (NumberFormatException exce) {
						exce.printStackTrace();
					}

					if (i == 1000) {
						return;
					}

					entity.setCustomName("§5x" + i);

				} catch (NullPointerException exc) {

					entity.setCustomName("§5x2");

				}
				entity.setCustomNameVisible(true);
				e.setCancelled(true);
			}
		}
	}
}
