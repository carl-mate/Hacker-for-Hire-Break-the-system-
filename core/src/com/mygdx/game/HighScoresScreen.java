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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.TotalUsers;
import util.Assets;
import util.Constants;
import util.Util;

public class HighScoresScreen extends InputAdapter implements Screen {
    private static final String TAG = HighScoresScreen.class.getName();

    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private ArrayList<TotalUsers> totalUsers;

    public HighScoresScreen(HackerGame hackerGame, SpriteBatch batch){
        this.hackerGame = hackerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }
    @Override
    public void show() {
        totalUsers = new ArrayList<>();
        Gdx.app.log(TAG, "userCounter: " + Constants.preferences.getInteger("userCounter"));

        //store the totalUsers from preferences to local ArrayList
        for(int i = 0; i <= Constants.preferences.getInteger("userCounter"); i++){
            totalUsers.add(new TotalUsers(Constants.preferences.getString("user-"+i), Constants.preferences.getFloat("score-"+i)));
        }

        //rank total users from highest to lowest
        Collections.sort(totalUsers, new Comparator<TotalUsers>() {
            @Override
            public int compare(TotalUsers totalUsers, TotalUsers t1) {
                return Float.compare(t1.getScore(), totalUsers.getScore());
            }
        });

//        //test
//        for(int i = 0; i <= Constants.preferences.getInteger("userCounter"); i++){
//            Gdx.app.log(TAG, "USER " + i + ": " + totalUsers.get(i).getName() + "SCORE: " + totalUsers.get(i).getScore());
//        }

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
        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.highScoresBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        try{
            if(totalUsers.get(0) != null){
                Assets.instance.font.drawGlitchEsportsFont(batch, "highScoreFontOne", totalUsers.get(0).getName() + " --- " + totalUsers.get(0).getScore() + " BTC");
            }
            if(totalUsers.get(1) != null){
                Assets.instance.font.drawGlitchEsportsFont(batch, "highScoreFontTwo", totalUsers.get(1).getName() + " --- " + totalUsers.get(1).getScore() + " BTC");
            }
            if(totalUsers.get(2) != null){
                Assets.instance.font.drawGlitchEsportsFont(batch, "highScoreFontThree", totalUsers.get(2).getName() + " --- " + totalUsers.get(2).getScore() + " BTC");
            }
            if(totalUsers.get(3) != null){
                Assets.instance.font.drawGlitchEsportsFont(batch, "highScoreFontFour", totalUsers.get(3).getName() + " --- " + totalUsers.get(3).getScore() + " BTC");
            }
            if(totalUsers.get(4) != null){
                Assets.instance.font.drawGlitchEsportsFont(batch, "highScoreFontFive", totalUsers.get(4).getName() + " --- " + totalUsers.get(4).getScore() + " BTC");
            }
        } catch(Exception e){

        }

        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Constants.preferences.putString("user", Constants.MENU_SCREEN_NAME);
            Constants.preferences.flush();
            hackerGame.showMenuScreen();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            this.totalUsers.clear();
            Constants.preferences.clear();
            Constants.preferences.flush();
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


