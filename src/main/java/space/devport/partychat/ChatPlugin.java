package space.devport.partychat;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatPlugin extends JavaPlugin {

    private final PlayerColorManager colorManager = new PlayerColorManager(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    @Override
    public void onDisable() {
        //
    }

    public PlayerColorManager getColorManager() {
        return colorManager;
    }
}
