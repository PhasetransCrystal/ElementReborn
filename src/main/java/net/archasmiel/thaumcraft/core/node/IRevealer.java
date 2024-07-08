package net.archasmiel.thaumcraft.core.node;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IRevealer {
    default boolean showNode(LivingEntity entity) {
        if(entity instanceof Player player){
            if(player.getMainHandItem().getItem() instanceof ShowMagicElementAble util) {
                return util.showNode();
            }

            if (player.getInventory().armor.get(0).getItem() instanceof ShowMagicElementAble util){
                return util.showNode();
            }
        }

        return true;
    }
}
