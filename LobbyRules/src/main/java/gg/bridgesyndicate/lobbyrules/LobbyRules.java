package gg.bridgesyndicate.lobbyrules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyRules extends JavaPlugin implements Listener {

    private static int VOID_HEIGHT = 10;

    @Override
    public void onEnable() {
        System.out.println(this.getClass() + " is loading.");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChatHandler(),this);
        setGameRules();
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        teleportPlayerToSpawn(player);
        event.setJoinMessage(ChatHandler.getJoinMessage(player.getName()));

    }

    private void teleportPlayerToSpawn(Player player){
        Location respawn = new Location(Bukkit.getWorld("world"), -16.5, 65.0, 0.5, 0F, 0F);
        player.teleport(respawn);
    }

    @EventHandler
    private void onWeatherChange(final WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    private void foodChangeEvent(final FoodLevelChangeEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            final Player player = (Player) event.getEntity();
            event.setCancelled(true);
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    private void entityDamageEvent(final EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockPlaceEvent(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onBlockBreakEvent(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerShootBow(EntityShootBowEvent event){
        event.setCancelled(true);
        Player player = (Player) event.getEntity();
        player.updateInventory();
    }

    @EventHandler
    private void onPlayerEat(PlayerItemConsumeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerFallIntoVoid(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(player.getLocation().getY() < VOID_HEIGHT){
            teleportPlayerToSpawn(player);
        }
    }

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(ChatHandler.getLeaveMessage(event.getPlayer().getName()));
    }

    private void setGameRules() {
        Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");
        Bukkit.getWorld("world").setGameRuleValue("naturalRegeneration", "false");
        Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getWorld("world").setGameRuleValue("randomTickSpeed", "0");
    }
}
