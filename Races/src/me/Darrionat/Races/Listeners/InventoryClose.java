package me.Darrionat.Races.Listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.GUI.ConfirmGUI;
import me.Darrionat.Races.GUI.RaceGUI;
import me.Darrionat.Races.Utils.Maps;

public class InventoryClose implements Listener {
	private Main plugin;

	public InventoryClose(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClick(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		String title = e.getView().getTitle();

		if (title.equals(RaceGUI.inventory_name)) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				public void run() {
					if (p.getOpenInventory().getTitle().equals(ConfirmGUI.inventory_name)) {
						return;
					}
					p.openInventory(RaceGUI.GUI(p, plugin));
				}
			}, 1L);// 60 L == 3 sec, 20 ticks == 1 sec

		}

		if (title.equals(ConfirmGUI.inventory_name)) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					FilesManager filesmanager = new FilesManager(plugin);
					FileConfiguration playerdata = filesmanager.getDataConfig("playerdata");
					Maps maps = new Maps();
					List<String> list = maps.getPlayersInConfig();
					if (list.contains(p.getName())) {
						return;
					}

					if (p.getOpenInventory().getTitle().equals(RaceGUI.inventory_name)) {
						return;
					}
					if (playerdata.getString(p.getUniqueId().toString()) == null) {
						p.openInventory(ConfirmGUI.GUI(p, plugin));
						return;
					}

				}
			}, 1L);// 60 L == 3 sec, 20 ticks == 1 sec

		}
	}
}