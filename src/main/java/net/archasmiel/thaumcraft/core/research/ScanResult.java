package net.archasmiel.thaumcraft.core.research;

import net.minecraft.world.entity.Entity;

import java.util.Objects;

public class ScanResult {
    public byte type = 0;
    public int id;
    public int meta;
    public Entity entity;
    public String phenomena;

    public ScanResult(byte type, int blockId, int blockMeta, Entity entity, String phenomena) {
        this.type = type;
        this.id = blockId;
        this.meta = blockMeta;
        this.entity = entity;
        this.phenomena = phenomena;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ScanResult sr)) {
            return false;
        }

        if (this.type != sr.type) {
            return false;
        }

        return switch (this.type) {
            case 1 -> Objects.equals(this.id, sr.id) && this.meta == sr.meta;
            case 2 -> Objects.equals(this.entity.getUUID(), sr.entity.getUUID());
            case 3 -> Objects.equals(this.phenomena, sr.phenomena);
            default -> true;
        };
    }
}
