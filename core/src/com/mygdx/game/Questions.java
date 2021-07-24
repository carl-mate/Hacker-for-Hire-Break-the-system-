package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.util.ArrayList;

import util.Enums.Difficulty;
import util.QuestionsManager;
import util.QuestionsManager.Item;

public class Questions {

    public static final String TAG = Questions.class.getName();

    private BitmapFont questionFont;

    private BitmapFont choicesFont;

    private String question;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String correctChoice;
    private String imageFileName;
    private String questionDifficulty;
    private int questionIndex;

    private Viewport viewport;
    private ArrayList<Item> itemArrayList;
    private long startTime;
    public Difficulty difficulty;



    public Questions(Viewport viewport, Difficulty difficulty) throws IOException {
        this.viewport = viewport;
        this.difficulty = difficulty;

        QuestionsManager.instance.init();


        //edit this
        if(difficulty == Difficulty.EASY){
            itemArrayList = QuestionsManager.instance.getItemArrayList(difficulty);
        } else if(difficulty == Difficulty.MEDIUM){
            itemArrayList = QuestionsManager.instance.getItemArrayList(difficulty);
        } else if(difficulty == Difficulty.HARD){
            itemArrayList = QuestionsManager.instance.getItemArrayList(difficulty);
        }
        questionIndex = 0;
    }

    public void initializeQuestions(){
        if(startTime == 0){
            if(questionIndex < itemArrayList.size()){
                question = itemArrayList.get(questionIndex).getQuestion();
                choiceA = itemArrayList.get(questionIndex).getChoiceOne();
                choiceB = itemArrayList.get(questionIndex).getChoiceTwo();
                choiceC = itemArrayList.get(questionIndex).getChoiceThree();
                choiceD = itemArrayList.get(questionIndex).getChoiceFour();
                correctChoice = itemArrayList.get(questionIndex).getCorrectChoice();
                imageFileName = itemArrayList.get(questionIndex).getImageFilename();
                questionDifficulty = itemArrayList.get(questionIndex).getDifficulty();
                startTime = TimeUtils.nanoTime();
                questionIndex++;
            } else{
                Gdx.app.log(TAG, "DONE");
            }

        }


    }


    public String getCorrectChoice(){
        return this.correctChoice;
    }

    public String getQuestionDifficulty(){
        return this.questionDifficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public String getImageFileName(){
        return imageFileName;
    }

    public void resetStartTime(){
        this.startTime = 0;
    }



}
