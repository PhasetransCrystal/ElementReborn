package net.archasmiel.thaumcraft.block;

import net.archasmiel.thaumcraft.Thaumcraft;
import net.archasmiel.thaumcraft.block.gen.ArcaneWorkBench;
import net.archasmiel.thaumcraft.item.TCItemRegister;
import net.archasmiel.thaumcraft.util.MemorizeSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCBlockRegister {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thaumcraft.MODID);

    public static final DeferredBlock<Block> ARCANE_WORKBENCH = register("arcane_workbench", ArcaneWorkBench::new, new Item.Properties());

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block, Item.Properties properties) {
        final Supplier<T> newBlock = new MemorizeSupplier<>(block);
        TCItemRegister.register(name, () -> new BlockItem(newBlock.get(), properties));
        return BLOCKS.register(name, newBlock);
    }
}
