package com.mygdx.game;

public enum PlayerCondition {
    //Walk
    stay, down, up, left, right,
    //Exercise
    squat, bench, deadlift, squatTechnic, pullUps,
    benchTechnic, deadliftTechnic, gripWorkout, archWorkout, legPress, pcSitting, hiper, pushUps,
    //Competition
    compSquat, compBench, compDeadlift,
    //Other
    sleeping, working, sitting, sittingRev, openRef, watchShop, watchLoli, watchCam, lookinLeft, lookinRight, lookinUp, goBuying,
    //Dialog Triger
    talkToArmGirl, talkToBicepsGuy, talkToCoach, talkToStaff, talkToCleaner,
    //goTo
    goToSquatRack, goToBenchRack, goToDeadliftRack, goToLegPress, goToPullUps, goToPushUps, goToHiper
}
