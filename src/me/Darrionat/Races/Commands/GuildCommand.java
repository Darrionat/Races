package me.Darrionat.Races.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Darrionat.Races.Main;
import me.Darrionat.Races.Files.FilesManager;
import me.Darrionat.Races.GUI.GuildGUI;
import me.Darrionat.Races.Utils.Utils;

public class GuildCommand implements CommandExecutor {

	private Main plugin;

	public GuildCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("guild").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ConfigurationSection config = plugin.getConfig();
		FilesManager filesmanager = new FilesManager(plugin);
		FileConfiguration guildData = filesmanager.getDataConfig("guilddata");

		if (!(sender instanceof Player)) {
			sender.sendMessage(Utils.chat(config.getString("messages.NotPlayer")));
			return true;
		}
		Player p = (Player) sender;
		String uuid = p.getUniqueId().toString();
		if (guildData.getString(uuid) != null) {
			p.sendMessage(Utils.chat(config.getString("messages.AlreadyInGuild")));
			return true;
		}
		p.openInventory(GuildGUI.GUI(p, plugin));
		return true;
	}
}
