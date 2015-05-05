package com.mygdx.game.handlers;

/**
 * Created by Darshi on 5/1/2015.
 */
public class MyInput {

    public static final int NUM_KEYS = 4; //4 keys, jump,left,right //two keys , one to jump and other to change color
    public static final int JUMP = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int PAUSE = 3;

    static {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public static boolean keys[]; //current key state
    public static boolean pkeys[]; // previous key state
    public static int setMouseX = -1;
    public static int setMouseY = -1;

    public static void update() //previous state updated to new state
    {
        for (int i = 0; i < NUM_KEYS; i++)
            pkeys[i] = keys[i];
    }

    //set keys state
    public static void setKey(int i, boolean b) {
        keys[i] = b;
    }

    //check if key i is down or not
    public static boolean isDown(int i) {
        return keys[i];
    }

    //check if key i was just pressed
    public static boolean isPressed(int i) {
        boolean b = keys[i] && !pkeys[i];
        pkeys[i] = keys[i];
        return b;
    }

}
