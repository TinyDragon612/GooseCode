package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class Wheels extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor topRight = hardwareMap.get(DcMotor.class, "topRight");
        DcMotor topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        DcMotor bottomRight = hardwareMap.get(DcMotor.class, "bottomRight");
        DcMotor bottomLeft = hardwareMap.get(DcMotor.class, "bottomLeft");

        waitForStart();

        if(opModeIsActive()) {
            telemetry.addData("Status", "aaaaaaaaaa");

            for(int i = 0; i < 100000; i++){
                topLeft.setPower(1);
            }
            topLeft.setPower(0);
            for(int i = 0; i < 100000; i++){
                topRight.setPower(1);
            }
            topRight.setPower(0);
            for(int i = 0; i < 100000; i++){
                bottomLeft.setPower(1);
            }
            bottomLeft.setPower(0);
            for(int i = 0; i < 100000; i++){
                bottomRight.setPower(1);
            }
            bottomRight.setPower(0);
        }
    }
}
