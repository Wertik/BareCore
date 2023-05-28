package space.devport.partychat;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatPlugin extends JavaPlugin {

    public static final MiniMessage SERIALIZER = MiniMessage.builder().tags(
            TagResolver.builder()
                    .resolver(StandardTags.defaults())
                    .resolver(StandardTags.gradient())
                    .resolver(StandardTags.transition())
                    .build()
    ).build();

    private final PlayerColorManager colorManager = new PlayerColorManager(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        //
    }

    public PlayerColorManager getColorManager() {
        return colorManager;
    }
}
