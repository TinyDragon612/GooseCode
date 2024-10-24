package org.firstinspires.ftc.teamcode.drivecode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.universalCode.universalOpMode;
import org.firstinspires.ftc.teamcode.universalCode.values;

@TeleOp(name="Driver Mode", group="Linear Opmode")
//@Disabled
public class DriverMode extends universalOpMode {

    boolean moveByPower = false;
    @Override
    public void runOpMode() {
        setup();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        wheels.setPower(1);

        setOpModeType(2);

        telemetry.update();

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flip

        // Wait for the game to start (driver presses PLAY)
        telemetry.update();
        waitForStart();
        slides.resetEncoders();
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");

            wheels.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);


            /* Allows for power and position based control (I know you hate triggers Evie, but they're helpful for testing.)
            In fact. the triggers should NOT be used, as it bypasses the state system (I'll fix this later, not hard, just tedious)
            While there are more states for the crane, some (like ALL_THE_WAY_BACK) only matter in auton, while others
            (like NUDGED) can't be set manually, and are controlled by internal systems.
            Because of how the crane system now works, this moves the arm automatically. No need for extra buttons!

            It ALSO has the hanging bypass for player 1.
             */
            if(gamepad1.dpad_up) {
                slides.setStatus(values.craneState.HANGING);
                moveByPower = false;
            }else if(gamepad2.cross){
                slides.setStatus(values.craneState.LOW_CHAMBER);
                moveByPower = false;
            }else if((gamepad2.circle && slides.getStatus() != values.craneState.NUDGED) || gamepad1.dpad_down){
                //We don't want the human player to be holding the button and force the robot to ram the claw into the wall.
                slides.setStatus(values.craneState.ON_GROUND);
                moveByPower = false;
            }else if(gamepad2.square){
                slides.setStatus(values.craneState.ON_WALL);
                moveByPower = false;
            }else if(gamepad2.triangle){
                slides.setStatus(values.craneState.HIGH_CHAMBER);
                moveByPower = false;
            }else if(gamepad2.dpad_up){
                slides.setStatus(values.craneState.HIGH_BASKET);
                moveByPower = false;
            }else if(gamepad2.dpad_down){
                slides.setStatus(values.craneState.LOW_BASKET);
                moveByPower = false;
            }else if(gamepad2.right_trigger > 0.1){
                slides.move(gamepad2.right_trigger, true);
                moveByPower = true;
            }else if(gamepad2.left_trigger > 0.1){
                slides.move(-gamepad2.left_trigger, true);
                moveByPower = true;
            }else if(moveByPower){
                slides.setPower(0);
            }

            if(gamepad2.left_bumper){
                clawServo.setPosition(values.clawClosed);
            }else if(gamepad2.right_bumper){
                clawServo.setPosition(values.clawOpen);
            }

            slides.craneMaintenance();


            telemetry.addData("Left Crane Motor Position", slides.getCurrentLeftPosition());
            telemetry.addData("Right Crane Motor Position", slides.getCurrentRightPosition());
            telemetry.addData("Trigger total:", gamepad2.right_trigger + gamepad2.left_trigger);
            telemetry.update();

        }
    }
}
