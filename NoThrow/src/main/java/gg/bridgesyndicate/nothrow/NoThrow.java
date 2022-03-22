package gg.bridgesyndicate.nothrow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoThrow extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println(this.getClass() + " is loading.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isRightClick()) event.setCancelled(true);
        if (event.isShiftClick()) event.setCancelled(true);
        if (event.getClickedInventory() != event.getWhoClicked().getInventory()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDragItem(InventoryDragEvent event) {
        event.setCancelled(true);
    }
}
