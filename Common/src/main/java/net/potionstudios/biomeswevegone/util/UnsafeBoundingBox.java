package net.potionstudios.biomeswevegone.util;

import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.Objects;

public final class UnsafeBoundingBox {
    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;

    public UnsafeBoundingBox() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public UnsafeBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public int minX() {
        return minX;
    }

    public int minY() {
        return minY;
    }

    public int minZ() {
        return minZ;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    public int maxZ() {
        return maxZ;
    }

    public void encapsulate(Vec3i pos) {
        this.minX = Math.min(this.minX, pos.getX());
        this.minY = Math.min(this.minY, pos.getY());
        this.minZ = Math.min(this.minZ, pos.getZ());
        this.maxX = Math.max(this.maxX, pos.getX());
        this.maxY = Math.max(this.maxY, pos.getY());
        this.maxZ = Math.max(this.maxZ, pos.getZ());
    }


    public boolean valid() {
        return this.minX <= this.maxX || this.minY <= this.maxY || this.minZ <= this.maxZ;
    }

    public BoundingBox toBoundingBox() {
        return new BoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UnsafeBoundingBox) obj;
        return this.minX == that.minX &&
                this.minY == that.minY &&
                this.minZ == that.minZ &&
                this.maxX == that.maxX &&
                this.maxY == that.maxY &&
                this.maxZ == that.maxZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public String toString() {
        return "UnsafeBoundingBox[" +
                "minX=" + minX + ", " +
                "minY=" + minY + ", " +
                "minZ=" + minZ + ", " +
                "maxX=" + maxX + ", " +
                "maxY=" + maxY + ", " +
                "maxZ=" + maxZ + ']';
    }
}