package cykuta.dungeons.utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String prefix = Chat.color("&d&lDungeons ");

    public static String color(String text){
        String msg = ChatColor.translateAlternateColorCodes('&', text);
     return msg;
    }
}
