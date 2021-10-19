package gg.bridgesyndicate.kits;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandKit implements CommandExecutor {
    private static final String BUCKET_NAME = "syndicate-bridge-kit-layouts";

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
                String inventoryLayoutJson = BridgeInventory.convertInventorySlotsToJSON(player);
                uploadJsonToS3(inventoryLayoutJson, player.getUniqueId());
                return true;
            }
        } else {
            return false;
        }
    }

    public void uploadJsonToS3(String inventoryLayoutJson, UUID uniqueId) {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(new DefaultAwsRegionProviderChain().getRegion())
                .build();
        s3client.putObject(
                BUCKET_NAME,
                uniqueId.toString(),
                inventoryLayoutJson
        );
    }
}
