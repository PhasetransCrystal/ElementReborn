package net.archasmiel.thaumcraft.core.research;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.logging.Level;

public interface IScanEventHandler {
    ScanResult scanPhenomena(ItemStack var1, Level var2, Player var3);
}
