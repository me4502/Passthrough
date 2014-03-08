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

        for(WorldEntry entry : plugin.config.worldEntries) {

            if(!entry.getName().equals(event.getPlayer().getLocation().getWorld().getName())) continue; //Wrong world.

            if(event.getTo().getY() > entry.getHeight()) {

                if(entry.getAbove() == null) return;

                double y = 0;
                if(plugin.config.getWorldEntryByWorld(entry.getAbove()) != null)
                    y = plugin.config.getWorldEntryByWorld(entry.getAbove()).getDepth()+1;

                event.getPlayer().teleport(new Location(Bukkit.getWorld(entry.getAbove()), event.getTo().getX(), y, event.getTo().getZ(), event.getTo().getYaw(), event.getTo().getPitch()));
            } else if(event.getTo().getY() < entry.getDepth()) {

                if(entry.getBeneath() == null) return;

                double y = 255;
                if(plugin.config.getWorldEntryByWorld(entry.getAbove()) != null)
                    y = plugin.config.getWorldEntryByWorld(entry.getAbove()).getHeight()-1;

                event.getPlayer().teleport(new Location(Bukkit.getWorld(entry.getBeneath()), event.getTo().getX(), y, event.getTo().getZ(), event.getTo().getYaw(), event.getTo().getPitch()));
            }
        }
    }
}