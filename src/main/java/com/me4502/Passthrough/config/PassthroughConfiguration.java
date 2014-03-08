package com.me4502.Passthrough.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.me4502.Passthrough.Passthrough;

public class PassthroughConfiguration {

    public Set<WorldEntry> worldEntries;

    Passthrough plugin;

    public PassthroughConfiguration(Passthrough plugin) {

        this.plugin = plugin;
    }

    FileConfiguration config;

    public void load() {

        config = new YamlConfiguration();
        worldEntries = new HashSet<WorldEntry>();
        try {
            config.load(new File(plugin.getDataFolder(), "config.yml"));

            ConfigurationSection worldLinks = config.getConfigurationSection("world-links");

            for(String key : worldLinks.getKeys(false)) {

                ConfigurationSection worldLink = worldLinks.getConfigurationSection(key);

                WorldEntry entry = new WorldEntry(worldLink.getString("world-name"));
                entry.setAbove(worldLink.getString("above-world"));
                entry.setBeneath(worldLink.getString("below-world"));
                entry.setDepth(worldLink.getDouble("world-minimum-height", 0));
                entry.setHeight(worldLink.getDouble("world-maximum-height", 255));

                worldEntries.add(entry);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {

    }
}