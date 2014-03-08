package com.me4502.Passthrough.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.me4502.Passthrough.Passthrough;

public class PassthroughConfiguration {

    public Set<WorldEntry> worldEntries;
    public Set<WorldProperties> worldProperties;

    Passthrough plugin;

    public PassthroughConfiguration(Passthrough plugin) {

        this.plugin = plugin;
    }

    FileConfiguration config;

    public void load() {

        config = new YamlConfiguration();
        worldEntries = new HashSet<WorldEntry>();
        worldProperties = new HashSet<WorldProperties>();
        try {
            config.load(new File(plugin.getDataFolder(), "config.yml"));

            ConfigurationSection worldLinks = config.getConfigurationSection("world-links");
            if(worldLinks == null) {
                worldLinks = config.createSection("world-links");
            }

            for(World world : Bukkit.getWorlds()) {
                if(worldLinks.getConfigurationSection(world.getName()) == null)
                    worldLinks.createSection(world.getName());
            }

            for(String key : worldLinks.getKeys(false)) {

                ConfigurationSection worldLink = worldLinks.getConfigurationSection(key);

                WorldEntry entry = new WorldEntry(key);
                entry.setAbove(worldLink.getString("above-world"));
                entry.setBeneath(worldLink.getString("below-world"));
                entry.setDepth(worldLink.getDouble("world-minimum-height", 0));
                entry.setHeight(worldLink.getDouble("world-maximum-height", 255));

                worldEntries.add(entry);
            }

            ConfigurationSection worldPropertiesConfig = config.getConfigurationSection("world-properties");
            if(worldPropertiesConfig == null) {
                worldPropertiesConfig = config.createSection("world-properties");
            }

            for(World world : Bukkit.getWorlds()) {
                if(worldPropertiesConfig.getConfigurationSection(world.getName()) == null)
                    worldPropertiesConfig.createSection(world.getName());
            }

            for(String key : worldPropertiesConfig.getKeys(false)) {

                ConfigurationSection worldProperty = worldPropertiesConfig.getConfigurationSection(key);

                WorldProperties entry = new WorldProperties(key);

                entry.setBlockModifier(worldProperty.getInt("block-modifier", 1));

                worldProperties.add(entry);
            }

            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {

    }

    public WorldEntry getWorldEntryByWorld(String worldName) {

        WorldEntry ent = null;

        for(WorldEntry entry : worldEntries) {

            if(entry.getName().equals(worldName)) {
                ent = entry;
                break;
            }
        }

        return ent;
    }

    public WorldProperties getWorldPropertiesByWorld(String worldName) {

        WorldProperties ent = null;

        for(WorldProperties entry : worldProperties) {

            if(entry.getName().equals(worldName)) {
                ent = entry;
                break;
            }
        }

        return ent;
    }
}