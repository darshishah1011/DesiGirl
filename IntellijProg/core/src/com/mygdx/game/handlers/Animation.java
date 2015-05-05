package com.mygdx.game.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Darshi on 5/2/2015.
 */
public class Animation {

    private TextureRegion[] frames;
    private float time; //current time
    private float delay; //time btwn frame to move to next one
    private int currentFrame; //which frame we currently are on
    private int timesPlayed; //how many times a animation is played

    public Animation(){

    }

    public Animation(TextureRegion[] frames){
        this(frames,1/12f);
    }

    public Animation(TextureRegion[] frames, float delay){
        setFrames(frames,delay);
    }

    public void setFrames(TextureRegion[] frames, float delay){
        this.frames = frames;
        this.delay = delay;
        time=0;
        currentFrame = 0;
        timesPlayed = 0;
    }

    public void update(float dt){
        if(delay <=0) return; // do not move animation
        time+=dt;
        while(time>=delay){
            step();
        }
    }

    public void step(){
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length){
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame(){ return frames[currentFrame];}
    public int getTimesPlayed(){return timesPlayed;}

}
