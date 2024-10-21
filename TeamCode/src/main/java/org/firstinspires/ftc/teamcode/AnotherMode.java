package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@TeleOp(name="AnotherMode", group="Linear Opmode")
@Disabled
public class AnotherMode extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor.ZeroPowerBehavior brake = DcMotor.ZeroPowerBehavior.BRAKE;
    private DcMotor.ZeroPowerBehavior floatt = DcMotor.ZeroPowerBehavior.FLOAT;

    private DcMotor topRight, bottomRight, topLeft, bottomLeft;
    private DcMotorEx slide1, slide2;

    private int errorBound = 60;

    private Servo claw;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        topRight = hardwareMap.get(DcMotor.class, "topRight");
        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        bottomRight = hardwareMap.get(DcMotor.class, "bottomRight");
        bottomLeft = hardwareMap.get(DcMotor.class, "bottomLeft");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        bottomRight.setDirection(DcMotor.Direction.FORWARD);
        bottomLeft.setDirection(DcMotor.Direction.REVERSE);

        topRight.setZeroPowerBehavior(floatt);
        topLeft.setZeroPowerBehavior(floatt);
        bottomRight.setZeroPowerBehavior(floatt);
        bottomLeft.setZeroPowerBehavior(floatt);

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");

        slide1.setDirection(DcMotor.Direction.REVERSE);

        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide1.setTargetPosition(0);
        slide2.setTargetPosition(0);

        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        claw = hardwareMap.get(Servo.class, "claw");
        slide1.setZeroPowerBehavior(brake);
        slide2.setZeroPowerBehavior(brake);

        telemetry.update();

        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "AAAAAAAA");


            // Send power to wheels
            topRight.setPower(((-gamepad1.left_stick_y + -gamepad1.left_stick_x)) + (-gamepad1.right_stick_x));
            topLeft.setPower(((-gamepad1.left_stick_y + gamepad1.left_stick_x)) + ((gamepad1.right_stick_x)));
            bottomRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x) + (-gamepad1.right_stick_x));
            bottomLeft.setPower(((-gamepad1.left_stick_y + -gamepad1.left_stick_x)) + (gamepad1.right_stick_x));

            if(gamepad1.left_trigger > 0.2){
                slidesDown();
                waitforDrawers(slide1, slide2);
                reset();
            }else if(gamepad1.right_trigger > 0.2){
                setBothSlides(2800, gamepad1.right_trigger);
                // waitforDrawers(slide1, slide2);
                // stall(slide1);
                // stall(slide2);
            }else{
                //setBothSlides(0, 0);
                // stall(slide1);
                // stall(slide2);
                slide1.setZeroPowerBehavior(brake);
                slide2.setZeroPowerBehavior(brake);
                slide1.setTargetPositionTolerance(slide1.getCurrentPosition());
                slide2.setTargetPositionTolerance(slide2.getCurrentPosition());
            }

            if(gamepad1.cross){
                claw.setPosition(0);
            }else if(gamepad1.circle){
                claw.setPosition(0.12);
            }

            telemetry.update();

        }
    }

    public void setBothSlides(int position, double power){
        // untoPosition(slide1);
        // untoPosition(slide2);
        // runtoPosition(slide1);
        // runtoPosition(slide2);
        // slide1.setTargetPosition(slide1.getCurrentPosition() + position);
        // slide2.setTargetPosition(slide2.getCurrentPosition() + position);
        slide1.setTargetPosition(position);
        slide2.setTargetPosition(position);
        slide1.setPower(power);
        slide2.setPower(power);
    }

    public void slidesDown(){
        slide1.setTargetPosition(0);
        slide2.setTargetPosition(0);
        slide1.setPower(1);
        slide2.setPower(1);
    }

    public int getBothSlides(){
        return (slide1.getCurrentPosition() + slide2.getCurrentPosition()) / 2;
    }
    public void nostall(DcMotorEx Harry) {
        Harry.setZeroPowerBehavior(floatt);
        Harry.setPower(0);
    }

    public void stall(DcMotorEx DcMotar) {
        DcMotar.setZeroPowerBehavior(brake);
        DcMotar.setPower(0);
    }

    public void runtoPosition(DcMotorEx John) {
        John.setTargetPosition(0);
        John.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        John.setPower(0);
    }
    public void untoPosition(DcMotorEx Neil) {
        Neil.setPower(0);
        Neil.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public void reset(){
        slide1.setPower(0);
        slide2.setPower(0);

        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide1.setTargetPosition(0);
        slide2.setTargetPosition(0);

        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void waitforDrawer(DcMotor george) {
        while(!(george.getCurrentPosition() > george.getTargetPosition() - errorBound && george.getCurrentPosition() < george.getTargetPosition() + errorBound));
    }

    public void waitforDrawers(DcMotor george, DcMotor BobbyLocks) {
        while(!((george.getCurrentPosition() > george.getTargetPosition() - errorBound && george.getCurrentPosition() < george.getTargetPosition() + errorBound) &&
                (BobbyLocks.getCurrentPosition() > BobbyLocks.getTargetPosition() - errorBound && BobbyLocks.getCurrentPosition() < BobbyLocks.getTargetPosition() + errorBound)));
    }

    public void nostall(DcMotor Harry) {
        Harry.setZeroPowerBehavior(floatt);
        Harry.setPower(0);
    }

    public void runtoPosition(DcMotor John) {
        John.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        John.setTargetPosition(0);
        John.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        John.setPower(0);
    }
    public void untoPosition(DcMotor Neil) {
        Neil.setPower(0);
        Neil.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
    }
}
