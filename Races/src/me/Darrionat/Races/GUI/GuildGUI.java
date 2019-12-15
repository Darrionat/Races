package me.Darrionat.Races.GUI;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.Utils.Utils;

public class GuildGUI {

	public static Main plugin;

	public GuildGUI(Main plugin) {
		GuildGUI.plugin = plugin;
	}

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 1 * 9;

	public static void initialize() {
		inventory_name = Utils.chat("&6&lChoose your Guild");
		inv = Bukkit.createInventory(null, inv_rows);
	}

	public static Inventory GUI(Player p, JavaPlugin plugin) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		FileConfiguration config = plugin.getConfig();
		String lightBringer = Utils.chat(config.getString("Lightbringer"));
		String chota = Utils.chat(config.getString("Chota"));
		String travelers = Utils.chat(config.getString("Traveler"));
		String enforcer = Utils.chat(config.getString("Enforcer"));
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 1, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 2, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 5, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 8, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 9, " ");

		Utils.createItem(inv, Material.YELLOW_STAINED_GLASS_PANE, 1, 3, lightBringer);
		Utils.createItem(inv, Material.RED_STAINED_GLASS_PANE, 1, 4, chota);
		Utils.createItem(inv, Material.MAGENTA_STAINED_GLASS_PANE, 1, 6, travelers);
		Utils.createItem(inv, Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, 7, enforcer);

		toReturn.setContents(inv.getContents());
		return toReturn;
	}

	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv, JavaPlugin plugin) {
		FileConfiguration config = plugin.getConfig();
		String lightBringer = Utils.chat(config.getString("Lightbringer"));
		String chota = Utils.chat(config.getString("Chota"));
		String travelers = Utils.chat(config.getString("Traveler"));
		String enforcer = Utils.chat(config.getString("Enforcer"));
		FilesManager filesmanager = new FilesManager((Main) plugin);
		String uuid = p.getUniqueId().toString();
		FileConfiguration guildDataConfig = filesmanager.getDataConfig("guilddata");
		File guildData = filesmanager.getFile("guilddata");
		if (clicked.getItemMeta().getDisplayName() == null) {
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equals(Utils.chat(lightBringer))) {
			guildDataConfig.set(uuid, "Lightbringer");
			p.setPlayerListName(lightBringer + " " + p.getDisplayName());
			saveGuild(guildData, guildDataConfig, p);
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equals(Utils.chat(chota))) {
			guildDataConfig.set(uuid, "Chota");
			p.setPlayerListName(chota + " " + p.getDisplayName());
			saveGuild(guildData, guildDataConfig, p);
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equals(Utils.chat(travelers))) {
			guildDataConfig.set(uuid, "Travelers");
			p.setPlayerListName(travelers + " " + p.getDisplayName());
			saveGuild(guildData, guildDataConfig, p);
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equals(Utils.chat(enforcer))) {
			guildDataConfig.set(uuid, "Enforcer");
			p.setPlayerListName(enforcer + " " + p.getDisplayName());
			saveGuild(guildData, guildDataConfig, p);
			return;
		}
	}

	public static void saveGuild(File guildData, FileConfiguration guildDataConfig, Player p) {
		try {
			guildDataConfig.save(guildData);
			p.closeInventory();
		} catch (IOException e) {
			p.sendMessage(Utils
					.chat("&4[Error] &cThere was an error saving your guild data. Please contact an Administrator."));
			e.printStackTrace();
		}
	}
}