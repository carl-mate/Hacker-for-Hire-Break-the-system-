package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Constants;
import util.Util;

public class HelpScreen extends InputAdapter implements Screen {

    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private int pageCounter;

    public HelpScreen(HackerGame hackerGame, SpriteBatch batch){
        this.hackerGame = hackerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        pageCounter = 0;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.binaryBackground, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(pageCounter-1 >= 0){
                pageCounter--;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(pageCounter+1 < 6){
                pageCounter++;
            }
        }

        if(pageCounter < 6){
            if(pageCounter == 0){
                Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.instructions1, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            } else if(pageCounter == 1){
                Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.instructions2, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            } else if(pageCounter == 2){
                Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.instructions3, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            } else if(pageCounter == 3){
                Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.actions1, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            } else if(pageCounter == 4){
                Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.actions2, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            } else if(pageCounter == 5){
                Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.actions3, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            }
        }

        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            hackerGame.showMenuScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}
