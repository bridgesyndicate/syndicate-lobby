package gg.bridgesyndicate.lobbyrules;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    private static final String HAS_JOINED = ChatColor.GRAY + " has joined the lobby.";
    private static final String HAS_LEFT = ChatColor.GRAY + " has left the lobby.";
    private static final String BUCKYTOUR_DISCORD_INVITE_LINK = "https://discord.gg/YNXtdEBEPv";

    public ChatHandler() {
    }

    @EventHandler
    public static void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "Lobby chat is temporarily disabled.");
        player.sendMessage("");
        sendDiscordInvite(player);

//        String message = event.getMessage();
//        boolean badMessage = FilterMessages.containsBlacklistedWords(message);
//        if (badMessage) {
//            event.getPlayer().sendMessage(ChatColor.RED + "Your message was blocked as it may have contained offensive slurs.");
//        } else {
//            broadcastChatMessage(playerName, message);
//        }
//        event.setCancelled(true);
    }

    private static void sendDiscordInvite(Player player) {
        TextComponent response = new TextComponent(
                        ChatColor.AQUA + "Click Here " +
                        ChatColor.GRAY + "if you would like to join our " +
                        ChatColor.AQUA + "Discord"
        );
        response.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                BUCKYTOUR_DISCORD_INVITE_LINK));
        player.spigot().sendMessage(response);
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
