package me.Darrionat.Races;

import org.bukkit.plugin.java.JavaPlugin;

import me.Darrionat.Races.Commands.GuildCommand;
import me.Darrionat.Races.Commands.RacesCommand;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.GUI.ConfirmGUI;
import me.Darrionat.Races.GUI.GuildGUI;
import me.Darrionat.Races.GUI.PotionGUI;
import me.Darrionat.Races.GUI.RaceGUI;
import me.Darrionat.Races.Listeners.InventoryClick;
import me.Darrionat.Races.Listeners.InventoryClose;
import me.Darrionat.Races.Listeners.PlayerJoin;
import me.Darrionat.Races.Listeners.PotionDrink;
import me.Darrionat.Races.Utils.EffectTimer;

public class Main extends JavaPlugin {

	// Made by Darrionat/Arcator
	// This plugin is a custom made plugin for Amber. Discord - @amber#0719
	// If you are in possession of this plugin, and you did not purchase it, you
	// could be charged with possession of stolen copyrighted material.
	// Immediately delete this plugin or code if you are not the original buyer, or
	// personally confirmed by the developer.
	public void onEnable() {
		saveDefaultConfig();
		FilesManager filemanager = new FilesManager(this);
		if (filemanager.fileExists("playerdata") == false) {
			filemanager.setup("playerdata");
		}
		if (filemanager.fileExists("guilddata") == false) {
			filemanager.setup("guilddata");
		}
		new RacesCommand(this);
		new GuildCommand(this);
		new InventoryClick(this);
		new InventoryClose(this);
		new PlayerJoin(this);
		new PotionDrink(this);
		RaceGUI.initialize();
		ConfirmGUI.initialize();
		PotionGUI.initialize();
		GuildGUI.initialize();
		EffectTimer timer = new EffectTimer(this);
		timer.startEffectTimer();
	}

	public void onDisable() {

	}

}
