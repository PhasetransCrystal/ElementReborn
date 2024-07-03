package net.archasmiel.thaumcraft.core.research;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class ScanResult {
    public byte type = 0;
    public Block id;
    public int meta;
    public Entity entity;
    public String phenomena;

    public ScanResult(byte type, Block blockId, Entity entity, String phenomena) {
        this.type = type;
        this.id = blockId;
        this.entity = entity;
        this.phenomena = phenomena;
    }

    public boolean equals(ScanResult obj) {
        if (this.type != obj.type) {
            return false;
        }

        return switch (this.type) {
            case 1 -> Objects.equals(this.id, obj.id);
            case 2 -> Objects.equals(this.entity.getUUID(), obj.entity.getUUID());
            case 3 -> Objects.equals(this.phenomena, obj.phenomena);
            default -> true;
        };
    }
}
