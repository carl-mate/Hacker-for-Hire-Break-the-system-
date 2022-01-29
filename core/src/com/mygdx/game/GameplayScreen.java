package com.mygdx.game;

import static util.Constants.BITCOIN_LOGO_WIDTH;
import static util.Constants.CALL_ANOTHER_HACKER_CENTER;
import static util.Constants.CALL_ANOTHER_HACKER_HEIGHT;
import static util.Constants.CALL_ANOTHER_HACKER_WIDTH;
import static util.Constants.DDEM_PROBABILITY_ALGORITHM_CENTER;
import static util.Constants.DDEM_PROBABILITY_ALGORITHM_HEIGHT;
import static util.Constants.DDEM_PROBABILITY_ALGORITHM_WIDTH;
import static util.Constants.INDICATOR_CENTER;
import static util.Constants.LIFE_ANIMATION_CENTER;
import static util.Constants.PLAY_SCREEN_MENU_BUTTON_CENTER;
import static util.Constants.PROGRESS_BAR_WIDTH;
import static util.Constants.SSH_INTO_A_SUPERCOMPUTER_CENTER;
import static util.Constants.SSH_INTO_A_SUPERCOMPUTER_HEIGHT;
import static util.Constants.SSH_INTO_A_SUPERCOMPUTER_WIDTH;
import static util.Constants.UI_BUTTON_CENTER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entity.Indicator;
import entity.PlayScreenButton;
import util.Assets;
import util.Constants;
import util.Enums.Difficulty;
import util.Util;

public class GameplayScreen extends InputAdapter implements Screen {
    public static final String TAG = GameplayScreen.class.getName();

    private HackerGame hackerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private Questions questions;
    private DelayedRemovalArray<Indicator> indicators;
    private PlayScreenButton playScreenButton;
    private boolean correct;
    private boolean wrong;
    private long roundElapsedTime;
    private String correctChoice;
    private Difficulty difficulty;
    private Random rand;
    private double[] randomDouble;
    private ArrayList<Double> dummyDoubles;
    private double sum;
    private double percentageMax;
    private int percentageMaxIndex;
    private boolean lucky;

    private double valueA;
    private double valueB;
    private double valueC;
    private double valueD;

    private int chances;
    private int questionsCounter;
    private int progressCounter;

    private int callAnotherHackerCurrentQuestion;
    private int sshIntoASuperComputerCurrentQuestion;
    private int ddemProbabilityAlgorithmCurrentQuestion;
    private int callAnotherHackerCtr;
    private int sshIntoASuperComputerCtr;
    private int ddemProbabilityAlgorithmCtr;

    private boolean choiceAIsActive;
    private boolean choiceBIsActive;
    private boolean choiceCIsActive;
    private boolean choiceDIsActive;

    private boolean choiceAIsClicked;
    private boolean choiceBIsClicked;
    private boolean choiceCIsClicked;
    private boolean choiceDIsClicked;

    private List<Character> choices;
    private ArrayList<Character> remove;

    private long removeStartTime;
    private long ddemStartTime;

    private long tryAgainButtonMouseHoverStartTime;
    private long returnToMenuButtonMouseHoverStartTime;
    private long highScoresButtonMouseHoverStartTime;
    private long storeScoreStartTime;
    private int userCounter;

    private float score;

    private int gameOverSoundIndex;
    private int victorySoundIndex;

    private Vector2 questionCenter;
    private Rectangle questionRectangleBounds;
    private Vector2 choiceAButtonCenterText;
    private Rectangle choiceAButtonBoundingBoxText;
    private Vector2 choiceCButtonCenterText;
    private Rectangle choiceCButtonBoundingBoxText;
    private Vector2 choiceBButtonCenterText;
    private Rectangle choiceBButtonBoundingBoxText;
    private Vector2 choiceDButtonCenterText;
    private Rectangle choiceDButtonBoundingBoxText;
    //private Vector2 topicCenter;
    //private Rectangle topicRectangleBounds;

    public GameplayScreen(HackerGame hackerGame, Difficulty difficulty, SpriteBatch batch) {
        this.hackerGame = hackerGame;
        this.batch = batch;
        this.difficulty = difficulty;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        Assets.instance.font.setViewport(this.viewport);
        indicators = new DelayedRemovalArray<>();
        remove = new ArrayList<>();
        correct = false;
        wrong = false;
        playScreenButton = new PlayScreenButton(this.viewport);

        try {
            questions = new Questions(viewport, difficulty);
        } catch (IOException e) {
            e.printStackTrace();
        }


        rand = new Random();
        randomDouble = new double[4];
        dummyDoubles = new ArrayList<>();
        sum = 0;
        percentageMax = 0;
        percentageMaxIndex = 0;
        lucky = false;

        valueA = 0;
        valueB = 0;
        valueC = 0;
        valueD = 0;

        chances = Constants.CHANCES;
        questionsCounter = 1;
        progressCounter = 29;

        choiceAIsActive = true;
        choiceBIsActive = true;
        choiceCIsActive = true;
        choiceDIsActive = true;

        callAnotherHackerCtr = 1;
        sshIntoASuperComputerCtr = 1;
        ddemProbabilityAlgorithmCtr = 1;
        removeStartTime = 0;

        questionsCounter = 0;

        userCounter = 0;

        //question
        questionCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2); 
        questionRectangleBounds = new Rectangle(questionCenter.x - Constants.QUESTION_BOX_WIDTH / 2, questionCenter.y - Constants.QUESTION_BOX_HEIGHT / 2, Constants.QUESTION_BOX_WIDTH, Constants.QUESTION_BOX_HEIGHT);

        //choiceA
        choiceAButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 3.8f);
        choiceAButtonBoundingBoxText = new Rectangle(choiceAButtonCenterText.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceAButtonCenterText.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //choiceB
        choiceBButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 3.8f);
        choiceBButtonBoundingBoxText = new Rectangle(choiceBButtonCenterText.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceBButtonCenterText.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //choiceC
        choiceCButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 100);
        choiceCButtonBoundingBoxText = new Rectangle(choiceCButtonCenterText.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceCButtonCenterText.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //choiceD
        choiceDButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 100);
        choiceDButtonBoundingBoxText = new Rectangle(choiceDButtonCenterText.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceDButtonCenterText.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);


        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    } @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        batch.dispose();
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

        renderUI(batch);
        renderScore(batch);

        indicators.begin();
        for (int i = 0; i < indicators.size; i++) {
            if (indicators.get(i).isWrongIndicatorFinished()) {
                wrong = false;
                indicators.removeIndex(i);
            }
            if (indicators.get(i).isCorrectIndicatorFinished()) {
                correct = false;
                indicators.removeIndex(i);
            }
        }
        indicators.end();

        for (Indicator x : indicators) {
            if (correct) {
                x.renderCorrect(batch);
            } else if (wrong) {
                x.renderWrong(batch);
            }
        }

        renderQuestions(batch);


        if (chances < 0) {
            renderGameOver(batch);
        } else if (progressCounter > 30) {
            renderWin(batch);
        }

        batch.end();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //menu button
        Vector2 playScreenMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 7, viewport.getCamera().viewportHeight - 20);
        Rectangle playScreenMenuButtonBoundingBox = new Rectangle(playScreenMenuButtonCenter.x - Constants.PLAY_SCREEN_MENU_BUTTON_WIDTH / 2, playScreenMenuButtonCenter.y - Constants.PLAY_SCREEN_MENU_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_MENU_BUTTON_WIDTH, Constants.PLAY_SCREEN_MENU_BUTTON_HEIGHT);

        //choiceA
        Vector2 choiceAButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 3.8f);
        Rectangle choiceAButtonBoundingBox = new Rectangle(choiceAButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceAButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //choiceB
        Vector2 choiceBButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 3.8f);
        Rectangle choiceBButtonBoundingBox = new Rectangle(choiceBButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceBButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //choiceC
        Vector2 choiceCButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 100);
        Rectangle choiceCButtonBoundingBox = new Rectangle(choiceCButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceCButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //choiceD
        Vector2 choiceDButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 100);
        Rectangle choiceDButtonBoundingBox = new Rectangle(choiceDButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceDButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

        //callAnotherHackerButton attributes
        Vector2 callAnotherHackerButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 18, viewport.getCamera().viewportHeight - CALL_ANOTHER_HACKER_HEIGHT - 50);
        Rectangle callAnotherHackerButtonBoundingBox = new Rectangle(callAnotherHackerButtonCenter.x - Constants.CALL_ANOTHER_HACKER_WIDTH / 2, callAnotherHackerButtonCenter.y - CALL_ANOTHER_HACKER_HEIGHT / 2, CALL_ANOTHER_HACKER_WIDTH, CALL_ANOTHER_HACKER_HEIGHT);

        //sshIntoASuperComputerButton attributes
        Vector2 sshIntoASuperComputerButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 8, viewport.getCamera().viewportHeight - SSH_INTO_A_SUPERCOMPUTER_HEIGHT - 50);
        Rectangle sshIntoASuperComputerButtonBoundingBox = new Rectangle(sshIntoASuperComputerButtonCenter.x - Constants.SSH_INTO_A_SUPERCOMPUTER_WIDTH / 2, sshIntoASuperComputerButtonCenter.y - SSH_INTO_A_SUPERCOMPUTER_HEIGHT / 2, SSH_INTO_A_SUPERCOMPUTER_WIDTH, SSH_INTO_A_SUPERCOMPUTER_HEIGHT);

        //ddemProbabilityAlgorithmButton attributes
        Vector2 ddemProbabilityAlgorithmButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 5, viewport.getCamera().viewportHeight - DDEM_PROBABILITY_ALGORITHM_HEIGHT - 50);
        Rectangle ddemProbabilityAlgorithmButtonBoundingBox = new Rectangle(ddemProbabilityAlgorithmButtonCenter.x - DDEM_PROBABILITY_ALGORITHM_WIDTH / 2, ddemProbabilityAlgorithmButtonCenter.y - DDEM_PROBABILITY_ALGORITHM_HEIGHT / 2, DDEM_PROBABILITY_ALGORITHM_WIDTH, DDEM_PROBABILITY_ALGORITHM_HEIGHT);

        if (chances >= 0 && progressCounter <= 30) {
            if (callAnotherHackerButtonBoundingBox.contains(worldTouch)) {
                if (callAnotherHackerCtr - 1 >= 0) {
                    callAnotherHackerCtr--;
                    callAnotherHackerCurrentQuestion = questionsCounter; //memorize current round to ensure that the action only works for one round
                }
            } else if (sshIntoASuperComputerButtonBoundingBox.contains(worldTouch)) {
                if (sshIntoASuperComputerCtr - 1 >= 0) {
                    sshIntoASuperComputerCtr--;
                    sshIntoASuperComputerCurrentQuestion = questionsCounter; //memorize current round to ensure that the action only works for one round
                }
            } else if (ddemProbabilityAlgorithmButtonBoundingBox.contains(worldTouch)) {
                if (ddemProbabilityAlgorithmCtr - 1 >= 0) {
                    ddemProbabilityAlgorithmCtr--;
                    ddemProbabilityAlgorithmCurrentQuestion = questionsCounter; //memorize current round to ensure that the action only works for one round
                }
            }

            if (playScreenMenuButtonBoundingBox.contains(worldTouch)) {
                hackerGame.showMenuScreen();
            }

            if (choiceAButtonBoundingBox.contains(worldTouch) || choiceBButtonBoundingBox.contains(worldTouch) || choiceCButtonBoundingBox.contains(worldTouch) || choiceDButtonBoundingBox.contains(worldTouch)) {
                if (correctChoice.equals("A") && choiceAButtonBoundingBox.contains(worldTouch)) {
                    judgeAnswer(true);
                } else if (correctChoice.equals("A") && !choiceAButtonBoundingBox.contains(worldTouch)) {
                    if (choiceBIsActive && choiceBButtonBoundingBox.contains(worldTouch) || choiceCIsActive && choiceCButtonBoundingBox.contains(worldTouch) || choiceDIsActive && choiceDButtonBoundingBox.contains(worldTouch)) {
                        judgeAnswer(false);
                    }
                }
                if (correctChoice.equals("B") && choiceBButtonBoundingBox.contains(worldTouch)) {
                    judgeAnswer(true);
                } else if (correctChoice.equals("B") && !choiceBButtonBoundingBox.contains(worldTouch)) {
                    if (choiceAIsActive && choiceAButtonBoundingBox.contains(worldTouch) || choiceCIsActive && choiceCButtonBoundingBox.contains(worldTouch) || choiceDIsActive && choiceDButtonBoundingBox.contains(worldTouch)) {
                        judgeAnswer(false);
                    }
                }
                if (correctChoice.equals("C") && choiceCButtonBoundingBox.contains(worldTouch)) {
                    judgeAnswer(true);
                } else if (correctChoice.equals("C") && !choiceCButtonBoundingBox.contains(worldTouch)) {
                    if (choiceAIsActive && choiceAButtonBoundingBox.contains(worldTouch) || choiceBIsActive && choiceBButtonBoundingBox.contains(worldTouch) || choiceDIsActive && choiceDButtonBoundingBox.contains(worldTouch)) {
                        judgeAnswer(false);
                    }

                }
                if (correctChoice.equals("D") && choiceDButtonBoundingBox.contains(worldTouch)) {
                    judgeAnswer(true);
                } else if (correctChoice.equals("D") && !choiceDButtonBoundingBox.contains(worldTouch)) {
                    if (choiceAIsActive && choiceAButtonBoundingBox.contains(worldTouch) || choiceBIsActive && choiceBButtonBoundingBox.contains(worldTouch) || choiceCIsActive && choiceCButtonBoundingBox.contains(worldTouch)) {
                        judgeAnswer(false);
                    }
                }
            }
        }

        choiceAIsClicked = choiceAButtonBoundingBox.contains(worldTouch);
        choiceCIsClicked = choiceCButtonBoundingBox.contains(worldTouch);
        choiceBIsClicked = choiceBButtonBoundingBox.contains(worldTouch);
        choiceDIsClicked = choiceDButtonBoundingBox.contains(worldTouch);


        if (chances < 0 || progressCounter > 30) {
            //tryAgain attributes
            Vector2 tryAgainButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 3.4f);
            Rectangle tryAgainButtonBoundingBox = new Rectangle(tryAgainButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, tryAgainButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

            //highScores attributes
            Vector2 highScoresButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 5.4f);
            Rectangle highScoresButtonBoundingBox = new Rectangle(highScoresButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, highScoresButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

            //returnToMenu attributes
            Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 13f);
            Rectangle returnToMenuButtonBoundingBox = new Rectangle(returnToMenuButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

            if (tryAgainButtonBoundingBox.contains(worldTouch)) {
                if(Assets.instance.musicClass.gameOverSound.isPlaying()){
                    Assets.instance.musicClass.gameOverSound.stop();
                }
                if(Assets.instance.musicClass.victorySound.isPlaying()){
                    Assets.instance.musicClass.victorySound.stop();
                }
                hackerGame.showDifficultyScreen();
            } else if(highScoresButtonBoundingBox.contains(worldTouch)){
                if(Assets.instance.musicClass.gameOverSound.isPlaying()){
                    Assets.instance.musicClass.gameOverSound.stop();
                }
                if(Assets.instance.musicClass.victorySound.isPlaying()){
                    Assets.instance.musicClass.victorySound.stop();
                }
                hackerGame.showHighScoresScreen();
            } else if (returnToMenuButtonBoundingBox.contains(worldTouch)) {
                if(Assets.instance.musicClass.gameOverSound.isPlaying()){
                    Assets.instance.musicClass.gameOverSound.stop();
                }
                if(Assets.instance.musicClass.victorySound.isPlaying()){
                    Assets.instance.musicClass.victorySound.stop();
                }
                hackerGame.showMenuScreen();
            }
        }


        return true;
    }

    //UTILITY FUNCTIONS
    private void judgeAnswer(boolean correct) {
        if (correct) {
            this.correct = true;
            indicators.add(new Indicator(viewport));
            Gdx.app.log(TAG, "CORRECT");
            Assets.instance.soundClass.correctSFX.play();
            if (questions.getQuestionDifficulty().equalsIgnoreCase("easy")) {  //easy question
                if (Util.secondsSince(roundElapsedTime) >= 300) {
                    score += Constants.EASY_SCORE_300;
                } else if (Util.secondsSince(roundElapsedTime) >= 200) {
                    score += Constants.EASY_SCORE_200_299;
                } else if (Util.secondsSince(roundElapsedTime) >= 100) {
                    score += Constants.EASY_SCORE_100_199;
                } else if (Util.secondsSince(roundElapsedTime) >= 0) {
                    score += Constants.EASY_SCORE_0_99;
                }
            } else if (questions.getQuestionDifficulty().equalsIgnoreCase("medium")) { //medium question
                if (Util.secondsSince(roundElapsedTime) >= 300) {
                    score += Constants.MEDIUM_SCORE_300;
                } else if (Util.secondsSince(roundElapsedTime) >= 200) {
                    score += Constants.MEDIUM_SCORE_200_299;
                } else if (Util.secondsSince(roundElapsedTime) >= 100) {
                    score += Constants.MEDIUM_SCORE_100_199;
                } else if (Util.secondsSince(roundElapsedTime) >= 0) {
                    score += Constants.MEDIUM_SCORE_0_99;
                }
            } else if (questions.getQuestionDifficulty().equalsIgnoreCase("hard")) { //hard question
                if (Util.secondsSince(roundElapsedTime) >= 300) {
                    score += Constants.HARD_SCORE_300;
                } else if (Util.secondsSince(roundElapsedTime) >= 200) {
                    score += Constants.HARD_SCORE_200_299;
                } else if (Util.secondsSince(roundElapsedTime) >= 100) {
                    score += Constants.HARD_SCORE_100_199;
                } else if (Util.secondsSince(roundElapsedTime) >= 0) {
                    score += Constants.HARD_SCORE_0_99;
                }
            }
            progressCounter++; //add to progress if the answer is correct
            questionsCounter++;
        } else {
            this.wrong = true;
            indicators.add(new Indicator(viewport));
            Gdx.app.log(TAG, "WRONG");
            Assets.instance.soundClass.wrongSFX.play();
            questionsCounter++;
            chances--;
        }
        resetRoundElapsedTime();
        questions.resetStartTime();
    }

    public void endGameButtons(SpriteBatch batch) {
        //tryAgain attributes
        Vector2 tryAgainButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 3.4f);
        Rectangle tryAgainButtonBoundingBox = new Rectangle(tryAgainButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, tryAgainButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        //highScores attributes
        Vector2 highScoresButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 5.4f);
        Rectangle highScoresButtonBoundingBox = new Rectangle(highScoresButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, highScoresButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        //returnToMenu attributes
        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 13f);
        Rectangle returnToMenuButtonBoundingBox = new Rectangle(returnToMenuButtonCenter.x - Constants.UI_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.UI_BUTTON_HEIGHT / 2, Constants.UI_BUTTON_WIDTH, Constants.UI_BUTTON_HEIGHT);

        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //hovered - tryAgain
        if (tryAgainButtonBoundingBox.contains(mousePosition)) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.tryAgainActive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 3.4f), UI_BUTTON_CENTER);
            if (tryAgainButtonMouseHoverStartTime == 0) {
                tryAgainButtonMouseHoverStartTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else {
            //not hovered - tryAgain
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.tryAgainInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 3.4f), UI_BUTTON_CENTER);
            if (tryAgainButtonMouseHoverStartTime > 0) {
                tryAgainButtonMouseHoverStartTime = 0;
            }
        }

        //hovered - highScores
        if (highScoresButtonBoundingBox.contains(mousePosition)) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.highScoresActive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 5.4f), UI_BUTTON_CENTER);
            if (highScoresButtonMouseHoverStartTime == 0) {
                highScoresButtonMouseHoverStartTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else {
            //not hovered - highScores
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.highScoresInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 5.4f), UI_BUTTON_CENTER);
            if (highScoresButtonMouseHoverStartTime > 0) {
                highScoresButtonMouseHoverStartTime = 0;
            }
        }

        //hovered - returnToMenu
        if (returnToMenuButtonBoundingBox.contains(mousePosition)) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.returnToMenuActive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 13f), UI_BUTTON_CENTER);
            if (returnToMenuButtonMouseHoverStartTime == 0) {
                returnToMenuButtonMouseHoverStartTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.mouseHoverSound.play();
            }
        } else {
            //not hovered - returnToMenu
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.returnToMenuInactive, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 13f), UI_BUTTON_CENTER);
            if (returnToMenuButtonMouseHoverStartTime > 0) {
                returnToMenuButtonMouseHoverStartTime = 0;
            }
        }
    }

    public void renderWin(SpriteBatch batch) {
        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.youWin, new Vector2(0, 0));
        renderScore(batch);
        endGameButtons(batch);

        if (victorySoundIndex == 0) {
            hackerGame.setPlayScreenMusicOff();
            Assets.instance.musicClass.victorySound.play();
            victorySoundIndex++;
        }
    }

    public void renderGameOver(SpriteBatch batch) {
        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.gameOver, new Vector2(0, 0));
        renderScore(batch);
        endGameButtons(batch);

        if (gameOverSoundIndex == 0) {
            hackerGame.setPlayScreenMusicOff();
            Assets.instance.musicClass.gameOverSound.play();
            gameOverSoundIndex++;
        }

    }

    public void renderQuestions(SpriteBatch batch) {
        if(chances >= 0 && progressCounter <= 30){

            questions.initializeQuestions();
            Assets.instance.font.drawGlitchEsportsFont(batch, "question", questions.getQuestion(), this.questionRectangleBounds);

            /*
               ACTION CASES:
               (1) Call another hacker - display playScreen Button in green (100% reliable)
               (2) SSH into a supercomputer - randomly remove TWO incorrect answers (50% reliable)
               (3) DDEM Probability Algorithm - display in percentages the most likely answer (25% reliable)
               */

            //Case (1) Call another hacker and Case (3) DDEM Probability Algorithm don't need a special case for the questions

            //Case (2) SSH into a supercomputer
            if (sshIntoASuperComputerCtr == 0) {
                String correctChoice = questions.getCorrectChoice();

                switch (correctChoice) {
                    case "A":
                        //A
                        Assets.instance.font.drawGlitchEsportsFont(batch, "choiceA", questions.getChoiceA(), this.choiceAButtonBoundingBoxText);

                        if (!remove.contains('B')) { //remove C, D AND display B, A
                            //B
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceB", questions.getChoiceB(), this.choiceBButtonBoundingBoxText);
                        } else if (!remove.contains('C')) { //remove B, D AND display C, A
                            //C
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceC", questions.getChoiceC(), this.choiceCButtonBoundingBoxText);
                        } else if (!remove.contains('D')) { //remove B, C AND display D, A
                            //D
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceD", questions.getChoiceD(), this.choiceDButtonBoundingBoxText);
                        }
                        break;
                    case "B":
                        //B
                        Assets.instance.font.drawGlitchEsportsFont(batch, "choiceB", questions.getChoiceB(), this.choiceBButtonBoundingBoxText);

                        if (!remove.contains('A')) { //remove C, D AND display A, B
                            //A
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceA", questions.getChoiceA(), this.choiceAButtonBoundingBoxText);
                        } else if (!remove.contains('C')) { //remove A, D AND display C, B
                            //C
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceC", questions.getChoiceC(), this.choiceCButtonBoundingBoxText);
                        } else if (!remove.contains('D')) { //remove A, C AND display D, B
                            //D
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceD", questions.getChoiceD(), this.choiceDButtonBoundingBoxText);
                        }
                        break;
                    case "C":
                        //C
                        Assets.instance.font.drawGlitchEsportsFont(batch, "choiceC", questions.getChoiceC(), this.choiceCButtonBoundingBoxText);

                        if (!remove.contains('A')) { //remove B, D AND display A, C
                            //A
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceA", questions.getChoiceA(), this.choiceAButtonBoundingBoxText);
                        } else if (!remove.contains('B')) { //remove A, D AND display B, C
                            //B
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceB", questions.getChoiceB(), this.choiceBButtonBoundingBoxText);
                        } else if (!remove.contains('D')) { //remove A, B AND display D, C
                            //D
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceD", questions.getChoiceD(), this.choiceDButtonBoundingBoxText);
                        }
                        break;
                    case "D":
                        //D
                        Assets.instance.font.drawGlitchEsportsFont(batch, "choiceD", questions.getChoiceD(), this.choiceDButtonBoundingBoxText);

                        if (!remove.contains('A')) { //remove B, C AND display A, D
                            //A
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceA", questions.getChoiceA(), this.choiceAButtonBoundingBoxText);
                        } else if (!remove.contains('B')) { //remove A, C AND display B, D
                            //B
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceB", questions.getChoiceB(), this.choiceBButtonBoundingBoxText);
                        } else if (!remove.contains('C')) { //remove A, B AND display C, D
                            //C
                            Assets.instance.font.drawGlitchEsportsFont(batch, "choiceC", questions.getChoiceC(), this.choiceCButtonBoundingBoxText);
                        }
                        break;
                }
            } else {
                //A
                Assets.instance.font.drawGlitchEsportsFont(batch, "choiceA", questions.getChoiceA(), this.choiceAButtonBoundingBoxText);

                //C
                Assets.instance.font.drawGlitchEsportsFont(batch, "choiceC", questions.getChoiceC(), this.choiceCButtonBoundingBoxText);

                //B
                Assets.instance.font.drawGlitchEsportsFont(batch, "choiceB", questions.getChoiceB(), this.choiceBButtonBoundingBoxText);

                //D
                Assets.instance.font.drawGlitchEsportsFont(batch, "choiceD", questions.getChoiceD(), this.choiceDButtonBoundingBoxText);

                correctChoice = questions.getCorrectChoice();
            }

            String imageFilename = questions.getImageFileName();
            Gdx.app.log(TAG, imageFilename);

            try {
                if (!imageFilename.equals("null")) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "instructions", "PRESS AND HOLD V TO VIEW IMAGE");
                    Texture image = Assets.instance.resourcesFilePath.image.get(imageFilename);

                    if (Gdx.input.isKeyPressed(Input.Keys.V)) {
                        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenImageFrame, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
                        Util.drawTextureRegion(batch, image, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), new Vector2(image.getWidth() / 2f, image.getHeight() / 2.5f));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void renderUI(SpriteBatch batch) {
        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenMenuButton, new Vector2(viewport.getCamera().viewportWidth / 7, viewport.getCamera().viewportHeight - 20), PLAY_SCREEN_MENU_BUTTON_CENTER);

        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.correctIndicator1, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 100), INDICATOR_CENTER);

        if (chances >= 0 && progressCounter <= 30) { //ONLY update when game is not yet over
            /*
               ACTION CASES:
               (1) Call another hacker - display playScreen Button in green (100% reliable)
               (2) SSH into a supercomputer - randomly remove TWO incorrect answers (50% reliable)
               (3) DDEM Probability Algorithm - display in percentages the most likely answer (25% reliable)
               */

            if (callAnotherHackerCtr == 0 || sshIntoASuperComputerCtr == 0 || ddemProbabilityAlgorithmCtr == 0) {

                //Case (1) Call another hacker
                if (callAnotherHackerCtr == 0) {
                    if (callAnotherHackerCurrentQuestion == questionsCounter) {
                        Gdx.app.log(TAG, "CALL ANOTHER HACKER");
                        String correctChoice = questions.getCorrectChoice();
                        switch (correctChoice) {
                            case "A":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('B')) { //remove C, D AND display B, A
                                        playScreenButton.renderButton(batch, "B", "CorrectA");
                                    } else if (!remove.contains('C')) { //remove B, D AND display C, A
                                        playScreenButton.renderButton(batch, "C", "CorrectA");
                                    } else if (!remove.contains('D')) { //remove B, C AND display D, A
                                        playScreenButton.renderButton(batch, "D", "CorrectA");
                                    }
                                } else {
                                    playScreenButton.renderButton(batch, "CorrectA", "B", "C", "D");
                                }
                                break;
                            case "B":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('A')) { //remove C, D AND display A, B
                                        playScreenButton.renderButton(batch, "A", "CorrectB");
                                    } else if (!remove.contains('C')) { //remove A, D AND display C, B
                                        playScreenButton.renderButton(batch, "C", "CorrectB");
                                    } else if (!remove.contains('D')) { //remove A, C AND display D, B
                                        playScreenButton.renderButton(batch, "D", "CorrectB");
                                    }
                                } else {
                                    playScreenButton.renderButton(batch, "CorrectB", "A", "C", "D");
                                }
                                break;
                            case "C":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('A')) { //remove B, D AND display A, C
                                        playScreenButton.renderButton(batch, "A", "CorrectC");
                                    } else if (!remove.contains('B')) { //remove A, D AND display B, C
                                        playScreenButton.renderButton(batch, "B", "CorrectC");
                                    } else if (!remove.contains('D')) { //remove A, B AND display D, C
                                        playScreenButton.renderButton(batch, "D", "CorrectC");
                                    }
                                } else {
                                    playScreenButton.renderButton(batch, "CorrectC", "A", "B", "D");
                                }
                                break;
                            case "D":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('A')) { //remove B, C AND display A, D
                                        playScreenButton.renderButton(batch, "A", "CorrectD");
                                    } else if (!remove.contains('B')) { //remove A, C AND display B, D
                                        playScreenButton.renderButton(batch, "B", "CorrectD");
                                    } else if (!remove.contains('C')) { //remove A, B AND display C, D
                                        playScreenButton.renderButton(batch, "C", "CorrectD");
                                    }
                                } else {
                                    playScreenButton.renderButton(batch, "CorrectD", "A", "B", "C");
                                }
                                break;
                        }
                    } else {
                        callAnotherHackerCtr--; //break out of the condition
                    }

                } else if (sshIntoASuperComputerCtr == 0) { //Case (2) SSH Into A Supercomputer
                    if (sshIntoASuperComputerCurrentQuestion == questionsCounter) {
                        Gdx.app.log(TAG, "SSH INTO A SUPERCOMPUTER");
                        String correctChoice = questions.getCorrectChoice();
                        switch (correctChoice) {
                            case "A":
                                //Randomly Select TWO to remove from B, C, D
                                choices = new ArrayList<>(Arrays.asList('B', 'C', 'D'));

                                if (removeStartTime == 0) {
                                    Collections.shuffle(choices);
                                    for (int i = 0; i < 2; i++) {
                                        remove.add(choices.get(i));
                                    }
                                    removeStartTime = TimeUtils.nanoTime();
                                }


                                if (!remove.contains('B')) { //remove C, D
                                    playScreenButton.renderButton(batch, "A", "B");
                                    choiceAIsActive = true;
                                    choiceBIsActive = true;
                                    choiceCIsActive = false;
                                    choiceDIsActive = false;
                                } else if (!remove.contains('C')) { //remove B, D
                                    playScreenButton.renderButton(batch, "A", "C");
                                    choiceAIsActive = true;
                                    choiceBIsActive = false;
                                    choiceCIsActive = true;
                                    choiceDIsActive = false;
                                } else if (!remove.contains('D')) { //remove B, C
                                    playScreenButton.renderButton(batch, "A", "D");
                                    choiceAIsActive = true;
                                    choiceBIsActive = false;
                                    choiceCIsActive = false;
                                    choiceDIsActive = true;
                                }
                                break;
                            case "B":
                                //Randomly Select TWO to remove from A, C, D
                                choices = new ArrayList<>(Arrays.asList('A', 'C', 'D'));

                                if (removeStartTime == 0) {
                                    Collections.shuffle(choices);

                                    for (int i = 0; i < 2; i++) {
                                        remove.add(choices.get(i));
                                    }
                                    removeStartTime = TimeUtils.nanoTime();
                                }

                                if (!remove.contains('A')) { //remove C, D
                                    playScreenButton.renderButton(batch, "B", "A");
                                    choiceAIsActive = true;
                                    choiceBIsActive = true;
                                    choiceCIsActive = false;
                                    choiceDIsActive = false;
                                } else if (!remove.contains('C')) { //remove A, D
                                    playScreenButton.renderButton(batch, "B", "C");
                                    choiceAIsActive = false;
                                    choiceBIsActive = true;
                                    choiceCIsActive = true;
                                    choiceDIsActive = false;
                                } else if (!remove.contains('D')) { //remove A, C
                                    playScreenButton.renderButton(batch, "B", "D");
                                    choiceAIsActive = false;
                                    choiceBIsActive = true;
                                    choiceCIsActive = false;
                                    choiceDIsActive = true;
                                }
                                break;
                            case "C":
                                //Randomly Select TWO to remove from A, B, D
                                choices = new ArrayList<>(Arrays.asList('A', 'B', 'D'));

                                if (removeStartTime == 0) {
                                    Collections.shuffle(choices);

                                    for (int i = 0; i < 2; i++) {
                                        remove.add(choices.get(i));
                                    }
                                    removeStartTime = TimeUtils.nanoTime();
                                }

                                if (!remove.contains('A')) { //remove B, D
                                    playScreenButton.renderButton(batch, "C", "A");
                                    choiceAIsActive = true;
                                    choiceBIsActive = false;
                                    choiceCIsActive = true;
                                    choiceDIsActive = false;
                                } else if (!remove.contains('B')) { //remove A, D
                                    playScreenButton.renderButton(batch, "C", "B");
                                    choiceAIsActive = false;
                                    choiceBIsActive = true;
                                    choiceCIsActive = true;
                                    choiceDIsActive = false;
                                } else if (!remove.contains('D')) { //remove A, B
                                    playScreenButton.renderButton(batch, "C", "D");
                                    choiceAIsActive = false;
                                    choiceBIsActive = false;
                                    choiceCIsActive = true;
                                    choiceDIsActive = true;
                                }
                                break;
                            case "D":
                                //Randomly Select TWO to remove from A, B, C
                                choices = new ArrayList<>(Arrays.asList('A', 'B', 'C'));

                                if (removeStartTime == 0) {
                                    Collections.shuffle(choices);
                                    for (int i = 0; i < 2; i++) {
                                        remove.add(choices.get(i));
                                    }
                                    removeStartTime = TimeUtils.nanoTime();
                                }

                                if (!remove.contains('A')) { //remove B, C
                                    playScreenButton.renderButton(batch, "D", "A");
                                    choiceAIsActive = true;
                                    choiceBIsActive = false;
                                    choiceCIsActive = false;
                                    choiceDIsActive = true;
                                } else if (!remove.contains('B')) { //remove A, C
                                    playScreenButton.renderButton(batch, "D", "B");
                                    choiceAIsActive = false;
                                    choiceBIsActive = true;
                                    choiceCIsActive = false;
                                    choiceDIsActive = true;
                                } else if (!remove.contains('C')) { //remove A, B
                                    playScreenButton.renderButton(batch, "D", "C");
                                    choiceAIsActive = false;
                                    choiceBIsActive = false;
                                    choiceCIsActive = true;
                                    choiceDIsActive = true;
                                }
                                break;
                        }
                    } else { //new round
                        sshIntoASuperComputerCtr--; //break out of the condition
                        choiceAIsActive = true;
                        choiceBIsActive = true;
                        choiceCIsActive = true;
                        choiceDIsActive = true;
                    }

                }

                if (ddemProbabilityAlgorithmCtr == 0) { //Case (3) DDEM Probability Algorithm
                    if (ddemProbabilityAlgorithmCurrentQuestion == questionsCounter) {
                        Gdx.app.log(TAG, "DDEM PROBABILITY ALGORITHM");
                        /*
                           Step 1: Generate four random numbers between 0 and 1
                           Step 2: Add these four numbers
                           Step 3: Divide each of the four numbers by the sum,
                           Step 4: Multiply by 100, and round to the nearest integer.
                           https://stackoverflow.com/questions/31229201/4-random-numbers-totaling-100
                        */


                        if (ddemStartTime == 0) {
                            //generate random values from 0 to 1
                            for (int i = 0; i < 4; i++) {
                                randomDouble[i] = Math.random();
                            }

                            //get the sum of the values
                            for (double x : randomDouble) {
                                sum += x;
                            }

                            //store the values
                            for (int i = 0; i < randomDouble.length; i++) {
                                randomDouble[i] /= sum;
                                randomDouble[i] *= 100;
                                randomDouble[i] = Math.round(randomDouble[i]);
                                dummyDoubles.add(randomDouble[i]);
                            }

                            //If you get lucky, then the correct answer will be displayed as the highest percentage.
                            //Else, randomDouble dummy values will be displayed
                            if (rand.nextInt(100) < 25) {  //25% chance of showing the correct answer. The highest percentage is the correct answer.
                                lucky = true;

                                /*
                                   Generate random values to display, the highest percentage is the correct answer.
                                   In the case that two of the highest values are equal, then the player is unlucky and has to guess.
                                   */

                                //get the max value
                                for (int i = 0; i < randomDouble.length; i++) {
                                    if (percentageMax < randomDouble[i]) {
                                        percentageMax = randomDouble[i];
                                        percentageMaxIndex = i; //largest double is stored in index i
                                    }
                                }

                                //update the dummyDoubles list
                                dummyDoubles.clear();

                                for (int i = 0; i < randomDouble.length; i++) {
                                    if (i != percentageMaxIndex) {
                                        dummyDoubles.add(randomDouble[i]);
                                    }
                                }
                            }
                            ddemStartTime = TimeUtils.nanoTime();
                        }

                        String correctChoice = questions.getCorrectChoice();

                        //initialize dummy values

                        if (lucky) {
                            switch (correctChoice) {
                                case "A":
                                    valueA = percentageMax;
                                    valueB = dummyDoubles.get(0);
                                    valueC = dummyDoubles.get(1);
                                    valueD = dummyDoubles.get(2);
                                    break;
                                case "B":
                                    valueA = dummyDoubles.get(0);
                                    valueB = percentageMax;
                                    valueC = dummyDoubles.get(1);
                                    valueD = dummyDoubles.get(2);
                                    break;
                                case "C":
                                    valueA = dummyDoubles.get(0);
                                    valueB = dummyDoubles.get(1);
                                    valueC = percentageMax;
                                    valueD = dummyDoubles.get(2);
                                    break;
                                case "D":
                                    valueA = dummyDoubles.get(0);
                                    valueB = dummyDoubles.get(1);
                                    valueC = dummyDoubles.get(2);
                                    valueD = percentageMax;
                                    break;
                            }
                        } else {
                            valueA = dummyDoubles.get(0);
                            valueB = dummyDoubles.get(1);
                            valueC = dummyDoubles.get(2);
                            valueD = dummyDoubles.get(3);
                        }

                        switch (correctChoice) {
                            case "A":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('B')) { //remove C, D AND display B, A
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "B", "CorrectA");
                                        } else {
                                            playScreenButton.renderButton(batch, "B", "A");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");

                                    } else if (!remove.contains('C')) { //remove B, D AND display C, A
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "C", "CorrectA");
                                        } else {
                                            playScreenButton.renderButton(batch, "C", "A");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");

                                    } else if (!remove.contains('D')) { //remove B, C AND display D, A
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "D", "CorrectA");
                                        } else {
                                            playScreenButton.renderButton(batch, "D", "A");
                                        }
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                    }
                                } else {
                                    if (callAnotherHackerCtr == 0) {
                                        playScreenButton.renderButton(batch, "CorrectA", "B", "C", "D");
                                    } else {
                                        playScreenButton.renderButton(batch, "A", "B", "C", "D");
                                    }
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                }
                                break;
                            case "B":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('A')) { //remove C, D AND display A, B
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "A", "CorrectB");
                                        } else {
                                            playScreenButton.renderButton(batch, "A", "B");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");

                                    } else if (!remove.contains('C')) { //remove A, D AND display C, B
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "C", "CorrectB");
                                        } else {
                                            playScreenButton.renderButton(batch, "C", "B");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");

                                    } else if (!remove.contains('D')) { //remove A, C AND display D, B
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "D", "CorrectB");
                                        } else {
                                            playScreenButton.renderButton(batch, "D", "B");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");

                                    }
                                } else {
                                    if (callAnotherHackerCtr == 0) {
                                        playScreenButton.renderButton(batch, "CorrectB", "A", "C", "D");
                                    } else {
                                        playScreenButton.renderButton(batch, "B", "A", "C", "D");
                                    }

                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                }
                                break;
                            case "C":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('A')) { //remove B, D AND display A, C
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "A", "CorrectC");
                                        } else {
                                            playScreenButton.renderButton(batch, "A", "C");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");

                                    } else if (!remove.contains('B')) { //remove A, D AND display B, C
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "B", "CorrectC");
                                        } else {
                                            playScreenButton.renderButton(batch, "B", "C");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");

                                    } else if (!remove.contains('D')) { //remove A, B AND display D, C
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "D", "CorrectC");
                                        } else {
                                            playScreenButton.renderButton(batch, "D", "C");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                    }
                                } else {
                                    if (callAnotherHackerCtr == 0) {
                                        playScreenButton.renderButton(batch, "CorrectC", "A", "B", "D");
                                    } else {
                                        playScreenButton.renderButton(batch, "C", "A", "B", "D");
                                    }

                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                }
                                break;
                            case "D":
                                if (sshIntoASuperComputerCtr == 0) {
                                    if (!remove.contains('A')) { //remove B, C AND display A, D
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "A", "CorrectD");
                                        } else {
                                            playScreenButton.renderButton(batch, "A", "D");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");

                                    } else if (!remove.contains('B')) { //remove A, C AND display B, D
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "B", "CorrectD");
                                        } else {
                                            playScreenButton.renderButton(batch, "B", "D");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");

                                    } else if (!remove.contains('C')) { //remove A, B AND display C, D
                                        if (callAnotherHackerCtr == 0) {
                                            playScreenButton.renderButton(batch, "C", "CorrectD");
                                        } else {
                                            playScreenButton.renderButton(batch, "C", "D");
                                        }

                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                        Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");

                                    }
                                } else {
                                    if (callAnotherHackerCtr == 0) {
                                        playScreenButton.renderButton(batch, "CorrectD", "A", "B", "C");
                                    } else {
                                        playScreenButton.renderButton(batch, "D", "A", "B", "C");
                                    }

                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageA", valueA + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageC", valueC + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageB", valueB + "%");
                                    Assets.instance.font.drawGlitchEsportsFont(batch, "percentageD", valueD + "%");
                                }
                                break;
                        }

                    } else { //new round
                        ddemProbabilityAlgorithmCtr = -1; //break out of the condition
                    }
                }

            } else {
                //Default
                playScreenButton.renderButton(batch, "ALL");
            }

            //callAnotherHackerButton attributes
            Vector2 callAnotherHackerButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 18, viewport.getCamera().viewportHeight - CALL_ANOTHER_HACKER_HEIGHT - 50);
            Rectangle callAnotherHackerButtonBoundingBox = new Rectangle(callAnotherHackerButtonCenter.x - Constants.CALL_ANOTHER_HACKER_WIDTH / 2, callAnotherHackerButtonCenter.y - CALL_ANOTHER_HACKER_HEIGHT / 2, CALL_ANOTHER_HACKER_WIDTH, CALL_ANOTHER_HACKER_HEIGHT);

            //sshIntoASuperComputerButton attributes
            Vector2 sshIntoASuperComputerButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 8, viewport.getCamera().viewportHeight - SSH_INTO_A_SUPERCOMPUTER_HEIGHT - 50);
            Rectangle sshIntoASuperComputerButtonBoundingBox = new Rectangle(sshIntoASuperComputerButtonCenter.x - Constants.SSH_INTO_A_SUPERCOMPUTER_WIDTH / 2, sshIntoASuperComputerButtonCenter.y - SSH_INTO_A_SUPERCOMPUTER_HEIGHT / 2, SSH_INTO_A_SUPERCOMPUTER_WIDTH, SSH_INTO_A_SUPERCOMPUTER_HEIGHT);

            //ddemProbabilityAlgorithmButton attributes
            Vector2 ddemProbabilityAlgorithmButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 5, viewport.getCamera().viewportHeight - DDEM_PROBABILITY_ALGORITHM_HEIGHT - 50);
            Rectangle ddemProbabilityAlgorithmButtonBoundingBox = new Rectangle(ddemProbabilityAlgorithmButtonCenter.x - DDEM_PROBABILITY_ALGORITHM_WIDTH / 2, ddemProbabilityAlgorithmButtonCenter.y - DDEM_PROBABILITY_ALGORITHM_HEIGHT / 2, DDEM_PROBABILITY_ALGORITHM_WIDTH, DDEM_PROBABILITY_ALGORITHM_HEIGHT);

            Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (callAnotherHackerButtonBoundingBox.contains(mousePosition)) {
                if (callAnotherHackerCtr == 1) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "actionBlue","CALL ANOTHER HACKER");
                } else if (callAnotherHackerCtr < 1) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "actionRed","CALL ANOTHER HACKER");
                }
            }

            if (sshIntoASuperComputerButtonBoundingBox.contains(mousePosition)) {
                if (sshIntoASuperComputerCtr == 1) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "actionBlue","SSH INTO A SUPERCOMPUTER");
                } else if (sshIntoASuperComputerCtr < 1) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "actionRed","SSH INTO A SUPERCOMPUTER");
                }
            }

            if (ddemProbabilityAlgorithmButtonBoundingBox.contains(mousePosition)) {
                if (ddemProbabilityAlgorithmCtr == 1) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "actionBlue","DDEM PROBABILITY ALGORITHM");
                } else if (ddemProbabilityAlgorithmCtr < 1) {
                    Assets.instance.font.drawGlitchEsportsFont(batch, "actionRed","DDEM PROBABILITY ALGORITHM");
                }
            }

        } else {
            //Default
            playScreenButton.renderButton(batch, "ALL");
        }


        if (callAnotherHackerCtr == 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.callAnotherHacker, new Vector2(viewport.getCamera().viewportWidth / 18, viewport.getCamera().viewportHeight - CALL_ANOTHER_HACKER_HEIGHT - 50), CALL_ANOTHER_HACKER_CENTER);
        } else if (callAnotherHackerCtr < 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.callAnotherHackerDisabled, new Vector2(viewport.getCamera().viewportWidth / 18, viewport.getCamera().viewportHeight - CALL_ANOTHER_HACKER_HEIGHT - 50), CALL_ANOTHER_HACKER_CENTER);
        }

        if (sshIntoASuperComputerCtr == 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.sshIntoASuperComputer, new Vector2(viewport.getCamera().viewportWidth / 8, viewport.getCamera().viewportHeight - SSH_INTO_A_SUPERCOMPUTER_HEIGHT - 50), SSH_INTO_A_SUPERCOMPUTER_CENTER);
        } else if (sshIntoASuperComputerCtr < 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.sshIntoASuperComputerDisabled, new Vector2(viewport.getCamera().viewportWidth / 8, viewport.getCamera().viewportHeight - SSH_INTO_A_SUPERCOMPUTER_HEIGHT - 50), SSH_INTO_A_SUPERCOMPUTER_CENTER);
        }

        if (ddemProbabilityAlgorithmCtr == 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.ddemProbabilityAlgorithm, new Vector2(viewport.getCamera().viewportWidth / 5, viewport.getCamera().viewportHeight - DDEM_PROBABILITY_ALGORITHM_HEIGHT - 50), DDEM_PROBABILITY_ALGORITHM_CENTER);
        } else if (ddemProbabilityAlgorithmCtr < 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.ddemProbabilityAlgorithmDisabled, new Vector2(viewport.getCamera().viewportWidth / 5, viewport.getCamera().viewportHeight - DDEM_PROBABILITY_ALGORITHM_HEIGHT - 50), DDEM_PROBABILITY_ALGORITHM_CENTER);
        }


        if (chances == 5) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.lifeAnimation1, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.40f), LIFE_ANIMATION_CENTER);
        } else if (chances == 4) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.lifeAnimation2, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.40f), LIFE_ANIMATION_CENTER);
        } else if (chances == 3) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.lifeAnimation3, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.40f), LIFE_ANIMATION_CENTER);
        } else if (chances == 2) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.lifeAnimation4, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.40f), LIFE_ANIMATION_CENTER);
        } else if (chances == 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.lifeAnimation5, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.40f), LIFE_ANIMATION_CENTER);
        } else if (chances == 0) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.lifeAnimation6, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.40f), LIFE_ANIMATION_CENTER);
        }

        if (progressCounter == 1) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar1, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 2) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar2, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 3) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar3, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 4) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar4, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 5) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar5, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 6) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar6, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 7) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar7, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 8) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar8, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 9) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar9, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 10) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar10, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 11) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar11, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 12) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar12, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 13) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar13, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 14) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar14, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 15) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar15, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 16) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar16, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 17) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar17, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 18) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar18, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 19) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar19, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 20) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar20, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 21) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar21, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 22) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar22, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 23) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar23, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 24) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar24, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 25) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar25, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 26) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar26, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 27) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar27, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 28) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar28, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 29) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar29, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else if (progressCounter == 30) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar30, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        } else {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.progressBar30, new Vector2((viewport.getCamera().viewportWidth - PROGRESS_BAR_WIDTH), viewport.getCamera().viewportHeight / 1.16f));
        }

        if (roundElapsedTime == 0) {
            roundElapsedTime = TimeUtils.nanoTime();
        }

        if (chances >= 0 && progressCounter <= 30) {
            Assets.instance.font.drawDSDigibFont(batch, "elapsedTime",String.valueOf(Util.secondsSince(roundElapsedTime)));

        }

    }


    public void renderScore(SpriteBatch batch) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        Assets.instance.font.drawDSDigibFont(batch, "score30", String.valueOf(df.format(score)));
        Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.bitcoinLogo, new Vector2((viewport.getCamera().viewportWidth - BITCOIN_LOGO_WIDTH) / 1.4f, viewport.getCamera().viewportHeight - 65));

        if (chances < 0 || progressCounter > 30) {
            Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.bitcoinLogo, new Vector2((viewport.getCamera().viewportWidth - BITCOIN_LOGO_WIDTH) / 1.4f, viewport.getCamera().viewportHeight - 65));
            Assets.instance.font.drawDSDigibFont(batch, "score30", String.valueOf(df.format(score)));
            Assets.instance.font.drawDSDigibFont(batch, "score100", String.valueOf(df.format(score)));
            Assets.instance.font.drawGlitchEsportsFont(batch, "bitcoins", "TOTAL BITCOIN EARNINGS:");

            if(storeScoreStartTime == 0){
                //store the scores
                while(true){
                    if(!Constants.preferences.contains("user-"+userCounter)){
                        Constants.preferences.putString("user-"+userCounter, Constants.preferences.getString("user"));
                        Constants.preferences.putFloat("score-"+userCounter, Float.parseFloat(df.format(score)));
                        break;
                    } else{
                        userCounter++;
                    }
                }
                Constants.preferences.putInteger("userCounter", userCounter);
                Constants.preferences.flush();
                storeScoreStartTime = TimeUtils.nanoTime();
            }


        }
    }

    public void resetRoundElapsedTime() {
        this.roundElapsedTime = 0;
    }

    /*
   private void initTextBounds() { 
       questionCenter = new Vector2(this.viewport.getCamera().viewportWidth / 2, this.viewport.getCamera().viewportHeight / 1.7f);
       questionRectangleBounds = new Rectangle(questionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, questionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

       instructionCenter = new Vector2(this.viewport.getCamera().viewportWidth / 2, this.viewport.getCamera().viewportHeight / 1.6f);
       instructionRectangleBounds = new Rectangle(instructionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, instructionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

       //choiceA
       choiceAButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 20);
       choiceAButtonBoundingBox = new Rectangle(choiceAButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceAButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

      //choiceB 
       choiceBButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 20);
       choiceBButtonBoundingBox = new Rectangle(choiceBButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceBButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

      //choiceC 
       choiceCButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 145);
       choiceCButtonBoundingBox = new Rectangle(choiceCButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceCButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

      //choiceD 
       choiceDButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 145);
       choiceDButtonBoundingBox = new Rectangle(choiceDButtonCenter.x - Constants.PLAY_SCREEN_BUTTON_WIDTH / 2, choiceDButtonCenter.y - Constants.PLAY_SCREEN_BUTTON_HEIGHT / 2, Constants.PLAY_SCREEN_BUTTON_WIDTH, Constants.PLAY_SCREEN_BUTTON_HEIGHT);

       //topic
       //topicCenter = new Vector2(viewport.getCamera().viewportWidth / 4f, viewport.getCamera().viewportHeight / 1.20f);
       //topicRectangleBounds = new Rectangle(topicCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, topicCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);
   }
*/


}

