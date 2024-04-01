package dev.eposs.nodeath;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.UUID;

public class ConfigCommand {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("toggledeath")
                        .requires(source -> source.hasPermissionLevel(4))
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .executes(context -> {
                                    ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                    UUID uuid = player.getUuid();

                                    if (Nodeath.playerList.contains(uuid)) {
                                        Nodeath.playerList.remove(uuid);
                                        context.getSource().sendFeedback(() -> Text.literal(player.getName().getString() + " can die again."), true);
                                    } else {
                                        Nodeath.playerList.add(uuid);
                                        context.getSource().sendFeedback(() -> Text.literal(player.getName().getString() + " can no longer die."), true);
                                    }

                                    return 1;
                                })
                        )
        ));
    }
}
