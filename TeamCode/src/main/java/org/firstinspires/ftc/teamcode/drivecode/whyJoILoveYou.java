package org.firstinspires.ftc.teamcode.drivecode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Love ya Jo", group="Linear Opmode")
public class whyJoILoveYou extends LinearOpMode {
    public void runOpMode(){
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("","\uD83D\uDE1A");
            telemetry.update();
        }
    }
}