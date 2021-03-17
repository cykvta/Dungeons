package cykuta.dungeons.commands;

import cykuta.dungeons.Dungeons;
import cykuta.dungeons.helpers.Dungeon_Manager;
import cykuta.dungeons.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Dungeon implements CommandExecutor {
    public Dungeons plugin;
    public Command_Dungeon(Dungeons plugin) {
        this.plugin = plugin;
    };

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("dungeons.admin"))) return false;
        if (args.length == 0){
            sender.sendMessage(Chat.color("&d&lDungeons"));
            sender.sendMessage(Chat.color("&e/dungeon create <id>"));
            sender.sendMessage(Chat.color("&e/dungeon remove <id>"));
            sender.sendMessage(Chat.color("&e/dungeon tp <id>"));
            sender.sendMessage(Chat.color("&e/dungeon setlevel <id> <level>"));
            sender.sendMessage(Chat.color("&e/dungeon settitle <id> <title> &7//All '_' replaced with spaces "));
            return true;
        }
        if (!(sender instanceof Player)) return false;

        FileConfiguration config = plugin.getConfig();
        Player player = (Player) sender;
        Dungeon_Manager creator = new Dungeon_Manager();
        switch (args[0]){
            default: player.sendMessage(Chat.color("&cSyntax Error.")); break;

            case "create":
                if (!creator.CreateDungeon(player.getLocation(), args[1], config)){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, dungeon "+args[1]+" already exist."));
                    break;
                }
                player.sendMessage(
                        Chat.color(Chat.prefix + "&a"+args[1]+" Successfully created."));
                plugin.saveConfig();
                break;

            case "remove":
                if (!creator.DeleteDungeon(args[1], config)){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, dungeon "+args[1]+" not exist."));
                    break;
                }
                player.sendMessage(
                        Chat.color(Chat.prefix + "&a"+args[1]+" Successfully deleted."));
                plugin.saveConfig();
                break;

            case "tp":
                if (!creator.TeleportDungeon(player, args[1], config)){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, dungeon "+args[1]+" not exist."));
                    break;
                }
                player.sendMessage(
                        Chat.color(Chat.prefix + "&a"+args[1]+" Successfully teleported."));
                break;

            case "settitle":
                try {
                    if (!creator.SetTitle(args[1], args[2], config)) {
                        player.sendMessage(
                                Chat.color(Chat.prefix + "&cError, cant set tittle on " + args[1] + "."));
                        break;
                    }
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&a" + args[1] + " Title was set successfully."));
                    plugin.saveConfig();
                    break;
                }catch (Exception e){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, cant set title on "+args[1]+"."));
                }

            case "setlevel":
                try {
                    int value = Integer.parseInt(args[2]);
                    if (!creator.SetLevel(args[1], value, config)){
                        player.sendMessage(
                                Chat.color(Chat.prefix + "&cError, cant set level on "+args[1]+"."));
                        break;
                    }
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&a"+args[1]+" MinLevel was set successfully."));
                    plugin.saveConfig();
                    break;

                }catch (Exception e){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, cant set level on "+args[1]+"."));
                    break;
                }
        }
        return true;
    }
}
