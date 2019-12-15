package me.Darrionat.Races.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;

public class EffectTimer {
	private Main plugin;

	public EffectTimer(Main plugin) {
		this.plugin = plugin;
	}

	public void startEffectTimer() {
		int time = plugin.getConfig().getInt("EffectTimer");
		FilesManager filesmanager = new FilesManager(plugin);
		FileConfiguration playerdata = filesmanager.getDataConfig("playerdata");
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					String uuid = p.getUniqueId().toString();
					if (playerdata.getString(uuid) == null) {
						continue;
					}
					if (playerdata.getString(uuid).equalsIgnoreCase("Elf")) {
						if (!p.hasPotionEffect(PotionEffectType.SPEED)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000 * 20, 0));
							continue;
						}
						continue;
					}
					if (playerdata.getString(uuid).equalsIgnoreCase("Dwarf")) {
						if (!p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000 * 20, 0));
							continue;
						}
						continue;
					}
					if (playerdata.getString(uuid).equalsIgnoreCase("Giant")) {
						if (!p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000 * 20, 0));
							continue;
						}
						continue;
					}
					if (playerdata.getString(uuid).equalsIgnoreCase("Demon")) {
						if (!p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000 * 20, 0));
							continue;
						}
						continue;
					}
				}
			}
		}, 0L, time * 20);
	}

}
