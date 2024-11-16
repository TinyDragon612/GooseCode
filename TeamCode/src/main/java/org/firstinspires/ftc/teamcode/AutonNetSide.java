package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.universalCode.universalOpMode;
import org.firstinspires.ftc.teamcode.universalCode.values;


@Autonomous(name="net")
public class AutonNetSide extends universalOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        setup();
        clawServo.setPosition(values.clawClosed);

        setOpModeType(1);
        wheels.isAuton();

        long startTime;

        slides.resetEncoders();
        slides.resetArm();
        waitForStart();
        startTime = System.currentTimeMillis();
        wheels.resetEncoders();

        telemetry.addData("Running","Net Side");
        telemetry.update();

        forward(2200);

        slides.setStatus(values.craneState.AUTON_ASCEND);
        for(long time = System.currentTimeMillis(); System.currentTimeMillis() - time < 3000;){
            slides.craneMaintenance();
        }

        rotate(90);
        forward(250);

    }
}