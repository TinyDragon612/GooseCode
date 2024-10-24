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
                return 0;
            case HIGH_CHAMBER:
                return 0;
            case LOW_BASKET:
                return 0;
            case HIGH_BASKET:
                return 0;
            case ON_WALL:
                return 0;
            case HANGING:
                return 0;
            case ALL_THE_WAY_BACK:
                return 0;
            //Default is the ON_GROUND position
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
                return 0;
            case HIGH_BASKET:
                return 0;
            case ON_WALL:
                return 0;
            case HANGING:
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
        HANGING,
        ALL_THE_WAY_BACK,
        SLIDES_MAX
    }

    public static int slidesMaximum = 2800;


    //Auton movement values
    public static int turn90DegreesClockwise = 90;

    public static int turn90DegreesCounterClockwise = - 90;
}
