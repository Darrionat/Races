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
import org.bukkit.potion.PotionEffect;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.Utils.EffectTimer;
import me.Darrionat.Races.Utils.Maps;
import me.Darrionat.Races.Utils.Utils;

public class ConfirmGUI {

	public static Main plugin;

	private static String type;

	public ConfirmGUI(Main plugin, String type) {
		ConfirmGUI.plugin = plugin;
		ConfirmGUI.type = type;
	}

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 1 * 9;

	public static void initialize() {
		inventory_name = Utils.chat("Confirm your choice");
		inv = Bukkit.createInventory(null, inv_rows);
	}

	public static Inventory GUI(Player p, JavaPlugin plugin) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		// ConfigurationSection config = plugin.getConfig();

		for (int i = 1; i <= 9; i++) {
			if (i == 4 || i == 6) {
				continue;
			}
			Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, i, " ");
		}
		Utils.createItem(inv, Material.RED_WOOL, 1, 4, "&4&lDENY");
		Utils.createItem(inv, Material.GREEN_WOOL, 1, 6, "&2&lCONFIRM", "&7This choice is forever.");

		toReturn.setContents(inv.getContents());
		return toReturn;
	}

	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv, JavaPlugin plugin) {
		if (clicked.getItemMeta().getDisplayName() == null) {
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&4&lDENY"))) {
			p.openInventory(RaceGUI.GUI(p, plugin));
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&2&lCONFIRM"))) {
			p.closeInventory();
			FilesManager filesmanager = new FilesManager((Main) plugin);
			FileConfiguration playerdata = filesmanager.getDataConfig("playerdata");
			File playerdataFile = filesmanager.getFile("playerdata");
			playerdata.set(p.getUniqueId().toString(), type);
			Maps maps = new Maps();

			try {
				playerdata.save(playerdataFile);
				Bukkit.getScheduler().cancelTasks(plugin);
				for (PotionEffect effect: p.getActivePotionEffects()) {
					p.removePotionEffect(effect.getType());
				}
				
				filesmanager.reloadFile("playerdata");
				EffectTimer timer = new EffectTimer((Main) plugin);
				timer.startEffectTimer();
				p.sendMessage(Utils.chat(plugin.getConfig().getString("messages.SelectType").replace("%type%", type)));
				maps.getPlayersInConfig().add(p.getName());
				return;
			} catch (IOException e) {
				p.sendMessage(Utils.chat("&cThere was an error saving your data. Please contact an Administrator"));
				System.out.println(
						Utils.chat("&4There was an error saving this player's data to playerdata.yml. Stacktrace:"));
				e.printStackTrace();
				return;
			}
		}

	}
}