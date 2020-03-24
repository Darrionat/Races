package me.Darrionat.Races.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Utils.Utils;

public class RaceGUI {

	public static Main plugin;

	public RaceGUI(Main plugin) {
		RaceGUI.plugin = plugin;
	}

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 1 * 9;

	public static void initialize() {
		inventory_name = Utils.chat("&6&lChoose your Race");
		inv = Bukkit.createInventory(null, inv_rows);
	}

	public static Inventory GUI(Player p, JavaPlugin plugin) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		// ConfigurationSection config = plugin.getConfig();

		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 1, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 2, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 5, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 8, " ");
		Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, 9, " ");

		Utils.createItem(inv, Material.FEATHER, 1, 3, "&bElf", "&7Permanent &bSpeed");
		Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 4, "&eDwarf", "&7Permanent &eHaste");
		Utils.createItem(inv, Material.COOKED_BEEF, 1, 6, "&cGiant", "&7Permanent &cStrength");
		Utils.createItem(inv, Material.BLAZE_ROD, 1, 7, "&4Demon", "&7Permanent &4Fire-Resistance");

		toReturn.setContents(inv.getContents());
		return toReturn;
	}

	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv, JavaPlugin plugin) {
		if (clicked.getItemMeta().getDisplayName() == null) {
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&bElf"))) {
			new ConfirmGUI((Main) plugin, "Elf");
			p.openInventory(ConfirmGUI.GUI(p, plugin));
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&eDwarf"))) {
			new ConfirmGUI((Main) plugin, "Dwarf");
			p.openInventory(ConfirmGUI.GUI(p, plugin));
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&cGiant"))) {
			new ConfirmGUI((Main) plugin, "Giant");
			p.openInventory(ConfirmGUI.GUI(p, plugin));
			return;
		}
		if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&4Demon"))) {
			new ConfirmGUI((Main) plugin, "Demon");
			p.openInventory(ConfirmGUI.GUI(p, plugin));
			return;
		}
	}
}