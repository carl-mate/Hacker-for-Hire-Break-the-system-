package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Constants;
import util.Util;

public class OptionsScreen extends InputAdapter implements Screen {
    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private boolean musicOn;

    public OptionsScreen(HackerGame hackerGame, SpriteBatch batch){
        this.hackerGame = hackerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        musicOn = hackerGame.getMusicOn();
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
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.onOff, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        if(musicOn){
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.musicButtonOn, new Vector2(viewport.getCamera().viewportWidth / 1.25f, (viewport.getCamera().viewportHeight - Constants.MUSIC_BUTTON_HEIGHT) / 1.8f), Constants.MUSIC_BUTTON_CENTER);
        } else{
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.musicButtonOff, new Vector2(viewport.getCamera().viewportWidth / 1.25f, (viewport.getCamera().viewportHeight - Constants.MUSIC_BUTTON_HEIGHT) / 1.8f), Constants.MUSIC_BUTTON_CENTER);
        }

        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            hackerGame.showMenuScreen();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //playButton attributes
        Vector2 musicButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.25f, (viewport.getCamera().viewportHeight - Constants.MUSIC_BUTTON_HEIGHT) / 1.8f);
        Rectangle musicButtonBoundingBox = new Rectangle(musicButtonCenter.x - Constants.MUSIC_BUTTON_WIDTH / 2, musicButtonCenter.y - Constants.MUSIC_BUTTON_HEIGHT / 1.8f, Constants.MUSIC_BUTTON_WIDTH, Constants.MUSIC_BUTTON_HEIGHT);

        if(musicButtonBoundingBox.contains(worldTouch)){
            if(musicOn){
                musicOn = false;
                hackerGame.setMenuScreenMusicPause();
                hackerGame.setPlayScreenMusicPause();
            } else{
                musicOn = true;
                hackerGame.setMenuScreenMusicOn();
            }
            hackerGame.setMusicOnOrOff();
        }

        return true;
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
