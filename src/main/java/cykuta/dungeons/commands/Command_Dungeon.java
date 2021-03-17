package cykuta.dungeons.commands;

import cykuta.dungeons.Dungeons;
import cykuta.dungeons.helpers.Dungeon_Manager;
import cykuta.dungeons.utils.Chat;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
            sender.sendMessage(Chat.color("&d&lDungeons &7--------------------------"));
            sender.sendMessage(Chat.color("&e/dungeon create <id> &7//Create new dungeon"));
            sender.sendMessage(Chat.color("&e/dungeon remove <id> &7//Remove dungeon"));
            sender.sendMessage(Chat.color("&e/dungeon tp <id>  &7//Teleport to dungeon"));
            sender.sendMessage(Chat.color("&e/dungeon setlevel <id> <level> &7//Set minimum level to enter the dungeon"));
            sender.sendMessage(Chat.color("&e/dungeon settitle <id> <title> &7//All '_' replaced with spaces "));
            sender.sendMessage(Chat.color("&e/dungeon seticon <id> &7//Set item in your main hand as a icon in GUI"));
            sender.sendMessage(Chat.color("&e/dungeon reload &7//Reload plugin config"));
            sender.sendMessage(Chat.color("&7------------------------------------"));
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
                        Chat.color(Chat.prefix +args[1]+" Successfully created."));
                plugin.saveConfig();
                break;

            case "remove":
                if (!creator.DeleteDungeon(args[1], config)){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, dungeon "+args[1]+" not exist."));
                    break;
                }
                player.sendMessage(
                        Chat.color(Chat.prefix +args[1]+" Successfully deleted."));
                plugin.saveConfig();
                break;

            case "tp":
                if (!creator.TeleportDungeon(player, args[1], config)){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, dungeon "+args[1]+" not exist."));
                    break;
                }
                player.sendMessage(
                        Chat.color(Chat.prefix + args[1]+" Successfully teleported."));
                break;

            case "settitle":
                try {
                    if (!creator.SetTitle(args[1], args[2], config)) {
                        player.sendMessage(
                                Chat.color(Chat.prefix + "&cError, cant set tittle on " + args[1] + "."));
                        break;
                    }
                    player.sendMessage(
                            Chat.color(Chat.prefix + args[1] + " Title was set successfully."));
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
                            Chat.color(Chat.prefix +args[1]+" MinLevel was set successfully."));
                    plugin.saveConfig();
                    break;

                }catch (Exception e){
                    player.sendMessage(
                            Chat.color(Chat.prefix + "&cError, cant set level on "+args[1]+"."));
                    break;
                }
            case "seticon":
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() != Material.AIR && creator.SetItem(args[1], item, config)){
                    player.sendMessage(
                            Chat.color(Chat.prefix +args[1]+" Item was set successfully."));
                    plugin.saveConfig();
                    break;
                }
                player.sendMessage(
                        Chat.color(Chat.prefix + "&cError, cant set item on "+args[1]+"."));
                break;
            case "reload":
                player.sendMessage(
                        Chat.color(Chat.prefix + "Plugin was set successfully relaoded."));
                plugin.reloadConfig();
        }
        return true;
    }
}
