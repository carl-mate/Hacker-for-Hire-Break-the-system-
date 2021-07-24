package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.io.InputStream;
import java.util.HashMap;


public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();
    public MainMenuAssets mainMenuAssets;
    public DifficultyScreenAssets difficultyScreenAssets;
    public PlayScreenAssets playScreenAssets;
    public MusicClass musicClass;
    public SoundClass soundClass;
    public ResourcesFilePath resourcesFilePath;
    public Font font;

    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager){
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        mainMenuAssets = new MainMenuAssets(atlas);
        difficultyScreenAssets = new DifficultyScreenAssets(atlas);
        playScreenAssets = new PlayScreenAssets(atlas);
        musicClass = new MusicClass();
        soundClass = new SoundClass();
        font = new Font();
    }

    public void initResourcesFilePath(){
        resourcesFilePath = new ResourcesFilePath();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class Font {
        private ExtendViewport viewport;
        public FreeTypeFontGenerator glitchEsportsFontGenerator;
        public FreeTypeFontGenerator dsDigibFontGenerator;

        public final FreeTypeFontGenerator.FreeTypeFontParameter instructionsFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter actionBlueFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter actionRedFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter questionFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter choicesFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter bitcoinsFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter percentageFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter score30FontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter score100FontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter elapsedTimeFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter loadingProgressFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter usernameFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter noticeFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter highScoreFontParameter;

        public BitmapFont instructionsFont;
        public BitmapFont actionBlueFont;
        public BitmapFont actionRedFont;
        public BitmapFont questionFont;
        public BitmapFont choicesFont;
        public BitmapFont bitcoinsFont;
        public BitmapFont percentageFont;
        public BitmapFont score30Font;
        public BitmapFont score100Font;
        public BitmapFont elapsedTimeFont;
        public BitmapFont loadingProgressFont;
        public BitmapFont usernameFont;
        public BitmapFont noticeFont;
        public BitmapFont highScoreFont;


        public GlyphLayout glyphLayout;

        public Font() {
            glitchEsportsFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("glitch_esports.ttf"));
            dsDigibFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("DS-DIGIB.TTF"));

            //font parameters
            highScoreFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            highScoreFontParameter.size = 45;
            highScoreFontParameter.color = Color.valueOf("13e3d0");
            highScoreFont = glitchEsportsFontGenerator.generateFont(highScoreFontParameter);

            instructionsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            instructionsFontParameter.size = 18;
            instructionsFontParameter.color = Color.valueOf("13e3d0");
            instructionsFont = glitchEsportsFontGenerator.generateFont(instructionsFontParameter);

            usernameFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            usernameFontParameter.size = 50;
            usernameFontParameter.color = Color.valueOf("11d5a6");
            usernameFont = glitchEsportsFontGenerator.generateFont(usernameFontParameter);

            noticeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            noticeFontParameter.size = 25;
            noticeFontParameter.color = Color.valueOf("ee2c5a");
            noticeFont = glitchEsportsFontGenerator.generateFont(noticeFontParameter);

            actionBlueFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            actionBlueFontParameter.size = 18;
            actionBlueFontParameter.color = Color.valueOf("13e3d0");
            actionBlueFont = glitchEsportsFontGenerator.generateFont(actionBlueFontParameter);

            actionRedFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            actionRedFontParameter.size = 18;
            actionRedFontParameter.color = Color.valueOf("ee2c5a");
            actionRedFont = glitchEsportsFontGenerator.generateFont(actionRedFontParameter);

            questionFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            questionFontParameter.size = 25;
            questionFontParameter.color = Color.valueOf("13e3d0");
            questionFont = glitchEsportsFontGenerator.generateFont(questionFontParameter);

            choicesFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            choicesFontParameter.size = 16;
            choicesFontParameter.color = Color.valueOf("13e3d0");
            choicesFont = glitchEsportsFontGenerator.generateFont(choicesFontParameter);

            bitcoinsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            bitcoinsFontParameter.size = 40;
            bitcoinsFontParameter.color = Color.valueOf("13e3d0");
            bitcoinsFont = glitchEsportsFontGenerator.generateFont(bitcoinsFontParameter);

            percentageFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            percentageFontParameter.size = 25;
            percentageFontParameter.color = Color.valueOf("13e3d0");
            percentageFont = glitchEsportsFontGenerator.generateFont(percentageFontParameter);

            score30FontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            score30FontParameter.size = 30;
            score30FontParameter.color = Color.valueOf("13e3d0");
            score30Font = dsDigibFontGenerator.generateFont(score30FontParameter);

            score100FontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            score100FontParameter.size = 100;
            score100FontParameter.color = Color.valueOf("13e3d0");
            score100Font = dsDigibFontGenerator.generateFont(score100FontParameter);

            elapsedTimeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            elapsedTimeFontParameter.size = 100;
            elapsedTimeFontParameter.color = Color.valueOf("13e3d0");
            elapsedTimeFont = dsDigibFontGenerator.generateFont(elapsedTimeFontParameter);

            loadingProgressFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            loadingProgressFontParameter.size = 100;
            loadingProgressFontParameter.color = Color.valueOf("13e3d0");
            loadingProgressFont = dsDigibFontGenerator.generateFont(loadingProgressFontParameter);

            glyphLayout = new GlyphLayout();

            Gdx.app.log(TAG, "FONT CONSTRUCTOR RUNS");

        }

        public void drawGlitchEsportsFont(SpriteBatch batch, String type, String text) {

            switch (type) {
                case "highScoreFontOne":
                    glyphLayout.setText(highScoreFont, text);
                    highScoreFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 1.28f);
                    break;
                case "highScoreFontTwo":
                    glyphLayout.setText(highScoreFont, text);
                    highScoreFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 1.48f);
                    break;
                case "highScoreFontThree":
                    glyphLayout.setText(highScoreFont, text);
                    highScoreFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 1.72f);
                    break;
                case "highScoreFontFour":
                    glyphLayout.setText(highScoreFont, text);
                    highScoreFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2.10f);
                    break;
                case "highScoreFontFive":
                    glyphLayout.setText(highScoreFont, text);
                    highScoreFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2.70f);
                    break;
                case "usernameMenuScreen":
                    glyphLayout.setText(usernameFont, text);
                    usernameFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2.2f);
                    break;
                case "username":
                    glyphLayout.setText(usernameFont, text);
                    usernameFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2f);
                    break;
                case "notice":
                    glyphLayout.setText(noticeFont, text);
                    noticeFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2.5f);
                    break;
                case "instructions":
                    glyphLayout.setText(instructionsFont, text);
                    instructionsFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2.5f);
                    break;
                case "actionBlue":
                    glyphLayout.setText(actionBlueFont, text);
                    actionBlueFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 14, (viewport.getCamera().viewportHeight - glyphLayout.height) - 120);
                    break;
                case "actionRed":
                    glyphLayout.setText(actionRedFont, text);
                    actionRedFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 14, (viewport.getCamera().viewportHeight - glyphLayout.height) - 120);
                    break;
                case "question":
                    glyphLayout.setText(questionFont, text);
                    questionFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight + glyphLayout.height) / 2);
                    break;
                case "choiceA":
                    glyphLayout.setText(choicesFont, text);
                    choicesFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 5.5f, (viewport.getCamera().viewportHeight + glyphLayout.height + 20) / 4 - 20);
                    break;
                case "choiceB":
                    glyphLayout.setText(choicesFont, text);
                    choicesFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 1.25f, (viewport.getCamera().viewportHeight + glyphLayout.height + 20) / 4 - 20);
                    break;
                case "choiceC":
                    glyphLayout.setText(choicesFont, text);
                    choicesFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 5.5f, (viewport.getCamera().viewportHeight + glyphLayout.height + 20) / 4 - 145);
                    break;
                case "choiceD":
                    glyphLayout.setText(choicesFont, text);
                    choicesFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 1.25f, (viewport.getCamera().viewportHeight + glyphLayout.height + 20) / 4 - 145);
                    break;
                case "bitcoins":
                    glyphLayout.setText(bitcoinsFont, text);
                    bitcoinsFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 1.9f);
                    break;
                case "percentageA":
                    glyphLayout.setText(percentageFont, text);
                    percentageFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 4.2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 4 - 60);
                    break;
                case "percentageB":
                    glyphLayout.setText(percentageFont, text);
                    percentageFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 1.30f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 4 - 60);
                    break;
                case "percentageC":
                    glyphLayout.setText(percentageFont, text);
                    percentageFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 4.2f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 4 - 185);
                    break;
                case "percentageD":
                    glyphLayout.setText(percentageFont, text);
                    percentageFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 1.30f, (viewport.getCamera().viewportHeight - glyphLayout.height) / 4 - 185);
                    break;

            }


        }

        public void drawDSDigibFont(SpriteBatch batch, String type, String text) {

            switch (type) {
                case "loadingProgress":
                    glyphLayout.setText(loadingProgressFont, text);
                    loadingProgressFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2);
                    break;
                case "score30":
                    glyphLayout.setText(score30Font, text);
                    score30Font.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 1.10f, (viewport.getCamera().viewportHeight - glyphLayout.height) - 5);
                    break;
                case "score100":
                    glyphLayout.setText(score100Font, text);
                    score100Font.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight - glyphLayout.height) / 2.1f);
                    break;
                case "elapsedTime":
                    glyphLayout.setText(elapsedTimeFont, text);
                    elapsedTimeFont.draw(batch, glyphLayout, (viewport.getCamera().viewportWidth - glyphLayout.width) / 2, (viewport.getCamera().viewportHeight + glyphLayout.height) - 120);
                    break;
            }

        }

        public void setViewport(ExtendViewport viewport) {
            this.viewport = viewport;
        }

    }

    public class ResourcesFilePath {
        public InputStream inputStream;
        public final HashMap<String, Texture> image;

        public ResourcesFilePath(){
            inputStream = this.getClass().getResourceAsStream("/Resources/Database.xlsx");

            image = new HashMap<>();

            image.put("GT4.png", new Texture(Gdx.files.internal("Resources/Images/GT4.png")));
            image.put("GT5.png", new Texture(Gdx.files.internal("Resources/Images/GT5.png")));
            image.put("GT6.png", new Texture(Gdx.files.internal("Resources/Images/GT6.png")));
            image.put("GT9.png", new Texture(Gdx.files.internal("Resources/Images/GT9.png")));
        }
    }

    public class SoundClass {
        public final Sound mouseHoverSound;
        public final Sound correctSFX;
        public final Sound wrongSFX;

        public SoundClass() {
            mouseHoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/mouse-hover-button.wav"));
            correctSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/correct-sfx.wav"));
            wrongSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/wrong-sfx.wav"));

        }
    }

    public class MusicClass {
        public final Music mainMenuMusic;
        public final Music playScreenMusic;
        public final Music gameOverSound;
        public final Music victorySound;

        public MusicClass() {
            mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/main-menu-music.wav"));
            playScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/play-screen-music.wav"));
            gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/game-over-sound-effect.mp3"));
            victorySound = Gdx.audio.newMusic(Gdx.files.internal("sounds/victory-sound-effect.mp3"));
        }
    }

    public class MainMenuAssets {

        public final TextureAtlas.AtlasRegion enterCodename;
        public final TextureAtlas.AtlasRegion mainMenuBG;
        public final TextureAtlas.AtlasRegion playButtonActive;
        public final TextureAtlas.AtlasRegion playButtonInactive;
        public final TextureAtlas.AtlasRegion optionsButtonActive;
        public final TextureAtlas.AtlasRegion optionsButtonInactive;
        public final TextureAtlas.AtlasRegion helpButtonActive;
        public final TextureAtlas.AtlasRegion helpButtonInactive;

        public final TextureAtlas.AtlasRegion instructions1;
        public final TextureAtlas.AtlasRegion instructions2;
        public final TextureAtlas.AtlasRegion instructions3;

        public final TextureAtlas.AtlasRegion actions1;
        public final TextureAtlas.AtlasRegion actions2;
        public final TextureAtlas.AtlasRegion actions3;

        public final TextureAtlas.AtlasRegion binaryBackground;

        public final TextureAtlas.AtlasRegion musicButtonOn;
        public final TextureAtlas.AtlasRegion musicButtonOff;

        public final TextureAtlas.AtlasRegion onOff;

        public MainMenuAssets(TextureAtlas atlas) {
            enterCodename = atlas.findRegion(Constants.ENTER_CODENAME);
            mainMenuBG = atlas.findRegion(Constants.MAIN_MENU_BG);
            playButtonActive = atlas.findRegion(Constants.PLAYBUTTON_ACTIVE);
            playButtonInactive = atlas.findRegion(Constants.PLAYBUTTON_INACTIVE);
            optionsButtonActive = atlas.findRegion(Constants.OPTIONBUTTON_ACTIVE);
            optionsButtonInactive = atlas.findRegion(Constants.OPTIONBUTTON_INACTIVE);
            helpButtonActive = atlas.findRegion(Constants.HELPBUTTON_ACTIVE);
            helpButtonInactive = atlas.findRegion(Constants.HELPBUTTON_INACTIVE);

            instructions1 = atlas.findRegion(Constants.INSTRUCTIONS_1);
            instructions2 = atlas.findRegion(Constants.INSTRUCTIONS_2);
            instructions3 = atlas.findRegion(Constants.INSTRUCTIONS_3);

            actions1 = atlas.findRegion(Constants.ACTIONS_1);
            actions2 = atlas.findRegion(Constants.ACTIONS_2);
            actions3 = atlas.findRegion(Constants.ACTIONS_3);

            binaryBackground = atlas.findRegion(Constants.BINARY_BACKGROUND);

            musicButtonOn = atlas.findRegion(Constants.MUSIC_ON);
            musicButtonOff = atlas.findRegion(Constants.MUSIC_OFF);

            onOff = atlas.findRegion(Constants.ON_OFF);
        }
    }

    public class PlayScreenAssets {
        public final TextureAtlas.AtlasRegion playScreenBG;
        public final TextureAtlas.AtlasRegion playScreenButton;
        public final TextureAtlas.AtlasRegion playScreenButtonCorrect;
        public final TextureAtlas.AtlasRegion playScreenMenuButton;
        public final TextureAtlas.AtlasRegion playScreenImageFrame;


        public final TextureAtlas.AtlasRegion lifeAnimation1;
        public final TextureAtlas.AtlasRegion lifeAnimation2;
        public final TextureAtlas.AtlasRegion lifeAnimation3;
        public final TextureAtlas.AtlasRegion lifeAnimation4;
        public final TextureAtlas.AtlasRegion lifeAnimation5;
        public final TextureAtlas.AtlasRegion lifeAnimation6;
        public final TextureAtlas.AtlasRegion lifeAnimation7;

        public final TextureAtlas.AtlasRegion progressBar1;
        public final TextureAtlas.AtlasRegion progressBar2;
        public final TextureAtlas.AtlasRegion progressBar3;
        public final TextureAtlas.AtlasRegion progressBar4;
        public final TextureAtlas.AtlasRegion progressBar5;
        public final TextureAtlas.AtlasRegion progressBar6;
        public final TextureAtlas.AtlasRegion progressBar7;
        public final TextureAtlas.AtlasRegion progressBar8;
        public final TextureAtlas.AtlasRegion progressBar9;
        public final TextureAtlas.AtlasRegion progressBar10;
        public final TextureAtlas.AtlasRegion progressBar11;
        public final TextureAtlas.AtlasRegion progressBar12;
        public final TextureAtlas.AtlasRegion progressBar13;
        public final TextureAtlas.AtlasRegion progressBar14;
        public final TextureAtlas.AtlasRegion progressBar15;
        public final TextureAtlas.AtlasRegion progressBar16;
        public final TextureAtlas.AtlasRegion progressBar17;
        public final TextureAtlas.AtlasRegion progressBar18;
        public final TextureAtlas.AtlasRegion progressBar19;
        public final TextureAtlas.AtlasRegion progressBar20;
        public final TextureAtlas.AtlasRegion progressBar21;
        public final TextureAtlas.AtlasRegion progressBar22;
        public final TextureAtlas.AtlasRegion progressBar23;
        public final TextureAtlas.AtlasRegion progressBar24;
        public final TextureAtlas.AtlasRegion progressBar25;
        public final TextureAtlas.AtlasRegion progressBar26;
        public final TextureAtlas.AtlasRegion progressBar27;
        public final TextureAtlas.AtlasRegion progressBar28;
        public final TextureAtlas.AtlasRegion progressBar29;
        public final TextureAtlas.AtlasRegion progressBar30;

        public final TextureAtlas.AtlasRegion correctIndicator1;
        public final TextureAtlas.AtlasRegion correctIndicator2;
        public final TextureAtlas.AtlasRegion correctIndicator3;
        public final TextureAtlas.AtlasRegion correctIndicator4;
        public final TextureAtlas.AtlasRegion correctIndicator5;
        public final TextureAtlas.AtlasRegion correctIndicator6;
        public final TextureAtlas.AtlasRegion correctIndicator7;
        public final TextureAtlas.AtlasRegion correctIndicator8;
        public final TextureAtlas.AtlasRegion correctIndicator9;

        public final TextureAtlas.AtlasRegion wrongIndicator1;
        public final TextureAtlas.AtlasRegion wrongIndicator2;
        public final TextureAtlas.AtlasRegion wrongIndicator3;
        public final TextureAtlas.AtlasRegion wrongIndicator4;
        public final TextureAtlas.AtlasRegion wrongIndicator5;
        public final TextureAtlas.AtlasRegion wrongIndicator6;
        public final TextureAtlas.AtlasRegion wrongIndicator7;
        public final TextureAtlas.AtlasRegion wrongIndicator8;
        public final TextureAtlas.AtlasRegion wrongIndicator9;

        public final TextureAtlas.AtlasRegion gameOver;
        public final TextureAtlas.AtlasRegion youWin;
        public final TextureAtlas.AtlasRegion tryAgainActive;
        public final TextureAtlas.AtlasRegion tryAgainInactive;
        public final TextureAtlas.AtlasRegion returnToMenuActive;
        public final TextureAtlas.AtlasRegion returnToMenuInactive;
        public final TextureAtlas.AtlasRegion highScoresActive;
        public final TextureAtlas.AtlasRegion highScoresInactive;
        public final TextureAtlas.AtlasRegion highScoresBG;


        public final Animation correctIndicatorAnimation;
        public final Animation wrongIndicatorAnimation;

        public final TextureAtlas.AtlasRegion bitcoinLogo;

        public final TextureAtlas.AtlasRegion callAnotherHacker;
        public final TextureAtlas.AtlasRegion sshIntoASuperComputer;
        public final TextureAtlas.AtlasRegion ddemProbabilityAlgorithm;

        public final TextureAtlas.AtlasRegion callAnotherHackerDisabled;
        public final TextureAtlas.AtlasRegion sshIntoASuperComputerDisabled;
        public final TextureAtlas.AtlasRegion ddemProbabilityAlgorithmDisabled;

        public PlayScreenAssets(TextureAtlas atlas) {
            playScreenBG = atlas.findRegion(Constants.PLAY_SCREEN_BG);
            playScreenButton = atlas.findRegion(Constants.PLAY_SCREEN_BUTTON);
            playScreenButtonCorrect = atlas.findRegion(Constants.PLAY_SCREEN_BUTTON_CORRECT);
            playScreenMenuButton = atlas.findRegion(Constants.PLAY_SCREEN_MENU_BUTTON);
            playScreenImageFrame = atlas.findRegion(Constants.PLAY_SCREEN_IMAGE_FRAME);

            gameOver = atlas.findRegion(Constants.GAME_OVER);
            youWin = atlas.findRegion(Constants.YOU_WIN);
            tryAgainActive = atlas.findRegion(Constants.TRY_AGAIN_ACTIVE);
            tryAgainInactive = atlas.findRegion(Constants.TRY_AGAIN_INACTIVE);
            returnToMenuActive = atlas.findRegion(Constants.RETURN_TO_MENU_ACTIVE);
            returnToMenuInactive = atlas.findRegion(Constants.RETURN_TO_MENU_INACTIVE);

            highScoresActive = atlas.findRegion(Constants.HIGHSCORES_ACTIVE);
            highScoresInactive = atlas.findRegion(Constants.HIGHSCORES_INACTIVE);

            highScoresBG = atlas.findRegion(Constants.HIGHSCORES_BG);

            lifeAnimation1 = atlas.findRegion(Constants.LIFE_ANIMATION_1);
            lifeAnimation2 = atlas.findRegion(Constants.LIFE_ANIMATION_2);
            lifeAnimation3 = atlas.findRegion(Constants.LIFE_ANIMATION_3);
            lifeAnimation4 = atlas.findRegion(Constants.LIFE_ANIMATION_4);
            lifeAnimation5 = atlas.findRegion(Constants.LIFE_ANIMATION_5);
            lifeAnimation6 = atlas.findRegion(Constants.LIFE_ANIMATION_6);
            lifeAnimation7 = atlas.findRegion(Constants.LIFE_ANIMATION_7);

            progressBar1 = atlas.findRegion(Constants.PROGRESS_BAR_1);
            progressBar2 = atlas.findRegion(Constants.PROGRESS_BAR_2);
            progressBar3 = atlas.findRegion(Constants.PROGRESS_BAR_3);
            progressBar4 = atlas.findRegion(Constants.PROGRESS_BAR_4);
            progressBar5 = atlas.findRegion(Constants.PROGRESS_BAR_5);
            progressBar6 = atlas.findRegion(Constants.PROGRESS_BAR_6);
            progressBar7 = atlas.findRegion(Constants.PROGRESS_BAR_7);
            progressBar8 = atlas.findRegion(Constants.PROGRESS_BAR_8);
            progressBar9 = atlas.findRegion(Constants.PROGRESS_BAR_9);
            progressBar10 = atlas.findRegion(Constants.PROGRESS_BAR_10);
            progressBar11 = atlas.findRegion(Constants.PROGRESS_BAR_11);
            progressBar12 = atlas.findRegion(Constants.PROGRESS_BAR_12);
            progressBar13 = atlas.findRegion(Constants.PROGRESS_BAR_13);
            progressBar14 = atlas.findRegion(Constants.PROGRESS_BAR_14);
            progressBar15 = atlas.findRegion(Constants.PROGRESS_BAR_15);
            progressBar16 = atlas.findRegion(Constants.PROGRESS_BAR_16);
            progressBar17 = atlas.findRegion(Constants.PROGRESS_BAR_17);
            progressBar18 = atlas.findRegion(Constants.PROGRESS_BAR_18);
            progressBar19 = atlas.findRegion(Constants.PROGRESS_BAR_19);
            progressBar20 = atlas.findRegion(Constants.PROGRESS_BAR_20);
            progressBar21 = atlas.findRegion(Constants.PROGRESS_BAR_21);
            progressBar22 = atlas.findRegion(Constants.PROGRESS_BAR_22);
            progressBar23 = atlas.findRegion(Constants.PROGRESS_BAR_23);
            progressBar24 = atlas.findRegion(Constants.PROGRESS_BAR_24);
            progressBar25 = atlas.findRegion(Constants.PROGRESS_BAR_25);
            progressBar26 = atlas.findRegion(Constants.PROGRESS_BAR_26);
            progressBar27 = atlas.findRegion(Constants.PROGRESS_BAR_27);
            progressBar28 = atlas.findRegion(Constants.PROGRESS_BAR_28);
            progressBar29 = atlas.findRegion(Constants.PROGRESS_BAR_29);
            progressBar30 = atlas.findRegion(Constants.PROGRESS_BAR_30);

            correctIndicator1 = atlas.findRegion(Constants.CORRECT_INDICATOR_1);
            correctIndicator2 = atlas.findRegion(Constants.CORRECT_INDICATOR_2);
            correctIndicator3 = atlas.findRegion(Constants.CORRECT_INDICATOR_3);
            correctIndicator4 = atlas.findRegion(Constants.CORRECT_INDICATOR_4);
            correctIndicator5 = atlas.findRegion(Constants.CORRECT_INDICATOR_5);
            correctIndicator6 = atlas.findRegion(Constants.CORRECT_INDICATOR_6);
            correctIndicator7 = atlas.findRegion(Constants.CORRECT_INDICATOR_7);
            correctIndicator8 = atlas.findRegion(Constants.CORRECT_INDICATOR_8);
            correctIndicator9 = atlas.findRegion(Constants.CORRECT_INDICATOR_9);

            wrongIndicator1 = atlas.findRegion(Constants.WRONG_INDICATOR_1);
            wrongIndicator2 = atlas.findRegion(Constants.WRONG_INDICATOR_2);
            wrongIndicator3 = atlas.findRegion(Constants.WRONG_INDICATOR_3);
            wrongIndicator4 = atlas.findRegion(Constants.WRONG_INDICATOR_4);
            wrongIndicator5 = atlas.findRegion(Constants.WRONG_INDICATOR_5);
            wrongIndicator6 = atlas.findRegion(Constants.WRONG_INDICATOR_6);
            wrongIndicator7 = atlas.findRegion(Constants.WRONG_INDICATOR_7);
            wrongIndicator8 = atlas.findRegion(Constants.WRONG_INDICATOR_8);
            wrongIndicator9 = atlas.findRegion(Constants.WRONG_INDICATOR_9);

            Array<TextureAtlas.AtlasRegion> correctIndicatorRegions = new Array<>();
            correctIndicatorRegions.add(correctIndicator1);
            correctIndicatorRegions.add(correctIndicator2);
            correctIndicatorRegions.add(correctIndicator3);
            correctIndicatorRegions.add(correctIndicator4);
            correctIndicatorRegions.add(correctIndicator5);
            correctIndicatorRegions.add(correctIndicator6);
            correctIndicatorRegions.add(correctIndicator7);
            correctIndicatorRegions.add(correctIndicator8);
            correctIndicatorRegions.add(correctIndicator9);

            Array<TextureAtlas.AtlasRegion> wrongIndicatorRegions = new Array<>();
            wrongIndicatorRegions.add(wrongIndicator1);
            wrongIndicatorRegions.add(wrongIndicator2);
            wrongIndicatorRegions.add(wrongIndicator3);
            wrongIndicatorRegions.add(wrongIndicator4);
            wrongIndicatorRegions.add(wrongIndicator5);
            wrongIndicatorRegions.add(wrongIndicator6);
            wrongIndicatorRegions.add(wrongIndicator7);
            wrongIndicatorRegions.add(wrongIndicator8);
            wrongIndicatorRegions.add(wrongIndicator9);

            correctIndicatorAnimation = new Animation(Constants.ANIMATION_DURATION, correctIndicatorRegions, Animation.PlayMode.NORMAL);
            wrongIndicatorAnimation = new Animation(Constants.ANIMATION_DURATION, wrongIndicatorRegions, Animation.PlayMode.NORMAL);

            bitcoinLogo = atlas.findRegion(Constants.BITCOIN_LOGO);

            callAnotherHacker = atlas.findRegion(Constants.CALL_ANOTHER_HACKER);
            sshIntoASuperComputer = atlas.findRegion(Constants.SSH_INTO_A_SUPERCOMPUTER);
            ddemProbabilityAlgorithm = atlas.findRegion(Constants.DDEM_PROBABILITY_ALGORITHM);

            callAnotherHackerDisabled = atlas.findRegion(Constants.CALL_ANOTHER_HACKER_DISABLED);
            sshIntoASuperComputerDisabled = atlas.findRegion(Constants.SSH_INTO_A_SUPERCOMPUTER_DISABLED);
            ddemProbabilityAlgorithmDisabled = atlas.findRegion(Constants.DDEM_PROBABILITY_ALGORITHM_DISABLED);

        }
    }

    public class DifficultyScreenAssets {
        public final TextureAtlas.AtlasRegion easyBG;
        public final TextureAtlas.AtlasRegion mediumBG;
        public final TextureAtlas.AtlasRegion hardBG;
        public final TextureAtlas.AtlasRegion easyButtonActive;
        public final TextureAtlas.AtlasRegion easyButtonInactive;
        public final TextureAtlas.AtlasRegion mediumButtonActive;
        public final TextureAtlas.AtlasRegion mediumButtonInactive;
        public final TextureAtlas.AtlasRegion hardButtonActive;
        public final TextureAtlas.AtlasRegion hardButtonInactive;

        public DifficultyScreenAssets(TextureAtlas atlas) {
            easyBG = atlas.findRegion(Constants.EASY);
            mediumBG = atlas.findRegion(Constants.MEDIUM);
            hardBG = atlas.findRegion(Constants.HARD);
            easyButtonActive = atlas.findRegion(Constants.EASYBUTTON_ACTIVE);
            easyButtonInactive = atlas.findRegion(Constants.EASYBUTTON_INACTIVE);
            mediumButtonActive = atlas.findRegion(Constants.MEDIUMBUTTON_ACTIVE);
            mediumButtonInactive = atlas.findRegion(Constants.MEDIUMBUTTON_INACTIVE);
            hardButtonActive = atlas.findRegion(Constants.HARDBUTTON_ACTIVE);
            hardButtonInactive = atlas.findRegion(Constants.HARDBUTTON_INACTIVE);
        }
    }
}