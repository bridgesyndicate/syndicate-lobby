package gg.bridgesyndicate.kits;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kits extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println(this.getClass() + " is loading.");
        this.getCommand("kit").setExecutor(new CommandKit());
    }
}


