package space.devport.partychat;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerColorManager {

    private final ChatPlugin plugin;

    private final Gradient[] availableGradients = new Gradient[]{
            new Gradient("#35FF42", "#97FF8E"),
            new Gradient("#FFFC1D", "#FFF892"),
            new Gradient("#FF6224", "#FFC299"),
            new Gradient("#39FAFF", "#B6FBFF"),
            new Gradient("#F423FF", "#FFB0F8")
    };

    private final Map<UUID, Gradient> playerGradients = new HashMap<>();

    public PlayerColorManager(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public Gradient getOrAssignGradient(UUID uniqueId) {
        Optional<Gradient> gradient = this.getPlayerGradient(uniqueId);
        if (gradient.isEmpty()) {
            assignPlayerGradient(uniqueId);
            return this.playerGradients.get(uniqueId);
        }
        return gradient.get();
    }

    public Optional<Gradient> getPlayerGradient(UUID uniqueId) {
        return Optional.ofNullable(this.playerGradients.get(uniqueId));
    }

    public void assignPlayerGradient(@NotNull UUID uniqueId) {
        Gradient gradient = availableGradients[playerGradients.size() % availableGradients.length];
        this.playerGradients.put(uniqueId, gradient);
    }
}
