package com.mygdx.game.managers;

import com.mygdx.game.Exercise;
import com.mygdx.game.PlayerCondition;
import com.mygdx.game.Stat;

public class PlayerExerciseManager extends ExerciseManager {

        Exercise squatExr = new Exercise(PlayerCondition.squat);
        Exercise bench = new Exercise(PlayerCondition.bench);
        Exercise deadlift = new Exercise(PlayerCondition.deadlift);
        Exercise pushUps = new Exercise(PlayerCondition.pushUps);
        Exercise pullUps = new Exercise(PlayerCondition.pullUps);

        public PlayerExerciseManager(){
                exercises.add(squatExr);
                exercises.add(bench);
                exercises.add(deadlift);
                exercises.add(pushUps);
                exercises.add(pullUps);

        }

        public void setDebugValues() {
                squatExr.setLVL(10, true);
                bench.setLVL(8,true);
                deadlift.setLVL(12,true);
        }

}
