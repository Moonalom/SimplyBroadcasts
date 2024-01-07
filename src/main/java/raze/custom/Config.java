package raze.custom;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private final File configFile;
    private FileConfiguration config;
    private boolean isLoaded = false;

    public Config(Plugin plugin) {
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public void loadConfig() {
        if (!isLoaded) {
            if (configFile.exists()) {
                config = YamlConfiguration.loadConfiguration(configFile);
            } else {
                config = YamlConfiguration.loadConfiguration(configFile);
                config.set("delay", 100);
                config.set("link_stub", "click");
                config.set("messages", getMessages());
                saveConfig();
            }

            isLoaded = true;
        }
    }
    private List<String> getMessages() {
        List<String> levels = new ArrayList<>();
        levels.add("Test messageю");
        levels.add("Test URL message $url. https://google.com");
        return levels;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
