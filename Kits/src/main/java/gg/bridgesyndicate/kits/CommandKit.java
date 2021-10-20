package gg.bridgesyndicate.kits;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandKit implements CommandExecutor {
    private static final String SYNDICATE_ENV = System.getenv("SYNDICATE_ENV");
    private static final String BUCKET_NAME = "syndicate-" + SYNDICATE_ENV + "-bridge-kit-layouts";
    private static AmazonS3 s3client = null;

    CommandKit() {
        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(new DefaultAwsRegionProviderChain().getRegion())
                .build();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    String playerKitJson = getKitJsonFromS3(player.getUniqueId());
                    String returnValue = BridgeInventory.setDefaultInventory(player, playerKitJson);
                    player.sendMessage(ChatColor.GRAY + "You have received " + returnValue + " inventory. " +
                            "Customize your layout and type " +
                            ChatColor.DARK_AQUA + ChatColor.BOLD + "/kit save" +
                            ChatColor.RESET + ChatColor.GRAY + " when you are satisfied.");
                }
                return true;
            } else {
                String inventoryLayoutJson = BridgeInventory.convertInventorySlotsToJSON(player);
                uploadJsonToS3(inventoryLayoutJson, player.getUniqueId());
                player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Kit saved!");
                player.getInventory().clear();
                return true;
            }
        } else {
            return false;
        }
    }

    private String objectNameFromPlayerUUID(UUID uniqueId){
        return uniqueId.toString() + ".json";
    }

    private String getKitJsonFromS3(UUID uniqueId) {
        S3Object s3Object;
        try {
            s3Object = s3client.getObject(BUCKET_NAME, objectNameFromPlayerUUID(uniqueId));
        } catch (Exception e) {
            return null;
        }
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return(text);
    }

    private void uploadJsonToS3(String inventoryLayoutJson, UUID uniqueId) {
        s3client.putObject(
                BUCKET_NAME,
                objectNameFromPlayerUUID(uniqueId),
                inventoryLayoutJson
        );
    }

    public static void main (String[] args) {
        System.out.println("hello world!");
        CommandKit commandKit = new CommandKit();
        commandKit.uploadJsonToS3("farts", UUID.randomUUID());
    }
}
