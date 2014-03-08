package com.me4502.Passthrough;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.me4502.Passthrough.config.WorldEntry;

public class PassthroughListener implements Listener {

    public Passthrough plugin;

    public PassthroughListener(Passthrough plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {

        if(event.getFrom().getBlockY() == event.getTo().getBlockY()) return; //We want to be as performance conscious as possible.

        WorldEntry entry = plugin.config.getWorldEntryByWorld(event.getTo().getWorld().getName());

        if(entry == null) return;

        if(event.getTo().getY() > entry.getHeight()) {

            if(entry.getAbove() == null) return;

            double y = 0;
            if(plugin.config.getWorldEntryByWorld(entry.getAbove()) != null)
                y = plugin.config.getWorldEntryByWorld(entry.getAbove()).getDepth()+1;

            double blockModifier = plugin.config.getWorldPropertiesByWorld(entry.getAbove()).getBlockModifier() / plugin.config.getWorldPropertiesByWorld(entry.getName()).getBlockModifier();

            event.getPlayer().teleport(new Location(Bukkit.getWorld(entry.getAbove()), event.getTo().getX() * blockModifier, y, event.getTo().getZ() * blockModifier, event.getTo().getYaw(), event.getTo().getPitch()));
        } else if(event.getTo().getY() < entry.getDepth()) {

            if(entry.getBeneath() == null) return;

            double y = 255;
            if(plugin.config.getWorldEntryByWorld(entry.getBeneath()) != null)
                y = plugin.config.getWorldEntryByWorld(entry.getBeneath()).getHeight()-2;

            double blockModifier = plugin.config.getWorldPropertiesByWorld(entry.getBeneath()).getBlockModifier() / plugin.config.getWorldPropertiesByWorld(entry.getName()).getBlockModifier();

            event.getPlayer().teleport(new Location(Bukkit.getWorld(entry.getBeneath()), event.getTo().getX() * blockModifier, y, event.getTo().getZ() * blockModifier, event.getTo().getYaw(), event.getTo().getPitch()));
        }
    }
}