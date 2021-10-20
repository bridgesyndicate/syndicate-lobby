package gg.bridgesyndicate.kits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BridgeInventory {

    private static final short LIGHT_GRAY_TERRACOTTA = 8;

    public static String convertInventorySlotsToJSON(Player player) {
        Iterator<ItemStack> itemStackIterator = player.getInventory().iterator();
        HashMap<String, Integer> itemLocationHashMap = new HashMap<String, Integer>();
        int idx = 0;
        while (itemStackIterator.hasNext()){
            ItemStack itemStack = itemStackIterator.next();
            if (itemStack != null)
                itemLocationHashMap.put(itemStack.getType().toString(), idx);
            idx++;
        }
        String returnValue = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            returnValue = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(itemLocationHashMap);
        } catch (JsonProcessingException e) {
            System.out.println("Error. Could not serialize the inventory layout.");
            e.printStackTrace();
        }
        return(returnValue);
    }

    private static int getInventoryLocation(HashMap<String, Integer> map, String key, int defaultValue){
        return ( map.get(key) != null ) ? map.get(key) : defaultValue;
    }

    public static String setDefaultInventory(Player player, String playerKitJson) {
        HashMap<String, Integer> kitLayoutMap = null;
        String returnValue = "the default";
        try {
            ObjectMapper mapper = new ObjectMapper();
            kitLayoutMap = mapper.readValue(playerKitJson, HashMap.class);
            returnValue = "your custom";
        } catch (JsonProcessingException e) {
            System.out.println("Error. Cannot deserialize players saved layout. Will use defaults.");
            e.printStackTrace();
        }

        // HOTBAR
        player.getInventory().clear();
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD);

        ItemMeta itemMetaSword = ironSword.getItemMeta();
        itemMetaSword.spigot().setUnbreakable(true);
        ironSword.setItemMeta(itemMetaSword);
        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "IRON_SWORD", 0),
                ironSword);

        ItemStack bow = new ItemStack(Material.BOW);

        ItemMeta itemMetaBow = bow.getItemMeta();
        itemMetaBow.setDisplayName(ChatColor.GREEN + "Bow");

        ArrayList<String> bowLore = new ArrayList<>();
        bowLore.add(ChatColor.GRAY + "Arrows regenerate every");
        bowLore.add(ChatColor.GREEN + "3.5s" + ChatColor.GRAY + ". You can have a maximum");
        bowLore.add(ChatColor.GRAY + "of " + ChatColor.GREEN + "1 " + ChatColor.GRAY + "arrow at a time.");
        bowLore.add("");
        itemMetaBow.setLore(bowLore);

        itemMetaBow.spigot().setUnbreakable(true);
        bow.setItemMeta(itemMetaBow);

        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "BOW", 1),
                bow);

        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMetaPick = pick.getItemMeta();
        itemMetaPick.spigot().setUnbreakable(true);
        itemMetaPick.addEnchant(Enchantment.DIG_SPEED, 2, false);
        pick.setItemMeta(itemMetaPick);

        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "DIAMOND_PICKAXE", 2),
                pick);

        ItemStack blocks1 = new ItemStack(Material.STAINED_CLAY, 64);

        blocks1.setDurability(LIGHT_GRAY_TERRACOTTA);

        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "LIGHT_GRAY_TERRACOTTA", 3),
                blocks1);
        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "LIGHT_GRAY_TERRACOTTA", 4),
                blocks1);


        ItemStack gaps = new ItemStack(Material.GOLDEN_APPLE, 8);

        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "GOLDEN_APPLE", 5),
                gaps);

        ItemStack arrow = new ItemStack(Material.ARROW, 1);

        ItemMeta itemMetaArrow = arrow.getItemMeta();
        itemMetaArrow.setDisplayName(ChatColor.GREEN + "Arrow");

        ArrayList<String> arrowLore = new ArrayList<>();
        arrowLore.add(ChatColor.GRAY + "Regenerates every " + ChatColor.GREEN + "3.5s" + ChatColor.GRAY + "!");
        itemMetaArrow.setLore(arrowLore);

        arrow.setItemMeta(itemMetaArrow);

        player.getInventory().setItem(
                getInventoryLocation(kitLayoutMap, "ARROW", 8),
                arrow);
        return(returnValue);
    }
}
