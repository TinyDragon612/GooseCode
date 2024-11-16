package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.universalCode.universalOpMode;
import org.firstinspires.ftc.teamcode.universalCode.values;


@Autonomous(name="autonnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
public class Auton1 extends universalOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        setup();
        clawServo.setPosition(values.clawClosed);

        setOpModeType(1);
        wheels.isAuton();

        long startTime;


        waitForStart();
        slides.resetEncoders();
        slides.resetArm();
        startTime = System.currentTimeMillis();
        wheels.resetEncoders();

        telemetry.addData("Running","Observation Side");
        telemetry.update();

        forward(1000);
        rotate(-135);
        forward(300);

        slides.setStatus(values.craneState.HIGH_BASKET);
        for(long time = System.currentTimeMillis(); System.currentTimeMillis() - time < 3000;){
            slides.craneMaintenance();
        }
        forward(150);
        clawServo.setPosition(values.clawOpen);

        forward(-150);
        slides.setStatus(values.craneState.ON_GROUND);
        forward(-300);
        rotate(90);
        forward(-100);
        clawServo.setPosition(values.clawClosed);


//        forward(1250);
//
//        rotate(-90);
//
//        slides.setStatus(values.craneState.ON_GROUND);
//        sleep(1000);
//
//        side(-200);
//
//        rotate(values.turn90DegreesClockwise);
//        foward(100);
//
//        foward(-200);
//
//        side(1100);
//
//        while(System.currentTimeMillis() - startTime < 16000.0);
//
//        foward(3350);
//        side(100);
//        foward(900);

    }
}