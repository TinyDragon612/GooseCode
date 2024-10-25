package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.universalCode.universalOpMode;
import org.firstinspires.ftc.teamcode.universalCode.values;


@Autonomous(name="autonnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
public class Auton1 extends universalOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        setup();

        setOpModeType(1);
        wheels.isAuton();

        long startTime;


        waitForStart();
        slides.resetEncoders();
        slides.resetArm();
        while (opModeIsActive() && !isStopRequested()) {
            startTime = System.currentTimeMillis();
            wheels.resetEncoders();


                    telemetry.addData("Running","Observation Side");
                    telemetry.update();

                    foward(400);
//                  side(20);

//            rotate(values.turn90DegreesCounterClockwise);
//            sleep(50);
//            rotate(values.turn90DegreesCounterClockwise);

//                    side(-200);
//
//                    rotate(values.turn90DegreesClockwise);
//                    foward(100);
//
//                    foward(-200);
//
//                    side(1100);
//
//                    while(System.currentTimeMillis() - startTime < 16000.0);
//
//                    foward(3350);
//                    side(100);
//                    foward(900);


                    //while(System.currentTimeMillis() - startTime < 16000.0);


                    // clawServo.setPosition(values.leftClawOpen);
                    // sleep(100);

        }


    }
}