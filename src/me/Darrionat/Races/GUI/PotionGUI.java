package me.Darrionat.Races.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Utils.Utils;

public class PotionGUI {

	public static Main plugin;

	public PotionGUI(Main plugin) {
		PotionGUI.plugin = plugin;
	}

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 1 * 9;

	public static void initialize() {
		inventory_name = Utils.chat("Magical Potion");
		inv = Bukkit.createInventory(null, inv_rows);
	}

	public static Inventory GUI(Player p, JavaPlugin plugin) {
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		for (int i = 1; i <= 9; i++) {
			if (i == 5) {
				continue;
			}
			Utils.createItem(inv, Material.BLACK_STAINED_GLASS_PANE, 1, i, " ");
			continue;
		}

		int expCost = plugin.getConfig().getInt("PotionResetCost");
		ItemStack item = getPotion();

		Utils.createPotion(inv, item, 1, 5, "&dMagical Potion", "&7This &dMagical Potion &7will allow you",
				"&7to choose your race once more", "&7Cost: &e" + expCost + " EXP Levels");

		toReturn.setContents(inv.getContents());
		return toReturn;
	}

	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv, JavaPlugin plugin) {
		if (clicked.getItemMeta().getDisplayName() == null) {
			return;
		}

		if (clicked.getItemMeta().getDisplayName().equals(Utils.chat("&dMagical Potion"))) {
			int playerLevel = p.getLevel();
			int expCost = plugin.getConfig().getInt("PotionResetCost");
			int neededLevels = expCost - playerLevel;

			if (playerLevel < expCost) {
				p.closeInventory();
				p.sendMessage(
						Utils.chat("&cYou need &e" + neededLevels + " EXP Levels &cto be able to purchase this."));
				return;
			}
			p.closeInventory();
			p.setLevel(playerLevel - expCost);
			ItemStack potion = getPotion();
			List<String> lore = new ArrayList<String>();
			ItemMeta meta = potion.getItemMeta();
			lore.add(Utils.chat("&7This &dMagical Potion &7will allow you"));
			lore.add(Utils.chat("&7to choose your race once more"));
			meta.setLore(lore);
			meta.setDisplayName(Utils.chat("&dMagical Potion"));
			potion.setItemMeta(meta);
			p.getInventory().addItem(potion);
			return;
		}
	}

	public static ItemStack getPotion() {
		ItemStack item = new ItemStack(Material.POTION, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		PotionMeta potion = (PotionMeta) meta;
		PotionData data = new PotionData(PotionType.INVISIBILITY);
		potion.setBasePotionData(data);
		item.setItemMeta(potion);
		return item;
	}
}