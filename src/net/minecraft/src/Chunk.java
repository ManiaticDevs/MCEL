package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public class Chunk
{

    @SuppressWarnings("unchecked")
	public Chunk(World world, int i, int j)
    {
        chunkTileEntityMap = new HashMap<ChunkPosition, TileEntity>();
        entities = new List[8];
        isTerrainPopulated = false;
        isModified = false;
        field_1524_q = false;
        hasEntities = false;
        lastSaveTime = 0L;
        worldObj = world;
        xPosition = i;
        zPosition = j;
        heightMap = new byte[256];
        for(int k = 0; k < entities.length; k++)
        {
            entities[k] = new ArrayList<Entity>();
        }

    }

    public Chunk(World world, byte abyte0[], int i, int j)
    {
        this(world, i, j);
        blocks = abyte0;
        data = new NibbleArray(abyte0.length);
        skylightMap = new NibbleArray(abyte0.length);
        blocklightMap = new NibbleArray(abyte0.length);
    }

    public boolean isAtLocation(int i, int j)
    {
        return i == xPosition && j == zPosition;
    }

    public int getHeightValue(int i, int j)
    {
        return heightMap[j << 4 | i] & 0xff;
    }

    public void func_1014_a()
    {
    }

    public void generateHeightMap()
    {
        int i = 127;
        for(int j = 0; j < 16; j++)
        {
            for(int k = 0; k < 16; k++)
            {
                int l = 127;
                for(int i1 = j << 11 | k << 7; l > 0 && Block.lightOpacity[blocks[(i1 + l) - 1]] == 0; l--) { }
                heightMap[k << 4 | j] = (byte)l;
                if(l < i)
                {
                    i = l;
                }
            }

        }

        lowestBlockHeight = i;
        isModified = true;
    }

    public void generateSkylightMap()
    {
        int i = 127;
        for(int j = 0; j < 16; j++)
        {
            for(int l = 0; l < 16; l++)
            {
                heightMap[l << 4 | j] = -128;
                func_1003_g(j, 127, l);
                if((heightMap[l << 4 | j] & 0xff) < i)
                {
                    i = heightMap[l << 4 | j] & 0xff;
                }
            }

        }

        lowestBlockHeight = i;
        for(int k = 0; k < 16; k++)
        {
            for(int i1 = 0; i1 < 16; i1++)
            {
                func_996_c(k, i1);
            }

        }

        isModified = true;
    }

    public void func_4143_d()
    {
        byte byte0 = 32;
        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                int k = i << 11 | j << 7;
                for(int l = 0; l < 128; l++)
                {
                    int j1 = Block.lightValue[blocks[k + l]];
                    if(j1 > 0)
                    {
                        blocklightMap.setNibble(i, l, j, j1);
                    }
                }

                int i1 = 15;
                for(int k1 = byte0 - 2; k1 < 128 && i1 > 0;)
                {
                    k1++;
                    byte byte1 = blocks[k + k1];
                    int l1 = Block.lightOpacity[byte1];
                    int i2 = Block.lightValue[byte1];
                    if(l1 == 0)
                    {
                        l1 = 1;
                    }
                    i1 -= l1;
                    if(i2 > i1)
                    {
                        i1 = i2;
                    }
                    if(i1 < 0)
                    {
                        i1 = 0;
                    }
                    blocklightMap.setNibble(i, k1, j, i1);
                }

            }

        }

        worldObj.func_616_a(EnumSkyBlock.Block, xPosition * 16, byte0 - 1, zPosition * 16, xPosition * 16 + 16, byte0 + 1, zPosition * 16 + 16);
        isModified = true;
    }

    private void func_996_c(int i, int j)
    {
        int k = getHeightValue(i, j);
        int l = xPosition * 16 + i;
        int i1 = zPosition * 16 + j;
        func_1020_f(l - 1, i1, k);
        func_1020_f(l + 1, i1, k);
        func_1020_f(l, i1 - 1, k);
        func_1020_f(l, i1 + 1, k);
    }

    private void func_1020_f(int i, int j, int k)
    {
        int l = worldObj.getHeightValue(i, j);
        if(l > k)
        {
            worldObj.func_616_a(EnumSkyBlock.Sky, i, k, j, i, l, j);
        } else
        if(l < k)
        {
            worldObj.func_616_a(EnumSkyBlock.Sky, i, l, j, i, k, j);
        }
        isModified = true;
    }

    private void func_1003_g(int i, int j, int k)
    {
        int l = heightMap[k << 4 | i] & 0xff;
        int i1 = l;
        if(j > l)
        {
            i1 = j;
        }
        for(int j1 = i << 11 | k << 7; i1 > 0 && Block.lightOpacity[blocks[(j1 + i1) - 1]] == 0; i1--) { }
        if(i1 == l)
        {
            return;
        }
        worldObj.func_680_f(i, k, i1, l);
        heightMap[k << 4 | i] = (byte)i1;
        if(i1 < lowestBlockHeight)
        {
            lowestBlockHeight = i1;
        } else
        {
            int k1 = 127;
            for(int i2 = 0; i2 < 16; i2++)
            {
                for(int k2 = 0; k2 < 16; k2++)
                {
                    if((heightMap[k2 << 4 | i2] & 0xff) < k1)
                    {
                        k1 = heightMap[k2 << 4 | i2] & 0xff;
                    }
                }

            }

            lowestBlockHeight = k1;
        }
        int l1 = xPosition * 16 + i;
        int j2 = zPosition * 16 + k;
        if(i1 < l)
        {
            for(int l2 = i1; l2 < l; l2++)
            {
                skylightMap.setNibble(i, l2, k, 15);
            }

        } else
        {
            worldObj.func_616_a(EnumSkyBlock.Sky, l1, l, j2, l1, i1, j2);
            for(int i3 = l; i3 < i1; i3++)
            {
                skylightMap.setNibble(i, i3, k, 0);
            }

        }
        int j3 = 15;
        int k3 = i1;
        while(i1 > 0 && j3 > 0) 
        {
            i1--;
            int l3 = Block.lightOpacity[getBlockID(i, i1, k)];
            if(l3 == 0)
            {
                l3 = 1;
            }
            j3 -= l3;
            if(j3 < 0)
            {
                j3 = 0;
            }
            skylightMap.setNibble(i, i1, k, j3);
        }
        for(; i1 > 0 && Block.lightOpacity[getBlockID(i, i1 - 1, k)] == 0; i1--) { }
        if(i1 != k3)
        {
            worldObj.func_616_a(EnumSkyBlock.Sky, l1 - 1, i1, j2 - 1, l1 + 1, k3, j2 + 1);
        }
        isModified = true;
    }

    public int getBlockID(int i, int j, int k)
    {
        return blocks[i << 11 | k << 7 | j];
    }

    public boolean setBlockIDWithMetadata(int i, int j, int k, int l, int i1)
    {
        byte byte0 = (byte)l;
        int j1 = heightMap[k << 4 | i] & 0xff;
        int k1 = blocks[i << 11 | k << 7 | j] & 0xff;
        if(k1 == l && data.getNibble(i, j, k) == i1)
        {
            return false;
        }
        int l1 = xPosition * 16 + i;
        int i2 = zPosition * 16 + k;
        blocks[i << 11 | k << 7 | j] = byte0;
        if(k1 != 0 && !worldObj.multiplayerWorld)
        {
            Block.blocksList[k1].onBlockRemoval(worldObj, l1, j, i2);
        }
        data.setNibble(i, j, k, i1);
        if(!worldObj.worldProvider.field_6478_e)
        {
            if(Block.lightOpacity[byte0] != 0)
            {
                if(j >= j1)
                {
                    func_1003_g(i, j + 1, k);
                }
            } else
            if(j == j1 - 1)
            {
                func_1003_g(i, j, k);
            }
            worldObj.func_616_a(EnumSkyBlock.Sky, l1, j, i2, l1, j, i2);
        }
        worldObj.func_616_a(EnumSkyBlock.Block, l1, j, i2, l1, j, i2);
        func_996_c(i, k);
        if(l != 0)
        {
            Block.blocksList[l].onBlockAdded(worldObj, l1, j, i2);
        }
        data.setNibble(i, j, k, i1);
        isModified = true;
        return true;
    }

    public boolean setBlockID(int i, int j, int k, int l)
    {
        byte byte0 = (byte)l;
        int i1 = heightMap[k << 4 | i] & 0xff;
        int j1 = blocks[i << 11 | k << 7 | j] & 0xff;
        if(j1 == l)
        {
            return false;
        }
        int k1 = xPosition * 16 + i;
        int l1 = zPosition * 16 + k;
        blocks[i << 11 | k << 7 | j] = byte0;
        if(j1 != 0)
        {
            Block.blocksList[j1].onBlockRemoval(worldObj, k1, j, l1);
        }
        data.setNibble(i, j, k, 0);
        if(Block.lightOpacity[byte0] != 0)
        {
            if(j >= i1)
            {
                func_1003_g(i, j + 1, k);
            }
        } else
        if(j == i1 - 1)
        {
            func_1003_g(i, j, k);
        }
        worldObj.func_616_a(EnumSkyBlock.Sky, k1, j, l1, k1, j, l1);
        worldObj.func_616_a(EnumSkyBlock.Block, k1, j, l1, k1, j, l1);
        func_996_c(i, k);
        if(l != 0 && !worldObj.multiplayerWorld)
        {
            Block.blocksList[l].onBlockAdded(worldObj, k1, j, l1);
        }
        isModified = true;
        return true;
    }

    public int getBlockMetadata(int i, int j, int k)
    {
        return data.getNibble(i, j, k);
    }

    public void setBlockMetadata(int i, int j, int k, int l)
    {
        isModified = true;
        data.setNibble(i, j, k, l);
    }

    public int getSavedLightValue(EnumSkyBlock enumskyblock, int i, int j, int k)
    {
        if(enumskyblock == EnumSkyBlock.Sky)
        {
            return skylightMap.getNibble(i, j, k);
        }
        if(enumskyblock == EnumSkyBlock.Block)
        {
            return blocklightMap.getNibble(i, j, k);
        } else
        {
            return 0;
        }
    }

    public void setLightValue(EnumSkyBlock enumskyblock, int i, int j, int k, int l)
    {
        isModified = true;
        if(enumskyblock == EnumSkyBlock.Sky)
        {
            skylightMap.setNibble(i, j, k, l);
        } else
        if(enumskyblock == EnumSkyBlock.Block)
        {
            blocklightMap.setNibble(i, j, k, l);
        } else
        {
            return;
        }
    }

    public int getBlockLightValue(int i, int j, int k, int l)
    {
        int i1 = skylightMap.getNibble(i, j, k);
        if(i1 > 0)
        {
            field_1540_a = true;
        }
        i1 -= l;
        int j1 = blocklightMap.getNibble(i, j, k);
        if(j1 > i1)
        {
            i1 = j1;
        }
        return i1;
    }

    public void addEntity(Entity entity)
    {
        if(field_1524_q)
        {
            return;
        }
        hasEntities = true;
        int i = MathHelper.floor_double(entity.posX / 16D);
        int j = MathHelper.floor_double(entity.posZ / 16D);
        if(i != xPosition || j != zPosition)
        {
            System.out.println((new StringBuilder()).append("Wrong location! ").append(entity).toString());
            Thread.dumpStack();
        }
        int k = MathHelper.floor_double(entity.posY / 16D);
        if(k < 0)
        {
            k = 0;
        }
        if(k >= entities.length)
        {
            k = entities.length - 1;
        }
        entity.field_621_aZ = true;
        entity.field_657_ba = xPosition;
        entity.field_656_bb = k;
        entity.field_654_bc = zPosition;
        entities[k].add(entity);
    }

    public void func_1015_b(Entity entity)
    {
        func_1016_a(entity, entity.field_656_bb);
    }

    public void func_1016_a(Entity entity, int i)
    {
        if(i < 0)
        {
            i = 0;
        }
        if(i >= entities.length)
        {
            i = entities.length - 1;
        }
        entities[i].remove(entity);
    }

    public boolean canBlockSeeTheSky(int i, int j, int k)
    {
        return j >= (heightMap[k << 4 | i] & 0xff);
    }

    public TileEntity getChunkBlockTileEntity(int i, int j, int k)
    {
        ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        TileEntity tileentity = (TileEntity)chunkTileEntityMap.get(chunkposition);
        if(tileentity == null)
        {
            int l = getBlockID(i, j, k);
            if(!Block.isBlockContainer[l])
            {
                return null;
            }
            BlockContainer blockcontainer = (BlockContainer)Block.blocksList[l];
            blockcontainer.onBlockAdded(worldObj, xPosition * 16 + i, j, zPosition * 16 + k);
            tileentity = (TileEntity)chunkTileEntityMap.get(chunkposition);
        }
        return tileentity;
    }

    public void func_1001_a(TileEntity tileentity)
    {
        int i = tileentity.xCoord - xPosition * 16;
        int j = tileentity.yCoord;
        int k = tileentity.zCoord - zPosition * 16;
        setChunkBlockTileEntity(i, j, k, tileentity);
    }

    public void setChunkBlockTileEntity(int i, int j, int k, TileEntity tileentity)
    {
        ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        tileentity.worldObj = worldObj;
        tileentity.xCoord = xPosition * 16 + i;
        tileentity.yCoord = j;
        tileentity.zCoord = zPosition * 16 + k;
        if(getBlockID(i, j, k) == 0 || !(Block.blocksList[getBlockID(i, j, k)] instanceof BlockContainer))
        {
            System.out.println("Attempted to place a tile entity where there was no entity tile!");
            return;
        }
        if(isChunkLoaded)
        {
            if(chunkTileEntityMap.get(chunkposition) != null)
            {
                worldObj.loadedTileEntityList.remove(chunkTileEntityMap.get(chunkposition));
            }
            worldObj.loadedTileEntityList.add(tileentity);
        }
        chunkTileEntityMap.put(chunkposition, tileentity);
    }

    public void removeChunkBlockTileEntity(int i, int j, int k)
    {
        ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        if(isChunkLoaded)
        {
            worldObj.loadedTileEntityList.remove(chunkTileEntityMap.remove(chunkposition));
        }
    }

    public void onChunkLoad()
    {
        isChunkLoaded = true;
        worldObj.loadedTileEntityList.addAll(chunkTileEntityMap.values());
        for(int i = 0; i < entities.length; i++)
        {
            worldObj.func_636_a(entities[i]);
        }

    }

    public void onChunkUnload()
    {
        isChunkLoaded = false;
        worldObj.loadedTileEntityList.removeAll(chunkTileEntityMap.values());
        for(int i = 0; i < entities.length; i++)
        {
            worldObj.func_632_b(entities[i]);
        }

    }

    public void setChunkModified()
    {
        isModified = true;
    }

    public void getEntitiesWithinAABBForEntity(Entity entity, AxisAlignedBB axisalignedbb, List<Entity> list)
    {
        int i = MathHelper.floor_double((axisalignedbb.minY - 2D) / 16D);
        int j = MathHelper.floor_double((axisalignedbb.maxY + 2D) / 16D);
        if(i < 0)
        {
            i = 0;
        }
        if(j >= entities.length)
        {
            j = entities.length - 1;
        }
        for(int k = i; k <= j; k++)
        {
            List<Entity> list1 = entities[k];
            for(int l = 0; l < list1.size(); l++)
            {
                Entity entity1 = (Entity)list1.get(l);
                if(entity1 != entity && entity1.boundingBox.intersectsWith(axisalignedbb))
                {
                    list.add(entity1);
                }
            }

        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void getEntitiesOfTypeWithinAAAB(Class class1, AxisAlignedBB axisalignedbb, List list)
    {
        int i = MathHelper.floor_double((axisalignedbb.minY - 2D) / 16D);
        int j = MathHelper.floor_double((axisalignedbb.maxY + 2D) / 16D);
        if(i < 0)
        {
            i = 0;
        }
        if(j >= entities.length)
        {
            j = entities.length - 1;
        }
        for(int k = i; k <= j; k++)
        {
            List list1 = entities[k];
            for(int l = 0; l < list1.size(); l++)
            {
                Entity entity = (Entity)list1.get(l);
                if(class1.isAssignableFrom(entity.getClass()) && entity.boundingBox.intersectsWith(axisalignedbb))
                {
                    list.add(entity);
                }
            }

        }

    }

    public boolean needsSaving(boolean flag)
    {
        if(neverSave)
        {
            return false;
        }
        if(hasEntities && worldObj.worldTime != lastSaveTime)
        {
            return true;
        } else
        {
            return isModified;
        }
    }

    public int func_1004_a(byte abyte0[], int i, int j, int k, int l, int i1, int j1, 
            int k1)
    {
        for(int l1 = i; l1 < l; l1++)
        {
            for(int l2 = k; l2 < j1; l2++)
            {
                int l3 = l1 << 11 | l2 << 7 | j;
                int l4 = i1 - j;
                System.arraycopy(abyte0, k1, blocks, l3, l4);
                k1 += l4;
            }

        }

        generateHeightMap();
        for(int i2 = i; i2 < l; i2++)
        {
            for(int i3 = k; i3 < j1; i3++)
            {
                int i4 = (i2 << 11 | i3 << 7 | j) >> 1;
                int i5 = (i1 - j) / 2;
                System.arraycopy(abyte0, k1, data.data, i4, i5);
                k1 += i5;
            }

        }

        for(int j2 = i; j2 < l; j2++)
        {
            for(int j3 = k; j3 < j1; j3++)
            {
                int j4 = (j2 << 11 | j3 << 7 | j) >> 1;
                int j5 = (i1 - j) / 2;
                System.arraycopy(abyte0, k1, blocklightMap.data, j4, j5);
                k1 += j5;
            }

        }

        for(int k2 = i; k2 < l; k2++)
        {
            for(int k3 = k; k3 < j1; k3++)
            {
                int k4 = (k2 << 11 | k3 << 7 | j) >> 1;
                int k5 = (i1 - j) / 2;
                System.arraycopy(abyte0, k1, skylightMap.data, k4, k5);
                k1 += k5;
            }

        }

        return k1;
    }

    public Random func_997_a(long l)
    {
        return new Random(worldObj.randomSeed + (long)(xPosition * xPosition * 0x4c1906) + (long)(xPosition * 0x5ac0db) + (long)(zPosition * zPosition) * 0x4307a7L + (long)(zPosition * 0x5f24f) ^ l);
    }

    public static boolean field_1540_a;
    public byte blocks[];
    public boolean isChunkLoaded;
    public World worldObj;
    public NibbleArray data;
    public NibbleArray skylightMap;
    public NibbleArray blocklightMap;
    public byte heightMap[];
    public int lowestBlockHeight;
    public final int xPosition;
    public final int zPosition;
    public Map<ChunkPosition, TileEntity> chunkTileEntityMap;
    public List<Entity> entities[];
    public boolean isTerrainPopulated;
    public boolean isModified;
    public boolean neverSave;
    public boolean field_1524_q;
    public boolean hasEntities;
    public long lastSaveTime;
}
