package gg.bridgesyndicate.kits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    BridgeInventory.setDefaultInventory(player);
                    player.sendMessage(ChatColor.GRAY + "You have received the default inventory. " +
                            "Customize your layout and type " +
                            ChatColor.DARK_AQUA + ChatColor.BOLD + "/kit save" +
                            ChatColor.RESET + ChatColor.GRAY + " when you are satisfied.");
                }
                return true;
            } else {
                player.sendMessage(ChatColor.GRAY + "saving kit.");
                return true;
            }
        } else {
            return false;
        }
    }
}
