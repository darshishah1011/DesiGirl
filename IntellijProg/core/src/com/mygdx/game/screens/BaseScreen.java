package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.DesiGirlGame;
import com.mygdx.game.entities.Coin;
import com.mygdx.game.entities.Enemies;
import com.mygdx.game.entities.Player;
import com.mygdx.game.handlers.*;

import static com.mygdx.game.handlers.B2DVariables.PPM;

/**
 * Created by Darshi on 5/4/2015.
 */
public class BaseScreen extends ScreenAdapter {

    public static final int V_HEIGHT = 240;
    public static final int V_WIDTH = 320;
    public static final float STEP = 1 / 60f;
    public static final int SCALE = 2;
    public static final String TITLE = "Desi Girl";
    public static Content res;
    protected final float MAX_VELOCITY = 0.3f;
    public OrthographicCamera mainCamera;
    public OrthographicCamera hudCam;
    public SpriteBatch spriteBatch;
    public FitViewport camViewPort, hudCamViewport;
    //    public FitViewport box2dViewport;
    public World world;
    protected OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    protected float tileSize;
    protected Player player;
    protected Array<Coin> coins;
    public static DesiGirlGame parent;
    protected TiledMap tiledMap;
    //    protected Box2DDebugRenderer box2DDebugRenderer;
//    protected OrthographicCamera box2dCamera;
    protected MyContactListener contactListener;
    public float tiledMapX, tiledMapY;
    public HudManager hudManager;
    protected Array<Enemies> enemies;

    public static BaseScreen newInstance(DesiGirlGame desiGirlGame) {
        BaseScreen baseScreen = new BaseScreen();
        baseScreen.parent = desiGirlGame;
        return baseScreen;
    }

    @Override
    public void show() {
        //normal mainCamera
        mainCamera = new OrthographicCamera();
//        mainCamera.setToOrtho(false, mainCamera.viewportWidth / 2, mainCamera.viewportHeight / 2);
        mainCamera.position.set(mainCamera.viewportWidth / 2, mainCamera.viewportHeight / 2, 0);
        camViewPort = new FitViewport(V_WIDTH, V_HEIGHT);
        camViewPort.setCamera(mainCamera);
        mainCamera.update();
        camViewPort.apply();

        //hud Cam
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, hudCam.viewportWidth / 2, hudCam.viewportHeight / 2);
        hudCamViewport = new FitViewport(V_WIDTH, V_HEIGHT);
        hudCamViewport.setCamera(hudCam);
        hudCam.update();
        hudCamViewport.apply();

        res = new Content();
        res.loadTexture("images/player.png", "player");
        res.loadTexture("images/crystal.png", "crystal");
        res.loadTexture("images/enemies.png", "enemies");
        //
        spriteBatch = new SpriteBatch();
        contactListener = new MyContactListener();

        //Box2D
        world = new World(new Vector2(0, -9.8f), true); // true means inactive bodies in world are set to sleep so not included in calc of update, thus worlds update runs faster
        world.setContactListener(contactListener); //world uses ur contact listener

        //create player
        createPlayer();
        GameStateManager.init();

        //set box2d camera   --- this mainCamera is so that we see Box2D objects after conversion
//        box2DDebugRenderer = new Box2DDebugRenderer();
//        box2dCamera = new OrthographicCamera();
//        box2dCamera.position.set(box2dCamera.viewportWidth/2/PPM,box2dCamera.viewportHeight/2/PPM,0);
//        box2dViewport = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM);
//        box2dViewport.setCamera(box2dCamera);
////        box2dCamera.setToOrtho(false, BaseScreen.V_WIDTH / PPM, BaseScreen.V_HEIGHT / PPM);
//        box2dCamera.update();
//        box2dViewport.apply();


        hudManager = new HudManager();
        hudManager.init(hudCamViewport, spriteBatch);
        Gdx.input.setInputProcessor(new MyInputProcessor());
    }

    @Override
    public void resize(int width, int height) {
        camViewPort.setScreenSize(width, height);
        camViewPort.apply(true);
        hudCamViewport.setScreenSize(width, height);
        hudCamViewport.apply(true);
//        hudManager.resize(width, height);
//        box2dViewport.setScreenSize((int) (width / PPM), (int) (height / PPM));
//        box2dViewport.apply(true);
    }

    //create player method
    private void createPlayer() {
        //define body
        BodyDef bdef = new BodyDef();
        PolygonShape polygon = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        //create falling player
        bdef.position.set(5 / PPM, 200 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        //polygon.setAsBox(5/PPM,5/PPM);
        polygon.setAsBox(13 / PPM, 13 / PPM);
        fdef.shape = polygon;
        fdef.filter.categoryBits = B2DVariables.BIT_PLAYER;  //set category bits for Box
        fdef.filter.maskBits = B2DVariables.BIT_GROUND; //box can collide with ground not with ball
        fdef.density = 16f;
        fdef.friction = .5f;
        body.createFixture(fdef).setUserData("player");
        body.setUserData(player);

        polygon.setAsBox(13 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
        fdef.shape = polygon;
        fdef.filter.categoryBits = B2DVariables.BIT_PLAYER;
        fdef.filter.maskBits = B2DVariables.BIT_GROUND | B2DVariables.BIT_CRYSTAL | B2DVariables.BIT_WATER | B2DVariables.BIT_ENEMY ;
        fdef.isSensor = true;
        //playerBody.createFixture(fdef).setUserData("foot");
        body.createFixture(fdef).setUserData("foot");

        // create player
        player = new Player(body);
        body.setUserData(player);
    }

    //tileMap creation
    public void createTileMap(String mapName) {
        // load tile map
        tiledMap = new TmxMapLoader().load(mapName);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //tileSize = (int)tiledMap.getProperties().get("tilewidth");
        tileSize = 32;
        TiledMapTileLayer layer;
        //create ground
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Ground");
        createLayer(layer, B2DVariables.BIT_GROUND);

        //create water
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Water");
        createLayer(layer, B2DVariables.BIT_WATER);

        //create coins
        createCoins();

        //create enimies if level>1
        if(mapName!="maps/Level1.tmx"){
            createEnemies();
        }

        MapProperties properties = tiledMap.getProperties();
        tiledMapX = properties.get("width", Integer.class) * properties.get("tilewidth", Integer.class);
        tiledMapY = properties.get("height", Integer.class) * properties.get("tileheight", Integer.class);
        GameStateManager.tiledMapX = tiledMapX;
        GameStateManager.tiledMapY = tiledMapY;

    }

    public void createLayer(TiledMapTileLayer layer, short bits) {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        //go through all cell in layer -- row = y n col = x
        for (int row = 0; row < layer.getHeight(); row++)
            for (int col = 0; col < layer.getWidth(); col++) {
                //get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                //check is cell exists
                if (cell == null) continue;
                if (cell.getTile() == null) continue;

                //create a body and fixture from cell
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(
                        (col + 0.5f) * tileSize / PPM, //0.5 so position at centre
                        (row + 0.5f) * tileSize / PPM
                );

                ChainShape chainShape = new ChainShape();
                Vector2[] v = new Vector2[3];
                //bottom left
                v[0] = new Vector2(
                        -tileSize / 2 / PPM, -tileSize / 2 / PPM
                );
                //top left
                v[1] = new Vector2(
                        -tileSize / 2 / PPM, tileSize / 2 / PPM
                );
                //top right
                v[2] = new Vector2(
                        tileSize / 2 / PPM, tileSize / 2 / PPM
                );

                chainShape.createChain(v);
                fdef.friction = 0f;
                fdef.shape = chainShape;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = B2DVariables.BIT_PLAYER;
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef).setUserData(layer.getName());

            }

    }

    //create coins method
    private void createCoins() {
        coins = new Array<Coin>();

        MapLayer layer = tiledMap.getLayers().get("Crystals"); //object layer
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (MapObject mo : layer.getObjects()) //iterate over each map object in that layer ie circle in our case
        {
            bdef.type = BodyDef.BodyType.StaticBody; //each crytsal is gona be static
            float x = Float.parseFloat(mo.getProperties().get("x").toString()) / PPM;
            float y = Float.parseFloat(mo.getProperties().get("y").toString()) / PPM;

            bdef.position.set(x, y);

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(12 / PPM);

            fdef.shape = circleShape;
            fdef.isSensor = true; //can travel through it
            fdef.filter.categoryBits = B2DVariables.BIT_CRYSTAL;
            fdef.filter.maskBits = B2DVariables.BIT_PLAYER;

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("crystal");

            Coin coin = new Coin(body);
            coins.add(coin);
            body.setUserData(coin);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);

        float playerx = player.getPosition().x;
        if (playerx * PPM > (mainCamera.position.x + mainCamera.viewportWidth * .25) && GameStateManager.tiledMapX - mainCamera.viewportWidth / 2 > mainCamera.position.x)
            mainCamera.position.set(playerx * PPM, mainCamera.position.y, 0);
//        float playerx = player.getPosition().x;
//                Gdx.app.log("Camera", "Playerx:" + playerx + " Camera: " + mainCamera.position.x + " width: " + mainCamera.viewportWidth);
        if (playerx * PPM < (mainCamera.position.x - mainCamera.viewportWidth * .25) && mainCamera.position.x - mainCamera.viewportWidth / 2 > 0)
            mainCamera.position.set(playerx * PPM, mainCamera.position.y, 0);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(mainCamera.combined);
    }

    public void update(float delta) {
        handleInput();
        world.step(1 / 30f, 6, 2);
        hudManager.update(delta, player.getNumCoins());
//
        //remove crystals from world after updating the world
        Array<Body> bodies = contactListener.getBodiesToRemove();
        for (int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            coins.removeValue((Coin) b.getUserData(), true);
            world.destroyBody(b);
        }
        bodies.clear();

        for (int i = 0; i < coins.size; i++) {
            coins.get(i).update(delta);
        }



    }


    public void handleInput() {

        // copy to JSON file the state of game
        if (hudManager.isInButton(MyInput.setMouseX, MyInput.setMouseY)) {
            GameStateManager.toJson();
            parent.setScreen(new MainMenu());
        }

        Vector2 vel = player.getBody().getLinearVelocity();
        Vector2 pos = player.getBody().getPosition();

        if (MyInput.isPressed(MyInput.JUMP)) {
            if (contactListener.isPlayerOnGround()) {
                player.getBody().applyLinearImpulse(0, 5, pos.x, pos.y, true);
//                float playerx = player.getPosition().x;
//                if (playerx * PPM > (mainCamera.position.x - mainCamera.viewportWidth * .25) && GameStateManager.tiledMapX - mainCamera.viewportWidth / 2 > mainCamera.position.x)
//                    mainCamera.translate(10, 0);

            }
        }

        if (MyInput.isDown(MyInput.RIGHT)) {
            if (contactListener.isPlayerOnGround() && (vel.x < MAX_VELOCITY)) {
                player.getBody().applyLinearImpulse(.1f, 0, pos.x, pos.y, true); //right

            }
        }

        if (MyInput.isDown(MyInput.LEFT)) {
            if (contactListener.isPlayerOnGround() && (vel.x > -MAX_VELOCITY)) {
                player.getBody().applyLinearImpulse(-.1f, 0, pos.x, pos.y, true); //left
            }
//            float playerx = player.getPosition().x;
//            if (playerx * PPM < (mainCamera.position.x - mainCamera.viewportWidth * .25) && mainCamera.position.x - mainCamera.viewportWidth / 2 > 0)
//                mainCamera.translate(-3, 0);
        }
    }

    private void createEnemies() {

        enemies = new Array<Enemies>();

        MapLayer layer = tiledMap.getLayers().get("Enemies"); //object layer
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (MapObject mo : layer.getObjects()) //iterate over each map object in that layer ie circle in our case
        {
            bdef.type = BodyDef.BodyType.StaticBody; //each crytsal is gona be static
            float x = Float.parseFloat(mo.getProperties().get("x").toString()) / PPM;
            float y = Float.parseFloat(mo.getProperties().get("y").toString()) / PPM;

            bdef.position.set(x, y);

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(12 / PPM);

            fdef.shape = circleShape;
            fdef.isSensor = true; //can travel through it
            fdef.filter.categoryBits = B2DVariables.BIT_ENEMY;
            fdef.filter.maskBits = B2DVariables.BIT_PLAYER;

            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("enemy");

            Enemies enemy = new Enemies(body);
            //enemies.add(enemy);
            body.setUserData(enemy);

        }
    }



    @Override
    public void dispose() {
        super.dispose();
        hudManager.dispose();
    }
}
