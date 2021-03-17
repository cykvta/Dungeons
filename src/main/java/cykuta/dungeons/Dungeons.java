package cykuta.dungeons;

import cykuta.dungeons.commands.Command_Dungeon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Dungeons extends JavaPlugin {
    public String cfgPath;

    @Override
    public void onEnable() {
        this.getCommand("dungeon").setExecutor(new Command_Dungeon(this));
        LoadCfg();
    }

    public void LoadCfg(){
        File config = new File(this.getDataFolder(),"config.yml");
        cfgPath = config.getPath();
        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}
