package com.eintosti.buildsystem.listener;

import com.eintosti.buildsystem.BuildSystem;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.session.SessionManager;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * Temporary workaround for https://github.com/IntellectualSites/FastAsyncWorldEdit/issues/1671.
 */
public class Issue1671Listener implements Listener {

    public Issue1671Listener(BuildSystem plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static void loadWorldSession(Player player, World world) {
        com.sk89q.worldedit.entity.Player wePlayer = BukkitAdapter.adapt(player);
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);

        SessionManager sessionManager = WorldEdit.getInstance().getSessionManager();
        LocalSession session = sessionManager.getIfPresent(wePlayer);

        session.loadSessionHistoryFromDisk(wePlayer.getUniqueId(), weWorld);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        loadWorldSession(player, world);
    }
}
