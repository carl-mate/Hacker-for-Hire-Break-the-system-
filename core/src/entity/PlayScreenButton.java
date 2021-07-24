package entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Util;

import static util.Constants.PLAY_SCREEN_BUTTON_CENTER;

public class PlayScreenButton {
    private ExtendViewport viewport;

    public PlayScreenButton(ExtendViewport viewport){
        this.viewport = viewport;
    }

    public void renderButton(SpriteBatch batch, String... strings){
        for(String x: strings){
            if(x.equalsIgnoreCase("A")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 20), PLAY_SCREEN_BUTTON_CENTER); //A
            }

            if(x.equalsIgnoreCase("B")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 20), PLAY_SCREEN_BUTTON_CENTER); //B
            }

            if(x.equalsIgnoreCase("C")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 145), PLAY_SCREEN_BUTTON_CENTER); //C
            }

            if(x.equalsIgnoreCase("D")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 145), PLAY_SCREEN_BUTTON_CENTER); //D
            }

            if(x.equalsIgnoreCase("CorrectA")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButtonCorrect, new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 20), PLAY_SCREEN_BUTTON_CENTER); //A
            }

            if(x.equalsIgnoreCase("CorrectB")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButtonCorrect, new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 20), PLAY_SCREEN_BUTTON_CENTER); //B
            }

            if(x.equalsIgnoreCase("CorrectC")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButtonCorrect, new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 145), PLAY_SCREEN_BUTTON_CENTER); //C
            }

            if(x.equalsIgnoreCase("CorrectD")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButtonCorrect, new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 145), PLAY_SCREEN_BUTTON_CENTER); //D
            }

            if(x.equalsIgnoreCase("ALL")){
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 20), PLAY_SCREEN_BUTTON_CENTER); //A
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 20), PLAY_SCREEN_BUTTON_CENTER); //B
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 4, viewport.getCamera().viewportHeight / 4 - 145), PLAY_SCREEN_BUTTON_CENTER); //C
                Util.drawTextureRegion(batch, Assets.instance.playScreenAssets.playScreenButton, new Vector2(viewport.getCamera().viewportWidth / 1.35f, viewport.getCamera().viewportHeight / 4 - 145), PLAY_SCREEN_BUTTON_CENTER); //D
            }
        }
    }


}
