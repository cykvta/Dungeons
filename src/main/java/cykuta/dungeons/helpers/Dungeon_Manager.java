package cykuta.dungeons.helpers;

import cykuta.dungeons.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

public class Dungeon_Manager{

    public boolean CreateDungeon(Location loc, String dungeonName, FileConfiguration config){
        String world = loc.getWorld().getName();
        double x = loc.getBlockX();
        double y = loc.getBlockY();
        double z = loc.getBlockZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();

        if (config.contains("Dungeons."+ dungeonName)) return false;
        config.set("Dungeons."+ dungeonName + ".location.world", world);
        config.set("Dungeons."+ dungeonName + ".location.x", x);
        config.set("Dungeons."+ dungeonName + ".location.y", y);
        config.set("Dungeons."+ dungeonName + ".location.z", z);
        config.set("Dungeons."+ dungeonName + ".location.yaw", yaw);
        config.set("Dungeons."+ dungeonName + ".location.pitch", pitch);
        return true;
    }

    public boolean DeleteDungeon(String dungeonName, FileConfiguration config){
        if (!(config.contains("Dungeons."+ dungeonName))) return false;
        config.set("Dungeons."+ dungeonName, null);
        return true;
    }

    public boolean TeleportDungeon(Player player, String dungeonName, FileConfiguration config){
        if (!(config.contains("Dungeons."+ dungeonName))) return false;
        String worlds = config.getString("Dungeons."+ dungeonName + ".location.world");
        assert worlds != null;
        World world = Bukkit.getWorld(worlds);
        double x = config.getDouble("Dungeons."+ dungeonName + ".location.x");
        double y = config.getDouble("Dungeons."+ dungeonName + ".location.y");
        double z = config.getDouble("Dungeons."+ dungeonName + ".location.z");
        float yaw = (float) config.getDouble("Dungeons."+ dungeonName + ".location.yaw");
        float pitch = (float) config.getDouble("Dungeons."+ dungeonName + ".location.pitch");
        String title = config.getString("Dungeons."+ dungeonName + ".title");

        Location loc = new Location(world,x,y,z);
        loc.setPitch(pitch);
        loc.setYaw(yaw);
        player.teleport(loc);
        player.sendTitle(title,null,10,20,10);

        return true;
    }

    public boolean SetTitle(String dungeonName, String title, FileConfiguration config){
        if (!(config.contains("Dungeons."+ dungeonName))) return false;
        config.set("Dungeons."+ dungeonName + ".title", Chat.color(title).replace("_"," "));
        return true;
    }

    public boolean SetLevel(String dungeonName, int level, FileConfiguration config){
            if (!(config.contains("Dungeons."+ dungeonName))) return false;
            config.set("Dungeons."+ dungeonName + ".minlevel", level);
            return true;
    }

    public String[] GetList(FileConfiguration config){
        Set<String> path = config.getConfigurationSection("Dungeons").getKeys(false);
        return path.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",");
    }
}
