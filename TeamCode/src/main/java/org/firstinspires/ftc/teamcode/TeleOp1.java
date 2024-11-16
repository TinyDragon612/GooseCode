package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="TeleOp1", group="Linear Opmode")
@Disabled
public class TeleOp1 extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor.ZeroPowerBehavior brake = DcMotor.ZeroPowerBehavior.BRAKE;
    private DcMotor.ZeroPowerBehavior floatt = DcMotor.ZeroPowerBehavior.FLOAT;

    private DcMotor topRight, bottomRight, topLeft, bottomLeft;
    private DcMotorEx slide1, slide2, arm;

    private int errorBound = 60;

    private Servo claw;

    boolean rotated;

    public enum State {
        DRAWER_START,
        DRAWER_FLIP_IN,
        DRAWER_FLIP_OUT,
        DRAWER_RETRACT,
        DRAWER_SETTLE

    }

    ;

    State drawerState = State.DRAWER_START;
    boolean time;

    int height;

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

        slide1 = hardwareMap.get(DcMotorEx.class, "slide1");
        slide2 = hardwareMap.get(DcMotorEx.class, "slide2");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        slide1.setDirection(DcMotor.Direction.REVERSE);

        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide1.setTargetPosition(0);
        slide2.setTargetPosition(0);
        arm.setTargetPosition(0);

        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        claw = hardwareMap.get(Servo.class, "claw");

        telemetry.update();

        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("slide1 ", slide1.getCurrentPosition());
            telemetry.addData("slide2: ", slide2.getCurrentPosition());
            telemetry.addData("arm: ", arm.getCurrentPosition());
            telemetry.update();

            switch (drawerState) {
                case DRAWER_START:
                    if (gamepad2.triangle) {
                        setDrawerHeight(2800);
                        drawerState = State.DRAWER_FLIP_OUT;
                    } else if (gamepad2.circle) {
                        setDrawerHeight(1000);
                        drawerState = State.DRAWER_FLIP_OUT;
                    } else if (gamepad2.cross) {
                        setDrawerHeight(600);
                        drawerState = State.DRAWER_FLIP_OUT;
                    }
                    break;
                case DRAWER_FLIP_OUT:
                    if (waitforDrawers(slide1, slide2)) {
                        movevertically(arm, 200, 1);
                        drawerState = State.DRAWER_FLIP_IN;
                    }
                    break;
                case DRAWER_FLIP_IN:
                    if (gamepad2.square) {
                        movevertically(arm, 1000, 1);

                        drawerState = State.DRAWER_RETRACT;
                    }
                    break;
                case DRAWER_RETRACT:
                    if (time) {
                        bringDrawersDown();

                        drawerState = State.DRAWER_SETTLE;
                    }
                    break;
                case DRAWER_SETTLE:
                    if ((time && waitforDrawers(slide1, slide2))) {
                        untoPosition(slide1);
                        untoPosition(slide2);

                        drawerState = State.DRAWER_START;
                    }
                    break;
                default:
                    drawerState = State.DRAWER_START;
            }

            //MORE GAMEPAD2 CONTROLS

            if (gamepad1.right_bumper) {
                claw.setPosition(0);
            } else if (gamepad1.left_bumper) {
                claw.setPosition(0.12);
            }

            //GAMEPAD1 CONTROLS

            // Send power to wheels
            topRight.setPower(((-gamepad1.left_stick_y + -gamepad1.left_stick_x)) + (-gamepad1.right_stick_x));
            topLeft.setPower(((-gamepad1.left_stick_y + gamepad1.left_stick_x)) + (gamepad1.right_stick_x));
            bottomRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x) + (-gamepad1.right_stick_x));
            bottomLeft.setPower(((-gamepad1.left_stick_y + -gamepad1.left_stick_x)) + (gamepad1.right_stick_x));

            //hanging stuff

            if (gamepad1.dpad_right) {
                movevertically(arm, 1000,1);
            }

            if (gamepad1.dpad_up) {
                setDrawerHeight(1000);
            } else if (gamepad1.dpad_down) {
                setDrawerHeight(0);
            }
        }
    }

        public void bringDrawersDown () {
            movevertically(slide1, -height - 50, 1);
            movevertically(slide2, -height - 50, 1);
        }

        public void setDrawerHeight ( int h){
            height = h;
            movevertically(slide1, h, 1);
            movevertically(slide2, h, 1);
        }

        public void waitforDrawer (DcMotor george){
            while (!(george.getCurrentPosition() > george.getTargetPosition() - errorBound && george.getCurrentPosition() < george.getTargetPosition() + errorBound))
                ;
        }

        public boolean waitforDrawers (DcMotor george, DcMotor BobbyLocks){
            return ((george.getCurrentPosition() > george.getTargetPosition() - errorBound && george.getCurrentPosition() < george.getTargetPosition() + errorBound) &&
                    (BobbyLocks.getCurrentPosition() > BobbyLocks.getTargetPosition() - errorBound && BobbyLocks.getCurrentPosition() < BobbyLocks.getTargetPosition() + errorBound));
        }

        public void movevertically (DcMotorEx lipsey,int position, double power){
            untoPosition(lipsey);
            runtoPosition(lipsey);
            lipsey.setTargetPosition(lipsey.getCurrentPosition() + position);
            lipsey.setPower(power);

        }

        public void nostall (DcMotorEx Harry){
            Harry.setZeroPowerBehavior(floatt);
            Harry.setPower(0);
        }

        public void stall (DcMotorEx DcMotar){
            DcMotar.setZeroPowerBehavior(brake);
            DcMotar.setPower(0);
        }

        public void runtoPosition (DcMotorEx John){
            John.setTargetPosition(0);
            John.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            John.setPower(0);
        }
        public void untoPosition (DcMotorEx Neil){
            Neil.setPower(0);
            Neil.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        }

}
