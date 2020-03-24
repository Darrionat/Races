package me.Darrionat.Races.Listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.GUI.RaceGUI;
import me.Darrionat.Races.Utils.Utils;

public class PotionDrink implements Listener {
	private Main plugin;

	public PotionDrink(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClick(PlayerItemConsumeEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		ItemStack item = e.getItem();
		if (item.getItemMeta().getDisplayName().equals(Utils.chat("&dMagical Potion"))) {
			FilesManager filesmanager = new FilesManager(plugin);
			FileConfiguration playerdata = filesmanager.getDataConfig("playerdata");
			if (playerdata.getString(uuid) == null) {
				p.sendMessage(Utils.chat("&cYou do not already have a race!"));
				e.setCancelled(true);
				return;
			}
			File playerdataFile = filesmanager.getFile("playerdata");
			playerdata.set(uuid, null);
			try {
				playerdata.save(playerdataFile);
			} catch (IOException exe) {
				p.sendMessage(Utils.chat("&cThere was an error saving your data. Please contact an Administrator"));
				System.out.println(
						Utils.chat("&4There was an error saving this player's data to playerdata.yml. Stacktrace:"));
				exe.printStackTrace();
				return;
			}
			p.openInventory(RaceGUI.GUI(p, plugin));
			e.setCancelled(true);
			p.getInventory().remove(item);
		}
	}
}