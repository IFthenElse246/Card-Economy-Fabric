package net.ianfinity.cardecon.block.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.block.entity.CardReaderEntity;
import net.ianfinity.cardecon.item.items.CreditCard;
import net.ianfinity.cardecon.networking.NetworkingIdentifiers;
import net.ianfinity.cardecon.screens.CardReaderGui;
import net.ianfinity.cardecon.screens.CardReaderScreen;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CardReader extends HorizontalFacingBlock implements BlockEntityProvider {
    public CardReader(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return Block.createCuboidShape(3, 0, 3, 13, 2, 13);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (world.isClient()) {
            return;
        }

        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof CardReaderEntity) {
            if (placer == null) {
                CardEcon.LOGGER.warn("card_reader placed at " + pos.toString() + " has no placer.");
                return;
            }

            final UUID uuid = placer.getUuid();

            ((CardReaderEntity) entity).setOwnerUuid(uuid);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CardReaderEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CardReaderEntity readerEntity) {  // make sure the block entity exists
            if (world.isClient()) {
                if (player.getMainHandStack().getItem() instanceof CreditCard) {    // make sure they are holding their credit card
                    if (readerEntity.getOwnerUuid() != null) { // block has an owner
                        MinecraftClient.getInstance().setScreen(new CardReaderScreen(new CardReaderGui())); // open the gui
                    }
                    return ActionResult.success(true);  // send to server
                } else {
                    return ActionResult.FAIL;   // don't send to server
                }
            } else {
                if (readerEntity.getOwnerUuid() == null)    // check if the block doesn't already have an owner
                {
                    readerEntity.setOwnerUuid(player.getUuid());    // set this player to the owner
                    ServerPlayNetworking.send((ServerPlayerEntity) player, NetworkingIdentifiers.NOTIFY_OWNER, PacketByteBufs.empty());
                }
                return ActionResult.success(true);
            }
        } else {
            return ActionResult.FAIL;
        }
    }
}
