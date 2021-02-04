package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GymRoom extends BaseRoom{

    public GymRoom() {
        super("gym");
    }

    public GymRoom(Player player) {
        super("gym", player);
    }

    @Override
    protected InputListener getEventListener(String text) {
        return  new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(text.equals("rack")){
                    player.setPath(890 , 125, 865, 125, PlayerCondition.squat);
                }
                /*player.doExercise = false;
                height = player.getHeight(squat);
                posExerciseY = (int) MathUtils.random(165, 165 + 500 - height);
                setUpWindow(none);
                player.animationTime = 0;
                setUpInfoWindow(showExrButton);*/
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };

    }

}


