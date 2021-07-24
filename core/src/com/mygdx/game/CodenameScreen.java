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

public class CodenameScreen extends InputAdapter implements Screen {
    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private StringBuilder username;
    private boolean handledUserName;
    private boolean emptyField;

    public CodenameScreen(HackerGame hackerGame, SpriteBatch batch){
        this.hackerGame = hackerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        Assets.instance.font.setViewport(this.viewport);
    }
    @Override
    public void show() {
        username = new StringBuilder();
        handledUserName = false;
        emptyField = false;
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

        if(!handledUserName){
            handleUsernameInput(batch);
        }

        batch.end();

        if(handledUserName){
            Constants.MENU_SCREEN_NAME = username.toString();
            Constants.preferences = Gdx.app.getPreferences("user");

            Constants.preferences.putString("user", username.toString());
            Constants.preferences.flush();
            hackerGame.showMenuScreen();

        }
    }

    public void handleUsernameInput(SpriteBatch batch){
        if(username.length() < 10){
            if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
                username.append('A');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
                username.append('B');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
                username.append('C');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                username.append('D');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                username.append('E');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
                username.append('F');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
                username.append('G');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
                username.append('H');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
                username.append('I');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
                username.append('J');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
                username.append('K');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
                username.append('L');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
                username.append('M');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
                username.append('N');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
                username.append('O');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
                username.append('P');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
                username.append('Q');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
                username.append('R');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
                username.append('S');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
                username.append('T');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
                username.append('U');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
                username.append('V');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                username.append('W');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
                username.append('X');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Y)){
                username.append('Y');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
                username.append('Z');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)){
                username.append('0');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
                username.append('1');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
                username.append('2');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
                username.append('3');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
                username.append('4');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
                username.append('5');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
                username.append('6');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)){
                username.append('7');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)){
                username.append('8');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
                username.append('9');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                username.append(' ');
            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            if(username.length() - 1 >= 0){
                username.deleteCharAt(username.length() - 1);
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(username.length() == 0){
                emptyField = true;
            } else{
                handledUserName = true;
            }
        }
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.enterCodename, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        Assets.instance.font.drawGlitchEsportsFont(batch, "username", username.toString());

        if(emptyField){
            Assets.instance.font.drawGlitchEsportsFont(batch, "notice", "YOUR CODENAME CAN'T BE BLANK!");
        } else{
            Assets.instance.font.drawGlitchEsportsFont(batch, "instructions", "Must only include 1-10 characters of letters and/or numbers");
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
