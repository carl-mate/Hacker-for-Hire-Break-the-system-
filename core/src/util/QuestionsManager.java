package util;

import com.badlogic.gdx.Gdx;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import util.Enums.Difficulty;

public class QuestionsManager {
    public static final String TAG = QuestionsManager.class.getName();
    public static final QuestionsManager instance = new QuestionsManager();
    public ArrayList<Item> itemArrayList;

    private QuestionsManager(){
    }

    /*
    Easy difficulty: 20 easy questions, 7 medium questions, 3 hard questions
    Medium difficulty: 15 easy questions, 10 medium questions, 5 hard questions
    Hard difficulty: 10 easy questions, 13 medium questions, 7 hard questions
    Note: The questions appear from EASY to DIFFICULT
     */
    public ArrayList<Item> getItemArrayList(Difficulty difficulty){
        ArrayList<Item> tempItemArrayList = new ArrayList<>();
        Collections.shuffle(this.itemArrayList);
        int easyCtr = 0;
        int mediumCtr = 0;
        int hardCtr = 0;

        for(int i = 0; i < itemArrayList.size(); i++){
            if(difficulty == Difficulty.EASY){
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("easy") && easyCtr < 20){
                    tempItemArrayList.add(itemArrayList.get(i));
                    easyCtr++;
                }
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("medium") && mediumCtr < 7){
                    tempItemArrayList.add(itemArrayList.get(i));
                    mediumCtr++;
                }
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("hard") && hardCtr < 3){
                    tempItemArrayList.add(itemArrayList.get(i));
                    hardCtr++;
                }
            } else if(difficulty == Difficulty.MEDIUM){
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("easy") && easyCtr < 15){
                    tempItemArrayList.add(itemArrayList.get(i));
                    easyCtr++;
                }
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("medium") && mediumCtr < 10){
                    tempItemArrayList.add(itemArrayList.get(i));
                    mediumCtr++;
                }
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("hard") && hardCtr < 5){
                    tempItemArrayList.add(itemArrayList.get(i));
                    hardCtr++;
                }
            } else if(difficulty == Difficulty.HARD){
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("easy") && easyCtr < 10){
                    tempItemArrayList.add(itemArrayList.get(i));
                    easyCtr++;
                }
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("medium") && mediumCtr < 13){
                    tempItemArrayList.add(itemArrayList.get(i));
                    mediumCtr++;
                }
                if(itemArrayList.get(i).getDifficulty().equalsIgnoreCase("hard") && hardCtr < 7){
                    tempItemArrayList.add(itemArrayList.get(i));
                    hardCtr++;
                }
            }
        }

//        for(int i = 0; i < itemArrayList.size(); i++){
//            if(itemArrayList.get(i).getDifficulty().equals(String.valueOf(difficulty))){
//                tempItemArrayList.add(itemArrayList.get(i));
//            }
//        }
        Gdx.app.log(TAG, "GET ITEM ARRAYLIST");
        return tempItemArrayList;
    }

    public void init() throws IOException {
        //fetch questions
        itemArrayList = ExcelFileToArrayList.convert();
    }

    public static class ExcelFileToArrayList {
        public static ArrayList<Item> convert() throws IOException {
            ArrayList<Item> temp = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(Assets.instance.resourcesFilePath.inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(1).getLastCellNum();

            String topic = null;
            String question = null;
            String choiceOne = null;
            String choiceTwo = null;
            String choiceThree = null;
            String choiceFour = null;
            String correctChoice = null;
            String imageFilename = null;
            String difficulty = null;

            for (int r = 1; r <= rows; r++) {
                XSSFRow tempRow = sheet.getRow(r);

                for (int c = 1; c <= cols; c++) {
                    XSSFCell cell = tempRow.getCell(c);

                    switch (c) {
                        case 1:
                            topic = cell.toString();
                            break;
                        case 2:
                            question = cell.toString();
                            break;
                        case 3:
                            choiceOne = cell.toString();
                            break;
                        case 4:
                            choiceTwo = cell.toString();
                            break;
                        case 5:
                            choiceThree = cell.toString();
                            break;
                        case 6:
                            choiceFour = cell.toString();
                            break;
                        case 7:
                            correctChoice = cell.toString();
                            break;
                        case 8:
                            if (cell == null) {
                                imageFilename = "null";
                            } else {
                                imageFilename = cell.toString();
                            }
                            break;
                        case 9:
                            difficulty = cell.toString();
                            break;
                    }
                }
                temp.add(new Item(topic, question, choiceOne, choiceTwo, choiceThree, choiceFour, correctChoice, imageFilename, difficulty));
            }

            return temp;
        }
    }

    public static class Item {
        private final String topic;
        private final String question;
        private final String choiceOne;
        private final String choiceTwo;
        private final String choiceThree;
        private final String choiceFour;
        private final String correctChoice;
        private final String imageFilename;
        private final String difficulty;
        private boolean isAnswered;

        public Item(String topic, String question, String choiceOne, String choiceTwo, String choiceThree,
                    String choiceFour, String correctChoice, String imageFilename, String difficulty) {
            this.topic = topic;
            this.question = question;
            this.choiceOne = choiceOne;
            this.choiceTwo = choiceTwo;
            this.choiceThree = choiceThree;
            this.choiceFour = choiceFour;
            this.correctChoice = correctChoice;
            this.imageFilename = imageFilename;
            this.difficulty = difficulty;
            this.isAnswered = false;
        }

        public void changeIsAnsweredToTrue() {
            this.isAnswered = true;
        }

        public String getTopic() {
            return topic;
        }

        public String getQuestion() {
            return question;
        }

        public String getChoiceOne() {
            return choiceOne;
        }

        public String getChoiceTwo() {
            return choiceTwo;
        }

        public String getChoiceThree() {
            return choiceThree;
        }

        public String getChoiceFour() {
            return choiceFour;
        }

        public String getCorrectChoice() {
            return correctChoice;
        }

        public String getImageFilename() {
            return imageFilename;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public boolean getIsAnswered() {
            return isAnswered;
        }
    }

}




