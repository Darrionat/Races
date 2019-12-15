package me.Darrionat.Races.Commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.GUI.PotionGUI;
import me.Darrionat.Races.GUI.RaceGUI;
import me.Darrionat.Races.Utils.EffectTimer;
import me.Darrionat.Races.Utils.Utils;

public class RacesCommand implements CommandExecutor {

	private Main plugin;

	public RacesCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("race").setExecutor(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ConfigurationSection config = plugin.getConfig();
		FilesManager filesmanager = new FilesManager(plugin);
		FileConfiguration playerdata = filesmanager.getDataConfig("playerdata");
		if (!(sender instanceof Player)) {
			sender.sendMessage(Utils.chat(config.getString("messages.NotPlayer")));
			return true;
		}
		Player p = (Player) sender;
		String uuid = p.getUniqueId().toString();
		String commandPerm = "races.command";
		if (!p.hasPermission(commandPerm)) {
			p.sendMessage(Utils.chat(config.getString("messages.NoPermission").replace("%permission%", commandPerm)));
			return true;
		}
		if (args.length == 0) {
			String race = playerdata.getString(uuid);
			p.sendMessage(Utils.chat("&eYou are the " + race + " race."));
			p.sendMessage(Utils.chat("&eYou can change this by using &6/race change"));
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("change")) {
			// The player should already be in there. So this is a case where an error
			// happened somewhere
			String changePerm = "races.change";
			if (!p.hasPermission(changePerm)) {
				p.sendMessage(
						Utils.chat(config.getString("messages.NoPermission").replace("%permission%", changePerm)));
				return true;
			}
			if (playerdata.getString(uuid) == null) {
				p.openInventory(RaceGUI.GUI(p, plugin));
				return true;
			}
			p.openInventory(PotionGUI.GUI(p, plugin));
			return true;

		}
		if (args[0].equalsIgnoreCase("admin")) {
			String adminPerm = "races.admin";
			if (!p.hasPermission(adminPerm)) {
				p.sendMessage(Utils.chat(config.getString("messages.NoPermission").replace("%permission%", adminPerm)));
				return true;
			}
			// /race admin Player [race]
			if (args.length != 3) {
				p.sendMessage(Utils.chat("&4Error: Use the following format"));
				p.sendMessage(Utils.chat("&c/race admin {Username} {Elf/Dwarf/Giant/Demon}"));
				return true;
			}
			if (Bukkit.getOfflinePlayer(args[1]) == null) {
				p.sendMessage(Utils.chat("&4Error: &cThat player does not exist."));
				return true;
			}
			if (playerdata.getString(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()) == null) {
				p.sendMessage(Utils.chat("&4Error: &cThat player does not have a race."));
				return true;
			}
			List<String> types = new ArrayList<String>();
			types.add("elf");
			types.add("dwarf");
			types.add("giant");
			types.add("demon");
			if (!types.contains(args[2].toLowerCase())) {
				p.sendMessage(Utils.chat("&4Error: &cThat is not a valid race, please use elf/dwarf/giant/demon."));
				return true;
			}
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			playerdata.set(target.getUniqueId().toString(), args[2]);
			File playerdataFile = filesmanager.getFile("playerdata");
			try {
				playerdata.save(playerdataFile);
				p.sendMessage(Utils.chat("&eChanged " + target.getName() + "'s race to " + args[2]));
				Bukkit.getScheduler().cancelTasks(plugin);
				filesmanager.reloadFile("playerdata");
				EffectTimer timer = new EffectTimer(plugin);
				timer.startEffectTimer();
				return true;
			} catch (IOException e) {
				p.sendMessage(Utils.chat("&cThere was an error saving your data. Please contact an Administrator"));
				System.out.println(
						Utils.chat("&4There was an error saving this player's data to playerdata.yml. Stacktrace:"));
				e.printStackTrace();
				return true;
			}
		}
		p.sendMessage(Utils.chat("&cRaces By Darrionat"));
		p.sendMessage(Utils.chat("&6/race - Displays information about your race"));
		p.sendMessage(Utils.chat("&6/race change - Allows you to change your race"));
		p.sendMessage(Utils.chat("&6/race admin [Player] [Race] - Force changes a player's race"));
		return true;
	}

	public void reloadPlayerDataOneSecDelay() {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {

			}
		}, 1L);// 60 L == 3 sec, 20 ticks == 1 sec
	}
}
