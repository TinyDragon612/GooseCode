package org.firstinspires.ftc.teamcode.universalCode;

public class values {

    //Claw Positions
    public static double clawOpen = 0.3;
    public static double clawClosed = 0.48;


    //Crane Arm Positions (REDO)\
    public static int getArmTargetPosition(craneState currentStatus){
        switch(currentStatus){
            case NUDGED:
                return 0;
            case LOW_CHAMBER:
                return 0;
            case HIGH_CHAMBER:
                return 400;
            case LOW_BASKET:
                return 470;
            case HIGH_BASKET:
                return 370;
            case ON_WALL:
                return 545;
            case HANGING_STAGE_1:
                return 0;
            case HANGING_STAGE_2:
                return 0;
            case ALL_THE_WAY_BACK:
                return 0;
            case ON_GROUND:
                return 640;
            case AUTON_ASCEND:
                return 400;
            default:
                return 0;
        }
    }

    //Slides Positions (NEED TESTING)
    public static int getSlidesTargetPosition(craneState currentStatus){
        switch(currentStatus){
            case LOW_CHAMBER:
                return 0;
            case HIGH_CHAMBER:
                return 0;
            case LOW_BASKET:
                return 1475;
            case HIGH_BASKET:
                return 2200;
            case ON_WALL:
                return 0;
            case HANGING_STAGE_1:
                return 0;
            case HANGING_STAGE_2:
                return 0;
            case SLIDES_MAX:
                return slidesMaximum;
            //Default is the ON_GROUND position
            default:
                return 0;
        }
    }

    public enum craneState{
        ON_GROUND,
        NUDGED,
        LOW_CHAMBER,
        HIGH_CHAMBER,
        LOW_BASKET,
        HIGH_BASKET,
        ON_WALL,
        HANGING_STAGE_1,
        HANGING_STAGE_2,
        ALL_THE_WAY_BACK,
        SLIDES_MAX,
        AUTON_ASCEND
    }

    public static int slidesMaximum = 2150;
}
