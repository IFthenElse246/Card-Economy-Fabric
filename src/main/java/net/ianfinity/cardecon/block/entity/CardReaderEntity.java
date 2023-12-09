package net.ianfinity.cardecon.block.entity;

import net.ianfinity.cardecon.CardEcon;
import net.ianfinity.cardecon.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CardReaderEntity extends BlockEntity {
    @Nullable
    private UUID OwnerUuid = null;

    public CardReaderEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CARD_READER_ENTITY, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        if (OwnerUuid != null)
            nbt.putUuid("Owner", OwnerUuid);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        try {
            OwnerUuid = nbt.getUuid("Owner");
        } catch (Exception e) {
            CardEcon.LOGGER.warn("card_reader at " + pos.toString() + " has no NBT Owner.");
        }
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        OwnerUuid = uuid;
    }

    @Nullable
    public UUID getOwnerUuid() {
        return OwnerUuid;
    }
}
