package entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Assets;
import util.Util;

import static util.Constants.INDICATOR_CENTER;

public class Indicator {
    private ExtendViewport viewport;
    private final long startTime;

    public Indicator(ExtendViewport viewport){
        this.viewport = viewport;
        startTime = TimeUtils.nanoTime();
    }

    public void renderCorrect(SpriteBatch batch){
        final float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        Util.drawTextureRegion(
                batch,
                (TextureRegion) Assets.instance.playScreenAssets.correctIndicatorAnimation.getKeyFrame(elapsedTime),
                new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 100),
                INDICATOR_CENTER);
    }

    public void renderWrong(SpriteBatch batch){
        final float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        Util.drawTextureRegion(
                batch,
                (TextureRegion) Assets.instance.playScreenAssets.wrongIndicatorAnimation.getKeyFrame(elapsedTime),
                new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 100),
                INDICATOR_CENTER);
    }

    public boolean isCorrectIndicatorFinished(){
        final float elapsedTime = Util.secondsSince(startTime);
        return Assets.instance.playScreenAssets.correctIndicatorAnimation.isAnimationFinished(elapsedTime);
    }

    public boolean isWrongIndicatorFinished(){
        final float elapsedTime = Util.secondsSince(startTime);
        return Assets.instance.playScreenAssets.wrongIndicatorAnimation.isAnimationFinished(elapsedTime);
    }
}
