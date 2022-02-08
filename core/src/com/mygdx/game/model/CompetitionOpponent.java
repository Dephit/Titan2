package com.mygdx.game.model;


import com.mygdx.game.model.enums.Comp;

import java.util.Random;

public class CompetitionOpponent{

    public Integer getCurrentSet(int attempt) {
        switch (attempt) {
            case 1:
                return 0;
            case 2:
                return squat.firstAttempt.isGood ? squat.firstAttempt.weight : 0;
            case 3:
                return squat.secondAttempt.isGood ? squat.secondAttempt.weight : squat.firstAttempt.isGood ? squat.firstAttempt.weight : 0;
            case 4:
                return squat.getBestAttempt() + 0;
            case 5:
                return squat.getBestAttempt() + (bench.firstAttempt.isGood ? bench.firstAttempt.weight : 0);
            case 6:
                return squat.getBestAttempt() + (bench.secondAttempt.isGood ? bench.secondAttempt.weight : (bench.firstAttempt.isGood ? bench.firstAttempt.weight : 0));
            case 7:
                return squat.getBestAttempt() + bench.getBestAttempt() + 0;
            case 8:
                return squat.getBestAttempt() + bench.getBestAttempt() + (deadlift.firstAttempt.isGood ? deadlift.firstAttempt.weight : 0);
            case 9:
                return squat.getBestAttempt() + bench.getBestAttempt() + (deadlift.secondAttempt.isGood ? deadlift.secondAttempt.weight : (deadlift.firstAttempt.isGood ? deadlift.firstAttempt.weight : 0));
            default:
                return getTotal();
        }
    }

    public class Exr{
        public Attempt firstAttempt;
        public Attempt secondAttempt;
        public Attempt thirdAttempt;

        public Exr(int weight){
            firstAttempt = new Attempt((int) (weight * 0.85f));
            int sW = firstAttempt.isGood ? (int) (weight * 0.95f) : new Random().nextInt(10) <= 8 ? (int) (weight * 0.85f) : (int) (weight * 0.95f);
            secondAttempt = new Attempt(sW);
            int tW = secondAttempt.isGood ? weight : new Random().nextInt(10) <= 8 ? sW : weight;
            thirdAttempt = new Attempt(tW);
        }

        public int getBestAttempt(){
            return thirdAttempt.isGood ?
                    thirdAttempt.weight : secondAttempt.isGood ?
                    secondAttempt.weight : firstAttempt.isGood ? firstAttempt.weight : 0;
        }

    }

    public class Attempt{
        public int weight;
        public boolean isGood = new Random().nextInt(10) <= 7;

        public Attempt(int weight){
            this.weight = weight;
        }
    }

    public String name;
    public Exr squat;
    public Exr bench;
    public Exr deadlift;
    public int total;

    public CompetitionOpponent(String name, int avgLvl) {
        this.name = name;
        squat = new Exr(
                80 + new Random().nextInt(30) * avgLvl
        );
        bench = new Exr(
                60 + new Random().nextInt(30) * avgLvl
        );
        deadlift = new Exr(
                80 + new Random().nextInt(30) * avgLvl
        );
    }

    public CompetitionOpponent(String name, int st, int bp, int dl) {
        this.name = name;
        squat = new Exr(
                st
        );
        bench = new Exr(
                bp
        );
        deadlift = new Exr(
                dl
        );
    }

    public int getTotal() {
        if(squat.getBestAttempt() == 0 ||  bench.getBestAttempt() == 0 || deadlift.getBestAttempt() == 0){
            total = 0;
        }else {
            total = squat.getBestAttempt() + bench.getBestAttempt() + deadlift.getBestAttempt();
        }
        return total;
    }
}