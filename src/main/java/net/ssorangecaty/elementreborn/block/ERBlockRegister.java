package net.ssorangecaty.elementreborn.block;

import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.block.gen.ArcaneWorkBench;
import net.ssorangecaty.elementreborn.block.gen.Node;
import net.ssorangecaty.elementreborn.item.ERItemRegister;
import net.ssorangecaty.elementreborn.util.MemorizeSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ERBlockRegister {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ElementReborn.MODID);

    public static final DeferredBlock<Block> ARCANE_WORKBENCH = register("arcane_workbench", ArcaneWorkBench::new, new Item.Properties());
    public static final DeferredBlock<Block> NODE = register("node", Node::new, new Item.Properties());


    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block, Item.Properties properties) {
        final Supplier<T> newBlock = new MemorizeSupplier<>(block);
        ERItemRegister.register(name, () -> new BlockItem(newBlock.get(), properties));
        return BLOCKS.register(name, newBlock);
    }
}
