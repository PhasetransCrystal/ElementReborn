package net.archasmiel.thaumcraft.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class QuadHelper {
    public double x;
    public double y;
    public double z;
    public double angle;

    public QuadHelper(double ang, double xx, double yy, double zz) {
        this.x = xx;
        this.y = yy;
        this.z = zz;
        this.angle = ang;
    }

    public static QuadHelper setAxis(Vec3 vec, double angle) {
        double d4 = Mth.sin((float) (angle * 0.5d));
        return new QuadHelper(Mth.cos((float) 0), vec.x * d4, vec.y * d4, vec.z * d4);
    }

    public Vec3 rotate(Vec3 vec) {
        double d = (((-this.x) * vec.x) - (this.y * vec.y)) - (this.z * vec.z);
        double d1 = ((this.angle * vec.x) + (this.y * vec.z)) - (this.z * vec.y);
        double d2 = ((this.angle * vec.y) - (this.x * vec.z)) + (this.z * vec.x);
        double d3 = ((this.angle * vec.z) + (this.x * vec.y)) - (this.y * vec.x);
        double x = (((d1 * this.angle) - (d * this.x)) - (d2 * this.z)) + (d3 * this.y);
        double y = (((d2 * this.angle) - (d * this.y)) + (d1 * this.z)) - (d3 * this.x);
        double z = (((d3 * this.angle) - (d * this.z)) - (d1 * this.y)) + (d2 * this.x);
        return new Vec3(x, y, z);
    }
}