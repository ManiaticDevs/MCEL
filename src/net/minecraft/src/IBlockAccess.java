package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public interface IBlockAccess
{

    public abstract int getBlockId(int i, int j, int k);

    public abstract TileEntity getBlockTileEntity(int i, int j, int k);

    public abstract float getLightBrightness(int i, int j, int k);

    public abstract int getBlockMetadata(int i, int j, int k);

    public abstract Material getBlockMaterial(int i, int j, int k);

    public abstract boolean isBlockOpaqueCube(int i, int j, int k);

    public abstract WorldChunkManager getWorldChunkManager();
}
