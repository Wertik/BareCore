package space.devport.partychat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerListener implements Listener {

    private final ChatPlugin plugin;

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

    public PlayerListener(ChatPlugin plugin) {
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
        final String message = String.format(Locale.US, "<transition:%s:%.6f>%s</transition>", String.join(":", transitionColors), phase, ChatPlugin.SERIALIZER.serialize(event.originalMessage()));

        final Component result = ChatPlugin.SERIALIZER.deserialize(String.format("%s <white>|</white> %s", playerName, message));

        event.renderer(
                (player1, component, component1, audience) -> result
        );
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final String playerName = plugin.getColorManager().getOrAssignGradient(event.getPlayer().getUniqueId())
                .generateGradientTag(event.getPlayer().getName());

        final Component message = ChatPlugin.SERIALIZER.deserialize(String.format("<color:#86FF33>»</color:#86FF33> %s <color:#C3C3C3>joined the server.</color:#C3C3C3>", playerName));

        event.joinMessage(message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final String playerName = plugin.getColorManager().getOrAssignGradient(event.getPlayer().getUniqueId())
                .generateGradientTag(event.getPlayer().getName());

        final Component message = ChatPlugin.SERIALIZER.deserialize(String.format("<color:#FF2465>«</color:#FF2465> %s <color:#C3C3C3>left the server.</color:#C3C3C3>", playerName));

        event.quitMessage(message);
    }
}
