package net.ssorangecaty.elementreborn.dungeon;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class Room {
    protected boolean init = false;
    public final ResourceLocation structureName;
    @Nullable
    public StructureTemplate structuretemplate;
    public Pair<Integer,Integer> structureSize;
    private final String author;
    private BlockPos structurePos = new BlockPos(0, 1, 0);
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private boolean ignoreEntities = true;
    private float integrity = 1.0F;
    private long seed;

    public Room(ResourceLocation structureName, String author) {
        this.structureName = structureName;
        this.author = author;
    }
    public Room(ResourceLocation structureName) {
        this.structureName = structureName;
        this.author = "Dungeon";
    }

    public void init(ServerLevel serverLevel){
        this.structuretemplate = serverLevel.getStructureManager().get(structureName).orElse(null);
        if (structuretemplate != null) {
            this.init = true;
        }else{
            throw new RuntimeException("dungeon structure init error" + structureName + "couldn't find structure by Name");
        }
    }

    public void initTest(){

    }

    public boolean placeStructureIfSameSize(ServerLevel serverLevel,Vec3i size,BlockPos blockPos) {
        if(!this.init){
            throw new RuntimeException("dungeon structure placing error" + structureName + "couldn't place structure because structure not init");
        }

        if (this.structureName != null) {
            if (this.structuretemplate == null) {
                return false;
            } else if (size.equals(this.structureSize)) {
                this.placeStructure(serverLevel, structuretemplate, blockPos);
                return true;
            }
        }
        return false;
    }

    public String getAuthor() {
        return author;
    }

    public ResourceLocation getStructureName() {
        return structureName;
    }

    @Nullable
    public StructureTemplate getStructuretemplate() {
        return structuretemplate;
    }

    public void setIgnoreEntities(boolean ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }

    public void setMirror(Mirror mirror) {
        this.mirror = mirror;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setStructurePos(BlockPos structurePos) {
        this.structurePos = structurePos;
    }

    public static RandomSource createRandom(long seed) {
        return seed == 0L ? RandomSource.create(Util.getMillis()) : RandomSource.create(seed);
    }

    private void placeStructure(ServerLevel serverLevel, StructureTemplate structureTemplate, BlockPos blockPos) {
        if(!this.init){
            throw new RuntimeException("dungeon structure placing error" + structureName + "couldn't place structure because structure not init");
        }

        StructurePlaceSettings structureplacesettings = new StructurePlaceSettings()
                .setMirror(this.mirror)
                .setRotation(this.rotation)
                .setIgnoreEntities(this.ignoreEntities);
        if (this.integrity < 1.0F) {
            structureplacesettings.clearProcessors()
                    .addProcessor(new BlockRotProcessor(Mth.clamp(this.integrity, 0.0F, 1.0F)))
                    .setRandom(createRandom(this.seed));
        }

        BlockPos blockpos = blockPos.offset(this.structurePos);
        structureTemplate.placeInWorld(serverLevel, blockpos, blockpos, structureplacesettings, createRandom(this.seed), 2);
    }

}
