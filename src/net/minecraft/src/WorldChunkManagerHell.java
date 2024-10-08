package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Arrays;

public class WorldChunkManagerHell extends WorldChunkManager
{

    public WorldChunkManagerHell(MobSpawnerBase mobspawnerbase, double d, double d1)
    {
        field_4201_e = mobspawnerbase;
        field_4200_f = d;
        field_4199_g = d1;
    }

    public MobSpawnerBase func_4074_a(ChunkCoordIntPair chunkcoordintpair)
    {
        return field_4201_e;
    }

    public MobSpawnerBase getMobSpawnAt(int i, int j)
    {
        return field_4201_e;
    }

    public double func_4072_b(int i, int j)
    {
        return field_4200_f;
    }

    public MobSpawnerBase[] func_4069_a(int i, int j, int k, int l)
    {
        field_4195_d = loadBlockGeneratorData(field_4195_d, i, j, k, l);
        return field_4195_d;
    }

    public double[] getTemperatures(double ad[], int i, int j, int k, int l)
    {
        if(ad == null || ad.length < k * l)
        {
            ad = new double[k * l];
        }
        Arrays.fill(ad, 0, k * l, field_4200_f);
        return ad;
    }

    public MobSpawnerBase[] loadBlockGeneratorData(MobSpawnerBase amobspawnerbase[], int i, int j, int k, int l)
    {
        if(amobspawnerbase == null || amobspawnerbase.length < k * l)
        {
            amobspawnerbase = new MobSpawnerBase[k * l];
            temperature = new double[k * l];
            humidity = new double[k * l];
        }
        Arrays.fill(amobspawnerbase, 0, k * l, field_4201_e);
        Arrays.fill(humidity, 0, k * l, field_4199_g);
        Arrays.fill(temperature, 0, k * l, field_4200_f);
        return amobspawnerbase;
    }

    private MobSpawnerBase field_4201_e;
    private double field_4200_f;
    private double field_4199_g;
}
