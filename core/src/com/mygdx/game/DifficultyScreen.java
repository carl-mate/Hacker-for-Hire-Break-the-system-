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
import util.Enums.Difficulty;
import util.Util;

public class DifficultyScreen extends InputAdapter implements Screen {
    private static final String TAG = MenuScreen.class.getName();

    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private long easyButtonHoverStartTime;
    private long mediumButtonHoverStartTime;
    private long hardButtonHoverStartTime;

    public Difficulty difficulty;

    public DifficultyScreen(HackerGame hackerGame, SpriteBatch batch){
        this.hackerGame = hackerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth(), viewport.getWorldHeight(), 0);
        difficulty = Difficulty.EASY;
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

        //easyButton attributes
        Vector2 easyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 40);
        Rectangle easyButtonBoundingBox = new Rectangle(easyButtonCenter.x - Constants.DIFFICULTY_BUTTON_WIDTH / 2, easyButtonCenter.y - Constants.DIFFICULTY_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_BUTTON_WIDTH, Constants.DIFFICULTY_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 mediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 240);
        Rectangle mediumButtonBoundingBox = new Rectangle(mediumButtonCenter.x - Constants.DIFFICULTY_BUTTON_WIDTH / 2, mediumButtonCenter.y - Constants.DIFFICULTY_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_BUTTON_WIDTH, Constants.DIFFICULTY_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 hardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 440);
        Rectangle hardButtonBoundingBox = new Rectangle(hardButtonCenter.x - Constants.DIFFICULTY_BUTTON_WIDTH / 2, hardButtonCenter.y - Constants.DIFFICULTY_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_BUTTON_WIDTH, Constants.DIFFICULTY_BUTTON_HEIGHT);

        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //checks which button is hovered and remains default until changed
        if(easyButtonBoundingBox.contains(mousePosition)) {
            difficulty = Difficulty.EASY;
            if(easyButtonHoverStartTime == 0){
                easyButtonHoverStartTime = TimeUtils.nanoTime();
                //reset other buttons
                mediumButtonHoverStartTime = 0;
                hardButtonHoverStartTime = 0;
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else if(mediumButtonBoundingBox.contains(mousePosition)){
            difficulty = Difficulty.MEDIUM;
            if(mediumButtonHoverStartTime == 0){
                mediumButtonHoverStartTime = TimeUtils.nanoTime();
                //reset other buttons
                easyButtonHoverStartTime = 0;
                hardButtonHoverStartTime = 0;
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else if(hardButtonBoundingBox.contains(mousePosition)){
            difficulty = Difficulty.HARD;
            if(hardButtonHoverStartTime == 0){
                hardButtonHoverStartTime = TimeUtils.nanoTime();
                //reset other buttons
                easyButtonHoverStartTime = 0;
                mediumButtonHoverStartTime = 0;
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        }


        if(difficulty == Difficulty.EASY){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonActive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 40), Constants.DIFFICULTY_BUTTON_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 240), Constants.DIFFICULTY_BUTTON_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 440), Constants.DIFFICULTY_BUTTON_CENTER);
        } else if(difficulty == Difficulty.MEDIUM){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 40), Constants.DIFFICULTY_BUTTON_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonActive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 240), Constants.DIFFICULTY_BUTTON_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 440), Constants.DIFFICULTY_BUTTON_CENTER);
        } else if(difficulty == Difficulty.HARD){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 40), Constants.DIFFICULTY_BUTTON_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 240), Constants.DIFFICULTY_BUTTON_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonActive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 440), Constants.DIFFICULTY_BUTTON_CENTER);
        }



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

        //easyButton attributes
        Vector2 easyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 40);
        Rectangle easyButtonBoundingBox = new Rectangle(easyButtonCenter.x - Constants.DIFFICULTY_BUTTON_WIDTH / 2, easyButtonCenter.y - Constants.DIFFICULTY_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_BUTTON_WIDTH, Constants.DIFFICULTY_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 mediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 240);
        Rectangle mediumButtonBoundingBox = new Rectangle(mediumButtonCenter.x - Constants.DIFFICULTY_BUTTON_WIDTH / 2, mediumButtonCenter.y - Constants.DIFFICULTY_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_BUTTON_WIDTH, Constants.DIFFICULTY_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 hardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + Constants.DIFFICULTY_BUTTON_HEIGHT - 440);
        Rectangle hardButtonBoundingBox = new Rectangle(hardButtonCenter.x - Constants.DIFFICULTY_BUTTON_WIDTH / 2, hardButtonCenter.y - Constants.DIFFICULTY_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_BUTTON_WIDTH, Constants.DIFFICULTY_BUTTON_HEIGHT);

        if(easyButtonBoundingBox.contains(worldTouch) || mediumButtonBoundingBox.contains(worldTouch) || hardButtonBoundingBox.contains(worldTouch)){
            hackerGame.showGameplayScreen(this.difficulty);
        }
        return true;
    }
}
