package me.Darrionat.Races.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.GUI.RaceGUI;

public class PlayerJoin implements Listener {
	private Main plugin;

	public PlayerJoin(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (plugin.getConfig().getBoolean("ForceRaceOnJoin") == false) {
			return;
		}
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		FilesManager filesmanager = new FilesManager(plugin);
		FileConfiguration playerdata = filesmanager.getDataConfig("playerdata");
		if (playerdata.getString(uuid) == null) {
			p.openInventory(RaceGUI.GUI(p, plugin));
		}

	}

}
