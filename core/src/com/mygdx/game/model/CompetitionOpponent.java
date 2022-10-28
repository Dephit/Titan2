package com.mygdx.game.model;


import com.mygdx.game.model.enums.Comp;

import java.util.Random;

public class CompetitionOpponent{

    public double getCurrentSet(int attempt) {
        switch (attempt) {
            case 1:
                return 0;
            case 2:
                return squat.firstAttempt.isGood ? squat.firstAttempt.weight : 0;
            case 3:
                return squat.secondAttempt.isGood ? squat.secondAttempt.weight : squat.firstAttempt.isGood ? squat.firstAttempt.weight : 0;
            case 4:
                return squat.getBestAttempt();
            case 5:
                return squat.getBestAttempt() + (bench.firstAttempt.isGood ? bench.firstAttempt.weight : 0);
            case 6:
                return squat.getBestAttempt() + (bench.secondAttempt.isGood ? bench.secondAttempt.weight : (bench.firstAttempt.isGood ? bench.firstAttempt.weight : 0));
            case 7:
                return squat.getBestAttempt() + bench.getBestAttempt();
            case 8:
                return squat.getBestAttempt() + bench.getBestAttempt() + (deadlift.firstAttempt.isGood ? deadlift.firstAttempt.weight : 0);
            case 9:
                return squat.getBestAttempt() + bench.getBestAttempt() + (deadlift.secondAttempt.isGood ? deadlift.secondAttempt.weight : (deadlift.firstAttempt.isGood ? deadlift.firstAttempt.weight : 0));
            default:
                return getTotal();
        }
    }

    public static class Exr{
        public Attempt firstAttempt;
        public Attempt secondAttempt;
        public Attempt thirdAttempt;

        public Exr(int weight){
            firstAttempt = new Attempt((weight * 0.85));
            Double sW = (double) (firstAttempt.isGood ? (int) (weight * 0.95) : new Random().nextInt(10) <= 8 ? (int) (weight * 0.85) : (int) (weight * 0.95));
            secondAttempt = new Attempt(sW);
            Double tW = secondAttempt.isGood ? weight : new Random().nextInt(10) <= 8 ? sW : weight;
            thirdAttempt = new Attempt(tW);
        }

        public double getBestAttempt(){
            return thirdAttempt.isGood ?
                    thirdAttempt.weight : secondAttempt.isGood ?
                    secondAttempt.weight : firstAttempt.isGood ? firstAttempt.weight : 0;
        }

    }

    public static class Attempt{
        private double weight;
        public boolean isGood = new Random().nextInt(10) <= 7;

        public void setWeight(double weight) {
            this.weight = Math.round(weight * 0.4) / 0.4;
        }

        public double getWeight() {
            return weight;
        }

        public Attempt(Double weight){
            setWeight(weight.intValue());
        }

        public Attempt(Double weight, boolean isGood){
            setWeight(weight.intValue());
            this.isGood = isGood;
        }
    }

    public String name;
    public Exr squat;
    public Exr bench;
    public Exr deadlift;
    public double total;

    public CompetitionOpponent(String name, int avgLvl) {
        this.name = name;
        squat = new Exr(
                80 + (new Random().nextInt(4) + 3) * avgLvl
        );
        bench = new Exr(
                60 + (new Random().nextInt(3) + 2) * avgLvl
        );
        deadlift = new Exr(
                80 + (new Random().nextInt(4) + 3) * avgLvl
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

    public double getTotal() {
        if(squat.getBestAttempt() == 0 ||  bench.getBestAttempt() == 0 || deadlift.getBestAttempt() == 0){
            total = 0;
        }else {
            total = squat.getBestAttempt() + bench.getBestAttempt() + deadlift.getBestAttempt();
        }
        return total;
    }
}