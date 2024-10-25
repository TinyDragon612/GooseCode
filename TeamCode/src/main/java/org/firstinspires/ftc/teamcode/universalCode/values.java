package org.firstinspires.ftc.teamcode.universalCode;

public class values {

    //Claw Positions
    public static double clawOpen = 0;
    public static double clawClosed = 0.12;


    //Crane Arm Positions (REDO)\
    public static int getArmTargetPosition(craneState currentStatus){
        switch(currentStatus){
            case NUDGED:
                return 0;
            case LOW_CHAMBER:
                return 550;
            case HIGH_CHAMBER:
                return 550;
            case LOW_BASKET:
                return 350;
            case HIGH_BASKET:
                return 280;
            case ON_WALL:
                return 490;
            case HANGING_STAGE_1:
                return 700;
            case HANGING_STAGE_2:
                return 700;
            case ALL_THE_WAY_BACK:
                return 0;
            case ON_GROUND:
                return 700;
            default:
                return 500;
        }
    }

    //Slides Positions (NEED TESTING)
    public static int getSlidesTargetPosition(craneState currentStatus){
        switch(currentStatus){
            case LOW_CHAMBER:
                return 0;
            case HIGH_CHAMBER:
                return 1000;
            case LOW_BASKET:
                return 1125;
            case HIGH_BASKET:
                return slidesMaximum;
            case ON_WALL:
                return 0;
            case HANGING_STAGE_1:
                return 1300;
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
    }

    public static int slidesMaximum = 2990;


    //Auton movement values
    public static int turn90DegreesClockwise = 90;

    public static int turn90DegreesCounterClockwise = - 90;
}
