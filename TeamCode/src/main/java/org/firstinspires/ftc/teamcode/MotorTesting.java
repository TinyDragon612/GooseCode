package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad.*;

import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.Map;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.util.ElapsedTime.Resolution;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name="Motor Testing PLEASE", group="Linear Opmode")
//@Disabled
public class MotorTesting extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor.ZeroPowerBehavior brake = DcMotor.ZeroPowerBehavior.BRAKE;
    private DcMotor.ZeroPowerBehavior floatt = DcMotor.ZeroPowerBehavior.FLOAT;

    private DcMotor topRight, bottomRight, topLeft, bottomLeft;
    private DcMotorEx slide1, slide2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        topRight = hardwareMap.get(DcMotor.class, "topRight");
        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        bottomRight = hardwareMap.get(DcMotor.class, "bottomRight");
        bottomLeft = hardwareMap.get(DcMotor.class, "bottomLeft");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        bottomRight.setDirection(DcMotor.Direction.REVERSE);
        bottomLeft.setDirection(DcMotor.Direction.REVERSE);

        topRight.setZeroPowerBehavior(floatt);
        topLeft.setZeroPowerBehavior(floatt);
        bottomRight.setZeroPowerBehavior(floatt);
        bottomLeft.setZeroPowerBehavior(floatt);

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");

        slide1.setTargetPosition(0);
        slide2.setTargetPosition(0);
        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide1.setZeroPowerBehavior(floatt);
        slide2.setZeroPowerBehavior(floatt);

        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.update();

        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("topLeft", topLeft.getCurrentPosition());
            telemetry.addData("topRight", topRight.getCurrentPosition());
            telemetry.addData("bottomLeft", bottomLeft.getCurrentPosition());
            telemetry.addData("bottomRight", bottomRight.getCurrentPosition());
            telemetry.addData("slide1", slide1.getCurrentPosition());
            telemetry.addData("slide2", slide2.getCurrentPosition());
            telemetry.addData("arm", arm.getCurrentPosition());
            telemetry.update();
        }
    }
}
