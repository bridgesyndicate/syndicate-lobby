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

    public static void setDefaultInventory(Player player) {
        // HOTBAR
        player.getInventory().clear();
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD);

        ItemMeta itemMetaSword = ironSword.getItemMeta();
        itemMetaSword.spigot().setUnbreakable(true);
        ironSword.setItemMeta(itemMetaSword);

        player.getInventory().setItem(0, ironSword);

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

        player.getInventory().setItem(1, bow);
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMetaPick = pick.getItemMeta();
        itemMetaPick.spigot().setUnbreakable(true);
        itemMetaPick.addEnchant(Enchantment.DIG_SPEED, 2, false);
        pick.setItemMeta(itemMetaPick);
        player.getInventory().setItem(2, pick);

        ItemStack blocks1 = new ItemStack(Material.STAINED_CLAY, 64);

        blocks1.setDurability(LIGHT_GRAY_TERRACOTTA);

        player.getInventory().setItem(3, blocks1);
        player.getInventory().setItem(4, blocks1);

        ItemStack gaps = new ItemStack(Material.GOLDEN_APPLE, 8);

        player.getInventory().setItem(5, gaps);

        ItemStack arrow = new ItemStack(Material.ARROW, 1);

        ItemMeta itemMetaArrow = arrow.getItemMeta();
        itemMetaArrow.setDisplayName(ChatColor.GREEN + "Arrow");

        ArrayList<String> arrowLore = new ArrayList<>();
        arrowLore.add(ChatColor.GRAY + "Regenerates every " + ChatColor.GREEN + "3.5s" + ChatColor.GRAY + "!");
        itemMetaArrow.setLore(arrowLore);

        arrow.setItemMeta(itemMetaArrow);
        player.getInventory().setItem(8, arrow);
    }
}
