package gg.bridgesyndicate.lobbyrules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    private static final String HAS_JOINED = ChatColor.GRAY + " has joined the lobby.";
    private static final String HAS_LEFT = ChatColor.GRAY + " has left the lobby.";

    public ChatHandler() {
    }

    @EventHandler
    public static void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        boolean badMessage = FilterMessages.containsBlacklistedWords(message);
        if (badMessage) {
            event.getPlayer().sendMessage(ChatColor.RED + "Your message was blocked as it may have contained offensive slurs.");
        } else {
            broadcastChatMessage(playerName, message);
        }
        event.setCancelled(true);
    }

    private static void broadcastChatMessage(String playerName, String message){
        Bukkit.broadcastMessage(ChatColor.AQUA + playerName + ChatColor.WHITE + ": " + message);
    }

    public static String getJoinMessage(String playerName){
        return ChatColor.AQUA + playerName + HAS_JOINED;
    }

    public static String getLeaveMessage(String playerName){
        return ChatColor.AQUA + playerName + HAS_LEFT;
    }

}
