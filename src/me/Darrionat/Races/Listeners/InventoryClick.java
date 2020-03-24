package me.Darrionat.Races.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.GUI.ConfirmGUI;
import me.Darrionat.Races.GUI.GuildGUI;
import me.Darrionat.Races.GUI.PotionGUI;
import me.Darrionat.Races.GUI.RaceGUI;
import me.Darrionat.Races.Utils.Utils;

public class InventoryClick implements Listener {
	private Main plugin;

	public InventoryClick(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String title = Utils.chat(e.getView().getTitle());
		if (title.equals(RaceGUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			RaceGUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
		}
		if (title.equals(ConfirmGUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			ConfirmGUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
		}
		if (title.equals(PotionGUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			PotionGUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
		}
		if (title.equals(GuildGUI.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			GuildGUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), plugin);
		}
	}
}