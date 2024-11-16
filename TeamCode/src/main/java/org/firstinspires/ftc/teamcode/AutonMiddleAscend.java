package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.universalCode.universalOpMode;
import org.firstinspires.ftc.teamcode.universalCode.values;


@Autonomous(name="middle ascend")
public class AutonMiddleAscend extends universalOpMode {

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

        forward(-800);
        rotate(90);
        rotate(90);
        forward(600);
        slides.setArmPower(0.7);
        slides.setStatus(values.craneState.HIGH_CHAMBER);
        for(long hold = System.currentTimeMillis();System.currentTimeMillis() - hold < 2000 && opModeIsActive();){slides.craneMaintenance();}
        clawServo.setPosition(values.clawOpen);
        slides.setArmPower(0.1);
        slides.setStatus(values.craneState.ALL_THE_WAY_BACK);
        forward(-600);
        rotate(90);
        forward(-1400);
        side(-600);
        forward(800);
        slides.setStatus(values.craneState.AUTON_ASCEND);
        for(long hold = System.currentTimeMillis();System.currentTimeMillis() - hold < 4000 && opModeIsActive();){slides.craneMaintenance();}
    }
}