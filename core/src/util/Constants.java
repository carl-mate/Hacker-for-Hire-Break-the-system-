package util;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final Color BACKGROUND_COLOR = Color.SKY;


    public static final float WORLD_SIZE = 664f;

    public static final String TEXTURE_ATLAS = "images/hacker.pack.atlas";

    public static final String LOADING_SCREEN = "loading-screen";

    public static Preferences preferences;

    public static String MENU_SCREEN_NAME;

    //Main Menu Assets
    public static final String ENTER_CODENAME = "enterCodename";
    public static final String MAIN_MENU_BG = "mainMenu-bg";
    public static final String PLAYBUTTON_ACTIVE = "playButton-active";
    public static final String PLAYBUTTON_INACTIVE = "playButton-inactive";
    public static final String OPTIONBUTTON_ACTIVE = "optionsButton-active";
    public static final String OPTIONBUTTON_INACTIVE = "optionsButton-inactive";
    public static final String HELPBUTTON_ACTIVE = "helpButton-active";
    public static final String HELPBUTTON_INACTIVE = "helpButton-inactive";
    public static final String INSTRUCTIONS_1 = "instructions-1";
    public static final String INSTRUCTIONS_2 = "instructions-2";
    public static final String INSTRUCTIONS_3 = "instructions-3";
    public static final String ACTIONS_1 = "actions-1";
    public static final String ACTIONS_2 = "actions-2";
    public static final String ACTIONS_3 = "actions-3";
    public static final String BINARY_BACKGROUND = "binary-background";
    public static final Vector2 UI_BUTTON_CENTER = new Vector2(94, 38);
    public static final Vector2 BG_CENTER = new Vector2(664 / 2, 664 / 2);
    public static final float UI_BUTTON_WIDTH = 180;
    public static final float UI_BUTTON_HEIGHT = 60;

    //Difficulty Screen Assets
    public static final String EASY = "easy";
    public static final String MEDIUM = "medium";
    public static final String HARD = "hard";
    public static final String EASYBUTTON_ACTIVE = "easy-active";
    public static final String EASYBUTTON_INACTIVE = "easy-inactive";
    public static final String MEDIUMBUTTON_ACTIVE = "medium-active";
    public static final String MEDIUMBUTTON_INACTIVE = "medium-inactive";
    public static final String HARDBUTTON_ACTIVE = "hard-active";
    public static final String HARDBUTTON_INACTIVE = "hard-inactive";
    public static final Vector2 DIFFICULTY_BUTTON_CENTER = new Vector2(194, 75);
    public static final float DIFFICULTY_BUTTON_WIDTH = 360;
    public static final float DIFFICULTY_BUTTON_HEIGHT = 113;

    //Play Screen Assets
    public static final int CHANCES = 5;
    public static final int TOTAL_QUESTIONS = 30;
    //scoring system
    public static final float EASY_SCORE_0_99 = 0.01f;
    public static final float EASY_SCORE_100_199 = 0.005f;
    public static final float EASY_SCORE_200_299 = 0.0025f;
    public static final float EASY_SCORE_300 = 0.0013f;

    public static final float MEDIUM_SCORE_0_99 = 0.02f;
    public static final float MEDIUM_SCORE_100_199 = 0.01f;
    public static final float MEDIUM_SCORE_200_299 = 0.005f;
    public static final float MEDIUM_SCORE_300 = 0.0026f;

    public static final float HARD_SCORE_0_99 = 0.04f;
    public static final float HARD_SCORE_100_199 = 0.02f;
    public static final float HARD_SCORE_200_299 = 0.01f;
    public static final float HARD_SCORE_300 = 0.0052f;

    public static final String GAME_OVER = "gameOver";
    public static final String YOU_WIN = "youWin";
    public static final String TRY_AGAIN_INACTIVE = "tryAgain-inactive";
    public static final String TRY_AGAIN_ACTIVE = "tryAgain-active";
    public static final String RETURN_TO_MENU_INACTIVE = "returnToMenu-inactive";
    public static final String RETURN_TO_MENU_ACTIVE = "returnToMenu-active";
    public static final String HIGHSCORES_ACTIVE = "highScores-active";
    public static final String HIGHSCORES_INACTIVE = "highScores-inactive";
    public static final String HIGHSCORES_BG = "highScores-bg";

    public static final String PLAY_SCREEN_IMAGE_FRAME = "show-image";

    public static final String PLAY_SCREEN_BG = "playScreenBG";
    public static final String PLAY_SCREEN_BUTTON = "playScreenButton";
    public static final String PLAY_SCREEN_BUTTON_CORRECT = "playScreenButton-correct";
    public static final String PLAY_SCREEN_MENU_BUTTON = "playMenuButton";
    public static final Vector2 PLAY_SCREEN_BUTTON_CENTER = new Vector2(149, 49);
    public static final float PLAY_SCREEN_BUTTON_WIDTH = 298;
    public static final float PLAY_SCREEN_BUTTON_HEIGHT = 97;

    public static final float QUESTION_BOX_WIDTH = 465;
    public static final float QUESTION_BOX_HEIGHT = 188;

    public static final String MUSIC_ON = "music-on";
    public static final String MUSIC_OFF = "music-off";
    public static final String ON_OFF = "on-off";
    public static final Vector2 MUSIC_BUTTON_CENTER = new Vector2(49, 49);
    public static final float MUSIC_BUTTON_WIDTH = 83;
    public static final float MUSIC_BUTTON_HEIGHT = 70;

    public static final Vector2 PLAY_SCREEN_MENU_BUTTON_CENTER = new Vector2(43, 43);
    public static final float PLAY_SCREEN_MENU_BUTTON_WIDTH = 44;
    public static final float PLAY_SCREEN_MENU_BUTTON_HEIGHT = 17;

    public static final String PROGRESS_BAR_1  = "progress-bar-1";
    public static final String PROGRESS_BAR_2  = "progress-bar-2";
    public static final String PROGRESS_BAR_3  = "progress-bar-3";
    public static final String PROGRESS_BAR_4  = "progress-bar-4";
    public static final String PROGRESS_BAR_5  = "progress-bar-5";
    public static final String PROGRESS_BAR_6  = "progress-bar-6";
    public static final String PROGRESS_BAR_7  = "progress-bar-7";
    public static final String PROGRESS_BAR_8  = "progress-bar-8";
    public static final String PROGRESS_BAR_9  = "progress-bar-9";
    public static final String PROGRESS_BAR_10  = "progress-bar-10";
    public static final String PROGRESS_BAR_11  = "progress-bar-11";
    public static final String PROGRESS_BAR_12  = "progress-bar-12";
    public static final String PROGRESS_BAR_13  = "progress-bar-13";
    public static final String PROGRESS_BAR_14  = "progress-bar-14";
    public static final String PROGRESS_BAR_15  = "progress-bar-15";
    public static final String PROGRESS_BAR_16  = "progress-bar-16";
    public static final String PROGRESS_BAR_17  = "progress-bar-17";
    public static final String PROGRESS_BAR_18  = "progress-bar-18";
    public static final String PROGRESS_BAR_19  = "progress-bar-19";
    public static final String PROGRESS_BAR_20  = "progress-bar-20";
    public static final String PROGRESS_BAR_21  = "progress-bar-21";
    public static final String PROGRESS_BAR_22  = "progress-bar-22";
    public static final String PROGRESS_BAR_23  = "progress-bar-23";
    public static final String PROGRESS_BAR_24  = "progress-bar-24";
    public static final String PROGRESS_BAR_25  = "progress-bar-25";
    public static final String PROGRESS_BAR_26  = "progress-bar-26";
    public static final String PROGRESS_BAR_27  = "progress-bar-27";
    public static final String PROGRESS_BAR_28  = "progress-bar-28";
    public static final String PROGRESS_BAR_29  = "progress-bar-29";
    public static final String PROGRESS_BAR_30  = "progress-bar-30";
    public static final Vector2 PROGRESS_BAR_CENTER = new Vector2(84, 20);
    public static final float PROGRESS_BAR_WIDTH = 168;
    public static final float PROGRESS_BAR_HEIGHT = 39;

    public static final String LIFE_ANIMATION_1 = "life-animation-1";
    public static final String LIFE_ANIMATION_2 = "life-animation-2";
    public static final String LIFE_ANIMATION_3 = "life-animation-3";
    public static final String LIFE_ANIMATION_4 = "life-animation-4";
    public static final String LIFE_ANIMATION_5 = "life-animation-5";
    public static final String LIFE_ANIMATION_6 = "life-animation-6";
    public static final String LIFE_ANIMATION_7 = "life-animation-7";
    public static final Vector2 LIFE_ANIMATION_CENTER = new Vector2(250, 73);
    public static final float LIFE_ANIMATION_WIDTH = 499;
    public static final float LIFE_ANIMATION_HEIGHT = 145;

    public static final String CORRECT_INDICATOR_1 = "correct-indicator-1";
    public static final String CORRECT_INDICATOR_2 = "correct-indicator-2";
    public static final String CORRECT_INDICATOR_3 = "correct-indicator-3";
    public static final String CORRECT_INDICATOR_4 = "correct-indicator-4";
    public static final String CORRECT_INDICATOR_5 = "correct-indicator-5";
    public static final String CORRECT_INDICATOR_6 = "correct-indicator-6";
    public static final String CORRECT_INDICATOR_7 = "correct-indicator-7";
    public static final String CORRECT_INDICATOR_8 = "correct-indicator-8";
    public static final String CORRECT_INDICATOR_9 = "correct-indicator-9";

    public static final String WRONG_INDICATOR_1 = "wrong-indicator-1";
    public static final String WRONG_INDICATOR_2 = "wrong-indicator-2";
    public static final String WRONG_INDICATOR_3 = "wrong-indicator-3";
    public static final String WRONG_INDICATOR_4 = "wrong-indicator-4";
    public static final String WRONG_INDICATOR_5 = "wrong-indicator-5";
    public static final String WRONG_INDICATOR_6 = "wrong-indicator-6";
    public static final String WRONG_INDICATOR_7 = "wrong-indicator-7";
    public static final String WRONG_INDICATOR_8 = "wrong-indicator-8";
    public static final String WRONG_INDICATOR_9 = "wrong-indicator-9";

    public static final Vector2 INDICATOR_CENTER = new Vector2(332, 49);
    public static final float INDICATOR_WIDTH = 664;
    public static final float INDICATOR_HEIGHT = 98;
    public static final float ANIMATION_DURATION = 0.1f;

    public static final String BITCOIN_LOGO = "bitcoin-logo";
    public static final Vector2 BITCOIN_LOGO_CENTER = new Vector2(43, 43);
    public static final float BITCOIN_LOGO_WIDTH = 29;
    public static final float BITCOIN_LOGO_HEIGHT = 29;

    public static final String CALL_ANOTHER_HACKER = "call-another-hacker";
    public static final String SSH_INTO_A_SUPERCOMPUTER = "ssh-into-a-supercomputer";
    public static final String DDEM_PROBABILITY_ALGORITHM = "ddem-probability-algorithm";

    public static final String CALL_ANOTHER_HACKER_DISABLED = "call-another-hacker-disabled";
    public static final String SSH_INTO_A_SUPERCOMPUTER_DISABLED = "ssh-into-a-supercomputer-disabled";
    public static final String DDEM_PROBABILITY_ALGORITHM_DISABLED = "ddem-probability-algorithm-disabled";

    public static final Vector2 CALL_ANOTHER_HACKER_CENTER = new Vector2(12, 15);
    public static final float CALL_ANOTHER_HACKER_WIDTH = 23;
    public static final float CALL_ANOTHER_HACKER_HEIGHT = 30;

    public static final Vector2 SSH_INTO_A_SUPERCOMPUTER_CENTER = new Vector2(23, 15);
    public static final float SSH_INTO_A_SUPERCOMPUTER_WIDTH = 45;
    public static final float SSH_INTO_A_SUPERCOMPUTER_HEIGHT = 30;

    public static final Vector2 DDEM_PROBABILITY_ALGORITHM_CENTER = new Vector2(15, 15);
    public static final float DDEM_PROBABILITY_ALGORITHM_WIDTH = 30;
    public static final float DDEM_PROBABILITY_ALGORITHM_HEIGHT = 30;








}
