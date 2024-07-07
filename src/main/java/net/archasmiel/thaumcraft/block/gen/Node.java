package net.archasmiel.thaumcraft.block.gen;

import com.mojang.serialization.MapCodec;
import net.archasmiel.thaumcraft.block.entity.NodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Node extends BaseEntityBlock {

    public static final MapCodec<Node> CODEC = simpleCodec((p) -> new Node());
    public Node() {
        super(Properties.of());
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    protected ItemInteractionResult useItemOn(
            @NotNull ItemStack itemStack,
            @NotNull BlockState blockState,
            @NotNull Level level,
            @NotNull BlockPos blockPos,
            @NotNull Player player,
            @NotNull InteractionHand interactionHand,
            @NotNull BlockHitResult hitResult
    ) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new NodeBlockEntity(blockPos, blockState);
    }



}
