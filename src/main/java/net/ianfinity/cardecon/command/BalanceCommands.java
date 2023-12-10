package net.ianfinity.cardecon.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.ianfinity.cardecon.data.PlayerEconDat;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NumberRangeArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class BalanceCommands {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.balance.remove.fail"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("balance").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("add")
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                .then(CommandManager.argument("amount", LongArgumentType.longArg(0))
                .executes(context ->
                    BalanceCommands.add(
                            (ServerCommandSource) context.getSource(),
                            EntityArgumentType.getPlayers(context, "targets"),
                            LongArgumentType.getLong(context, "amount")
                    )
                )))));
        dispatcher.register(CommandManager.literal("balance").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("remove")
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                .then(CommandManager.argument("amount", LongArgumentType.longArg(0))
                .executes(context ->
                        BalanceCommands.remove(
                                (ServerCommandSource) context.getSource(),
                                EntityArgumentType.getPlayers(context, "targets"),
                                LongArgumentType.getLong(context, "amount")
                        )
                )))));
        dispatcher.register(CommandManager.literal("balance").requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("get")
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                .executes(context ->
                        BalanceCommands.get(
                                (ServerCommandSource) context.getSource(),
                                EntityArgumentType.getPlayers(context, "targets")
                        )
                ))));
    }


    public static int add(ServerCommandSource source, Collection<? extends ServerPlayerEntity> targets, long amount){
        for (ServerPlayerEntity player : targets) {
            PlayerEconDat dat = PlayerEconDat.getPlayerData(player.getUuid());
            dat.setCurrency(dat.getCurrency() + amount);
        }
        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.balance.add.success.single", amount, ((ServerPlayerEntity)targets.iterator().next()).getDisplayName()), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.balance.add.success.multiple", amount, targets.size()), true);
        }
        return 1;
    }

    public static int remove(ServerCommandSource source, Collection<? extends ServerPlayerEntity> targets, long amount) throws CommandSyntaxException {
        long change = 0;
        long balance = 0;
        for (ServerPlayerEntity player : targets) {
            PlayerEconDat dat = PlayerEconDat.getPlayerData(player.getUuid());
            long oldCurrency = dat.getCurrency();
            long newCurrency = Math.max(oldCurrency - amount, 0);
            change += oldCurrency - newCurrency;
            dat.setCurrency(newCurrency);
            balance = newCurrency;
        }

        if (change == 0) {
            throw FAILED_EXCEPTION.create();
        }

        final long finalChange = change;
        if (targets.size() == 1) {
            final long finalBalance = balance;
            source.sendFeedback(() -> Text.translatable("commands.balance.remove.success.single", finalChange, ((ServerPlayerEntity)targets.iterator().next()).getDisplayName(), finalBalance), true);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.balance.remove.success.multiple", finalChange, targets.size(), amount), true);
        }
        return (int) change;
    }

    public static int get(ServerCommandSource source, Collection<? extends ServerPlayerEntity> targets) {
        long balance = 0;
        for (ServerPlayerEntity player : targets) {
            PlayerEconDat dat = PlayerEconDat.getPlayerData(player.getUuid());
            balance += dat.getCurrency();
        }

        final long finalBalance = balance;
        if (targets.size() == 1) {
            source.sendFeedback(() -> Text.translatable("commands.balance.get.success.single", ((ServerPlayerEntity)targets.iterator().next()).getDisplayName(), finalBalance), false);
        } else {
            source.sendFeedback(() -> Text.translatable("commands.balance.get.success.multiple", targets.size(), finalBalance), false);
        }
        return (int) balance;
    }
}
