package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Constants;
import util.Util;

import static util.Constants.UI_BUTTON_CENTER;
import static util.Constants.UI_BUTTON_HEIGHT;

public class MenuScreen extends InputAdapter implements Screen{
    private static final String TAG = MenuScreen.class.getName();

    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private long playButtonMouseHoverStartTime;
    private long optionsButtonMouseHoverStartTime;
    private long helpButtonMouseHoverStartTime;


    public MenuScreen(HackerGame hackerGame, SpriteBatch batch){
        this.hackerGame = hackerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        Assets.instance.font.setViewport(this.viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(
                0,
                0,
                0,
                0);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.mainMenuBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        //playButton attributes
        Vector2 playButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2.7f);
        Rectangle playButtonBoundingBox = new Rectangle(playButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, playButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        //optionsButton attributes
        Vector2 optionsButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4);
        Rectangle optionsButtonBoundingBox = new Rectangle(optionsButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, optionsButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        //helpButton attributes
        Vector2 helpButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 7.5f);
        Rectangle helpButtonBoundingBox = new Rectangle(helpButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, helpButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //hovered - playButton
        if(playButtonBoundingBox.contains(mousePosition)){
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.playButtonActive, new Vector2(viewport.getCamera().viewportWidth / 2, (viewport.getCamera().viewportHeight - UI_BUTTON_HEIGHT) / 2.7f), UI_BUTTON_CENTER);
            if(playButtonMouseHoverStartTime == 0){
                playButtonMouseHoverStartTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else{
            //not hovered - playButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.playButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, (viewport.getCamera().viewportHeight - UI_BUTTON_HEIGHT) / 2.7f), UI_BUTTON_CENTER);
            if(playButtonMouseHoverStartTime > 0){
                playButtonMouseHoverStartTime = 0;
            }
        }

        //hovered - optionsButton
        if(optionsButtonBoundingBox.contains(mousePosition)){
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.optionsButtonActive, new Vector2(viewport.getCamera().viewportWidth / 2, (viewport.getCamera().viewportHeight - UI_BUTTON_HEIGHT) / 4f), UI_BUTTON_CENTER);
            if(optionsButtonMouseHoverStartTime == 0){
                optionsButtonMouseHoverStartTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else{
            //not hovered - optionsButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.optionsButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, (viewport.getCamera().viewportHeight - UI_BUTTON_HEIGHT) / 4f), UI_BUTTON_CENTER);
            if(optionsButtonMouseHoverStartTime > 0){
                optionsButtonMouseHoverStartTime = 0;
            }
        }

        //hovered - helpButton
        if(helpButtonBoundingBox.contains(mousePosition)){
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.helpButtonActive, new Vector2(viewport.getCamera().viewportWidth / 2, (viewport.getCamera().viewportHeight - UI_BUTTON_HEIGHT) / 7.5f), UI_BUTTON_CENTER);
            if(helpButtonMouseHoverStartTime == 0){
                helpButtonMouseHoverStartTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else{
            //not hovered - helpButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.helpButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, (viewport.getCamera().viewportHeight - UI_BUTTON_HEIGHT) / 7.5f), UI_BUTTON_CENTER);
            if(helpButtonMouseHoverStartTime > 0){
                helpButtonMouseHoverStartTime = 0;
            }
        }

        Assets.instance.font.drawGlitchEsportsFont(batch, "usernameMenuScreen", Constants.MENU_SCREEN_NAME);

        batch.end();
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //playButton attributes
        Vector2 playButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4 + 40);
        Rectangle playButtonBoundingBox = new Rectangle(playButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, playButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        //optionsButton attributes
        Vector2 optionsButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4);
        Rectangle optionsButtonBoundingBox = new Rectangle(optionsButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, optionsButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        //helpButton attributes
        Vector2 helpButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 7.5f);
        Rectangle helpButtonBoundingBox = new Rectangle(helpButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, helpButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);


        if(playButtonBoundingBox.contains(worldTouch)){
            hackerGame.showDifficultyScreen();
        }

        if(optionsButtonBoundingBox.contains(worldTouch)){
            hackerGame.showOptionsScreen();
        }

        if(helpButtonBoundingBox.contains(worldTouch)){
            hackerGame.showHelpScreen();
        }

        return true;
    }

//    public void handleUsernameInput(SpriteBatch batch){
//        if(username.length() < 16){
//            if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
//                username.append('A');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
//                username.append('B');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
//                username.append('C');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
//                username.append('D');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
//                username.append('E');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
//                username.append('F');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
//                username.append('G');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
//                username.append('H');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
//                username.append('I');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
//                username.append('J');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
//                username.append('K');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
//                username.append('L');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
//                username.append('M');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
//                username.append('N');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
//                username.append('O');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
//                username.append('P');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
//                username.append('Q');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
//                username.append('R');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
//                username.append('S');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
//                username.append('T');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
//                username.append('U');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
//                username.append('V');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
//                username.append('W');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
//                username.append('X');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.Y)){
//                username.append('Y');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
//                username.append('Z');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)){
//                username.append('0');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
//                username.append('1');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
//                username.append('2');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
//                username.append('3');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
//                username.append('4');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
//                username.append('5');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
//                username.append('6');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)){
//                username.append('7');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)){
//                username.append('8');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
//                username.append('9');
//            }
//            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
//                username.append(' ');
//            }
//
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
//            if(username.length() - 1 >= 0){
//                username.deleteCharAt(username.length() - 1);
//            }
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
//            if(username.length() == 0){
//                emptyField = true;
//            } else{
//                handledUserName = true;
//            }
//        }
//        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.enterCodename, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
//        Assets.instance.font.drawGlitchEsportsFont(batch, "username", username.toString());
//
//        if(emptyField){
//            Assets.instance.font.drawGlitchEsportsFont(batch, "notice", "YOUR CODENAME CAN'T BE BLANK!");
//        } else{
//            Assets.instance.font.drawGlitchEsportsFont(batch, "instructions", "Must only include 1-16 characters of letters and/or numbers");
//        }
//    }

}
