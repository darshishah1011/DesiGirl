package com.mygdx.game.handlers;

/**
 * Created by Darshi on 4/27/2015.
 */
public class B2DVariables {

    //pixels per meter ratio
    public static final float PPM = 100; // 1 meter = 100 pixels

    //category bits -- can only use power of two values ie 2,4,8,16 that to only 16 bits so 16 possible
    /*public static final short BIT_GROUND = 2;
    public static final short BIT_PLAYER = 4; //renamed box to player
    *///public static final short BIT_BALL = 8;

    public static final short BIT_PLAYER = 2;
    public static final short BIT_GROUND = 4;
    public static final short BIT_CRYSTAL = 8;
    public static final short BIT_WATER = 16;
    public static final short BIT_ENEMY = 32;

}
