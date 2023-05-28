package space.devport.bare;

import org.bukkit.plugin.java.JavaPlugin;
import space.devport.bare.chat.ChatListener;
import space.devport.bare.chat.PlayerColorManager;

public class CorePlugin extends JavaPlugin {

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
