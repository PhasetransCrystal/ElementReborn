package net.ssorangecaty.elementreborn.core.node;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

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
