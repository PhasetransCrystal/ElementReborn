package net.ssorangecaty.elementreborn.block.gen;

import com.mojang.serialization.MapCodec;
import net.ssorangecaty.elementreborn.block.ERBlockEntityRegister;
import net.ssorangecaty.elementreborn.block.entity.ArcaneWorkBenchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArcaneWorkBench extends AbstractFurnaceBlock {
    public static final MapCodec<ArcaneWorkBench> CODEC = simpleCodec(p -> new ArcaneWorkBench());

    public ArcaneWorkBench() {
        super(Properties.of());
    }

    @Override
    protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    protected void openContainer(Level level, @NotNull BlockPos blockPos, @NotNull Player player) {
        BlockEntity blockentity = level.getBlockEntity(blockPos);
        if (blockentity instanceof ArcaneWorkBenchBlockEntity entity) {
            player.openMenu(entity);
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new ArcaneWorkBenchBlockEntity(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_, ERBlockEntityRegister.ARCANE_WORKBENCH.get(), ArcaneWorkBenchBlockEntity::tick);
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState p_52240_, Level p_52241_, BlockPos p_52242_) {
        return new SimpleMenuProvider((p_52229_, p_52230_, p_52231_) -> new CraftingMenu(p_52229_, p_52230_, ContainerLevelAccess.create(p_52241_, p_52242_)), Component.translatable("container.crafting"));
    }
}
