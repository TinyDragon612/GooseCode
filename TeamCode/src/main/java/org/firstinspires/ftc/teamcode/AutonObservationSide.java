package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.universalCode.universalOpMode;
import org.firstinspires.ftc.teamcode.universalCode.values;


@Autonomous(name="observe")
public class AutonObservationSide extends universalOpMode {

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

        forward(-500);

    }
}