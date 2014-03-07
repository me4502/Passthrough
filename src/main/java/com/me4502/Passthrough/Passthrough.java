package com.me4502.Passthrough;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.bukkit.plugin.java.JavaPlugin;

import com.me4502.Passthrough.config.PassthroughConfiguration;

public class Passthrough extends JavaPlugin {

    PassthroughConfiguration config;

    @Override
    public void onEnable() {

        createDefaultConfiguration(new File(getDataFolder(), "config.yml"), "config.yml");
        config = new PassthroughConfiguration();
        config.load();

        getServer().getPluginManager().registerEvents(new PassthroughListener(this), this);
    }

    @Override
    public void onDisable() {

        config.save();
    }

    /**
     * Create a default configuration file from the .jar.
     *
     * @param actual      The destination file
     * @param defaultName The name of the file inside the jar's defaults folder
     */
    public void createDefaultConfiguration(File actual, String defaultName) {

        // Make parent directories
        File parent = actual.getParentFile();
        if (!parent.exists())
            parent.mkdirs();

        if (actual.exists())
            return;

        InputStream input = null;
        JarFile file = null;
        try {
            file = new JarFile(getFile());
            ZipEntry copy = file.getEntry("defaults/" + defaultName);
            if (copy == null) {
                file.close();
                throw new FileNotFoundException();
            }
            input = file.getInputStream(copy);
        } catch (IOException e) {
            getLogger().severe("Unable to read default configuration: " + defaultName);
        }

        if (input != null) {
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length = 0;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }

                getLogger().info("Default configuration file written: " + actual.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    file.close();
                    input.close();
                    if(output != null)
                        output.close();
                } catch (IOException ignored) {}
            }
        } else if (file != null)
            try {
                file.close();
            } catch (IOException ignored) {}
    }
}