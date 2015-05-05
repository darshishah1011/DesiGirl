package com.mygdx.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Player;
import com.mygdx.game.screens.BaseScreen;
import com.mygdx.game.screens.GameOver;

/**
 * Created by Darshi on 5/1/2015.
 */
public class MyContactListener implements ContactListener {

    private int numOfFootContacts;
    private Array<Body> bodiesToRemove;


    public MyContactListener() {
        super();
        bodiesToRemove = new Array<Body>();
    }

    //called when two fixtures collide with each other
    @Override
    public void beginContact(Contact contact) //holds information of fixtures colliding with each other
    {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //System.out.println(fa.getUserData() + "," +fb.getUserData());//find out two colliding fixtures

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            //System.out.println("fa is foot");
            numOfFootContacts++;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            //System.out.println("fb is foot");
            numOfFootContacts++;
        }


        if (fa.getUserData() != null && fa.getUserData().equals("Water")) {
            //this.game.setScreen(new GameOver(game));
            BaseScreen.parent.setScreen(new GameOver());
        }

        if (fb.getUserData() != null && fb.getUserData().equals("Water")) {
            BaseScreen.parent.setScreen(new GameOver());
        }

    }

    public boolean isPlayerOnGround() {
        return numOfFootContacts > 0;
    }

    // called when two fixtures no more collide
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        //System.out.println(fa.getUserData() + "," +fb.getUserData());//find out two colliding fixtures

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            //System.out.println("fa is foot");
            numOfFootContacts--;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            //System.out.println("fb is foot");
            numOfFootContacts--;
        }

        if (fa.getUserData() != null && fa.getUserData().equals("crystal")) {
            //remove crystal.Cant do on fly as world is getting updated, so we maintain a list and then remove after world is updated
            fa.setUserData("crystal.done");
            ((Player) fb.getBody().getUserData()).collectCoins();
            bodiesToRemove.add(fa.getBody());

        } else if (fb.getUserData() != null && fb.getUserData().equals("crystal")) {
            fb.setUserData("crystal.done");
            ((Player) fa.getBody().getUserData()).collectCoins();
            bodiesToRemove.add(fb.getBody());
        }

        if (fa.getUserData() != null && fa.getUserData().equals("Water")) {
            //this.game.setScreen(new GameOver(game));
            BaseScreen.parent.setScreen(new GameOver());
        }

        if (fb.getUserData() != null && fb.getUserData().equals("Water")) {
            BaseScreen.parent.setScreen(new GameOver());
        }


    }

    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    //called after collision detection and before collision handling
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    //called after collision handling
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
