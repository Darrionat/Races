package me.Darrionat.Races.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.Darrionat.Races.Main;
import me.Darrionat.Races.Utils.Utils;

public class FilesManager {

	private Main plugin;

	public FilesManager(Main plugin) {
		this.plugin = plugin;
	}

	// Files and File configurations here
	public static FileConfiguration config;
	public File File;
	// -------------------------------------

	public void setup(String fileName) {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		File = new File(plugin.getDataFolder(), fileName + ".yml");

		if (!File.exists()) {
			try {
				File.createNewFile();
				config = YamlConfiguration.loadConfiguration(File);
				String successMessage = "&e[" + plugin.getName() + "] &aCreated the " + fileName + ".yml file";
				Bukkit.getServer().getConsoleSender().sendMessage(Utils.chat(successMessage));
				saveMessages();
			} catch (IOException exe) {
				String failMessage = "&e[" + plugin.getName() + "] &cFailed to create the " + fileName + ".yml file";
				Bukkit.getServer().getConsoleSender().sendMessage(Utils.chat(failMessage));
				exe.printStackTrace();
			}
		}

	}

	public boolean fileExists(String fileName) {
		File = new File(plugin.getDataFolder(), fileName + ".yml");
		if (File.exists()) {
			return true;
		}
		return false;
	}

	public void deleteFile(String fileName) {
		File = new File(plugin.getDataFolder(), fileName + ".yml");
		File.delete();
		return;
	}

	public FileConfiguration getDataConfig(String fileName) {
		File = new File(plugin.getDataFolder(), fileName + ".yml");
		config = YamlConfiguration.loadConfiguration(File);
		return config;
	}

	public File getFile(String fileName) {
		File = new File(plugin.getDataFolder(), fileName + ".yml");
		return File;
	}

	public void saveMessages() {
		/*
		 * try { messagesConfig.set("updatePt2",
		 * "&bAn update for &7GUIShopSpawners &f(%UpdatedVersion%) &bis available at");
		 * messagesConfig.save(messagesFile); String successMessage = "&e[" +
		 * plugin.getName() + "] &aSaved the messages.yml file";
		 * Bukkit.getServer().getConsoleSender().sendMessage(Utils.chat(successMessage))
		 * ; } catch (IOException exe) {
		 * 
		 * }
		 */

	}

	public void reloadFile(String fileName) {
		File = new File(plugin.getDataFolder(), fileName + ".yml");
		config = YamlConfiguration.loadConfiguration(File);
		String reloadMessage = "&e[" + plugin.getName() + "] &aReloaded the " + fileName + ".yml file";
		Bukkit.getServer().getConsoleSender().sendMessage(Utils.chat(reloadMessage));
	}
}
