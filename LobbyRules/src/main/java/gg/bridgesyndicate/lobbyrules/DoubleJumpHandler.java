package gg.bridgesyndicate.lobbyrules;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;
import java.util.UUID;

public class DoubleJumpHandler implements Listener {

    private final ArrayList<UUID> hasAlreadyDoubleJumped;

    public DoubleJumpHandler(){
        this.hasAlreadyDoubleJumped = new ArrayList<>();
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        event.setCancelled (true);
        Player player = event.getPlayer();
        if (!hasAlreadyDoubleJumped.contains(player.getUniqueId())) {
            player.setAllowFlight(false);
            player.setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.7d).setY(1.0d));
            player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 1.0f, 0.8f);
            hasAlreadyDoubleJumped.add(player.getUniqueId());
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onTouchGround(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isOnGround()) { // deprecated because it can be spoofed with hacked clients. we don't care about that for the lobby
            hasAlreadyDoubleJumped.remove(player.getUniqueId());
            player.setAllowFlight(true);
        }
    }

}
