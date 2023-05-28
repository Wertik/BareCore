package space.devport.partychat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.devport.partychat.ChatPlugin;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatListener implements Listener {

    private final ChatPlugin plugin;

    private final MiniMessage serializer = MiniMessage.builder().tags(
            TagResolver.builder()
                    .resolver(StandardTags.defaults())
                    .resolver(StandardTags.gradient())
                    .resolver(StandardTags.transition())
                    .build()
    ).build();

    private final String[] transitionColors = {
            "#FFFBDD",
            "#FFDDDD",
            "#EEFFDB",
            "#D7FFFA",
            "#D4DFFF",
            "#FFD1F0",
            "#F0D1FF",
            "#ECFFFB"
    };

    private final AtomicInteger messageCount = new AtomicInteger(0);

    public ChatListener(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        // Get a gradient for the player
        final String playerName = plugin.getColorManager()
                .getOrAssignGradient(player.getUniqueId())
                .generateGradientTag(player.getName());

        // Use transition for chat messages

        // Calculate phase between 0 and 1 for the transition
        double phase = Math.sin((Math.PI / 3) * messageCount.getAndIncrement() / transitionColors.length);

        plugin.getServer().getLogger().info(String.format("Phase: %.6f", phase));

        // Format a message with a transition
        final String message = String.format(Locale.US, "<transition:%s:%.6f>%s</transition>", String.join(":", transitionColors), phase, serializer.serialize(event.originalMessage()));

        final Component result = serializer.deserialize(String.format("%s <white>|</white> %s", playerName, message));

        event.renderer(
                (player1, component, component1, audience) -> result
        );
    }
}
