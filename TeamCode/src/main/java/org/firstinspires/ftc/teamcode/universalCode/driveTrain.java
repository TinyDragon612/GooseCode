package org.firstinspires.ftc.teamcode.universalCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class driveTrain {
    public DcMotor topLeft;
    public DcMotor topRight;
    public DcMotor bottomLeft;
    public DcMotor bottomRight;

    public IMUInterface imu;
    public double targetHeading;

    private double fowardSpeed = 0.75;

    private double lastAngle;
    private double currAngle = 0;
    private double speed = 1;
    private double headingOffset = 0;

    private boolean auton = false;

    private final universalOpMode opMode;

    private final crane crane;

    public driveTrain(HardwareMap hardwareMap, universalOpMode opmode){
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        topLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        topRight = hardwareMap.get(DcMotor.class, "topRight");

        bottomLeft = hardwareMap.get(DcMotor.class, "bottomLeft");
        bottomLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        bottomRight = hardwareMap.get(DcMotor.class, "bottomRight");
        bottomRight.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new IMUInterface(hardwareMap);

        lastAngle = imu.getYaw();

        targetHeading = 0;

        opMode = opmode;

        crane = opMode.slides;
    }
    public void manualDrive(double frontLeftPower, double frontRightPower, double backLeftPower, double bottomRightPower){
        topLeft.setPower(frontLeftPower * speed);
        topRight.setPower(frontRightPower * speed);
        bottomLeft.setPower(backLeftPower * speed);
        bottomRight.setPower(bottomRightPower * speed);
    }

    public void setPower(double newPower){
        if(auton){
            topLeft.setPower(newPower);
            topRight.setPower(newPower);
            bottomLeft.setPower(newPower);
            bottomRight.setPower(newPower);
        }else{
            speed = newPower;
        }
    }

    public void setFowardSpeed(double newPower){
        fowardSpeed = newPower;
    }

    public void setMode(DcMotor.RunMode mode){
        topLeft.setMode(mode);
        topRight.setMode(mode);
        bottomLeft.setMode(mode);
        bottomRight.setMode(mode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        topLeft.setZeroPowerBehavior(behavior);
        topRight.setZeroPowerBehavior(behavior);
        bottomLeft.setZeroPowerBehavior(behavior);
        bottomRight.setZeroPowerBehavior(behavior);
    }

    public void setTargetPosition(int target){
        topLeft.setTargetPosition(target);
        topRight.setTargetPosition(target);
        bottomLeft.setTargetPosition(target);
        bottomRight.setTargetPosition(target);
    }

    //Auton Shtuff
    public void isAuton(){
        auton = true;
        this.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.setTargetPosition(0);
        this.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //changed from zeropowerbehavior.float
        //brake makes it brake on zero power, resisting any change
    }

    public void moveByEncoder(DcMotor mot, int target, double power){
        mot.setPower(0);
        mot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mot.setTargetPosition(0);
        mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mot.setPower(0);

        mot.setTargetPosition(target);
        mot.setPower(power);
    }

    public void foward(int target){
       resetAngle();
        targetHeading = 0 - headingOffset;
        waitForWheels(target, true);
    }

    private void continueFoward(){
        double leftPower = fowardSpeed - fowardSpeed * (Math.max(imu.getYaw(), 0) / 90);
        double rightPower = fowardSpeed - fowardSpeed * (Math.max(-imu.getYaw(), 0) / 90 );
        topLeft.setPower(leftPower);
        topRight.setPower(rightPower);
        bottomLeft.setPower(leftPower);
        bottomRight.setPower(rightPower);
    }

    public void side(int target){
        resetAngle();
        targetHeading = 0 - headingOffset;
        waitForWheels(target, false);
    }
    private void continueSide(){
        double sideSpeed = 0.5;
        double leftPower = sideSpeed - sideSpeed * (-imu.getYaw() / 90);
        double rightPower = sideSpeed - sideSpeed * (imu.getYaw() / 90);
        topLeft.setPower(leftPower);
        topRight.setPower(rightPower);
        bottomLeft.setPower(-leftPower);
        bottomRight.setPower(-rightPower);
    }

    public void rotate(int target) {
        resetAngle();
        setPower(0);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turnTo(-target);
        targetHeading = target;
        resetEncoders();
        opMode.sleep(100);

    }

    public void waitForWheels(int target, boolean foward) {
        if(foward){
            this.moveByEncoder(topLeft, target, 0);
            this.moveByEncoder(topRight, target, 0);
            this.moveByEncoder(bottomLeft, target, 0);
            this.moveByEncoder(bottomRight, target, 0);
        }else{
            this.moveByEncoder(topLeft, target, 0);
            this.moveByEncoder(topRight, -target, 0);
            this.moveByEncoder(bottomLeft, -target, 0);
            this.moveByEncoder(bottomRight, target, 0);
        }
        double margin = 10;
        while((topLeft.getCurrentPosition() > topLeft.getTargetPosition() + margin ||
                topRight.getCurrentPosition() > topRight.getTargetPosition() + margin ||
                bottomLeft.getCurrentPosition() > bottomLeft.getTargetPosition() + margin ||
                bottomRight.getCurrentPosition() > bottomRight.getTargetPosition() + margin ||
                topLeft.getCurrentPosition() < topLeft.getTargetPosition() - margin ||
                topRight.getCurrentPosition() < topRight.getTargetPosition() - margin ||
                bottomLeft.getCurrentPosition() < bottomLeft.getTargetPosition() - margin ||
                bottomRight.getCurrentPosition() < bottomRight.getTargetPosition() - margin) &&
                opMode.opModeIsActive()
        ) {
            crane.craneMaintenance();
            if(foward){
                continueFoward();
            }else{
                continueSide();
            }
            opMode.telemetry.addData("Yaw: ", imu.getYaw());
            opMode.telemetry.addData("frontLeft: ", topLeft.getCurrentPosition());
            opMode.telemetry.addData("frontRight: ", topRight.getCurrentPosition());
            opMode.telemetry.addData("backLeft: ", bottomLeft.getCurrentPosition());
            opMode.telemetry.addData("backRight: ", bottomRight.getCurrentPosition());

            opMode.telemetry.addData("left draw slide", crane.getCurrentLeftPosition());
            opMode.telemetry.addData("claw spin", crane.getCurrentArmPosition());

            opMode.telemetry.update();
            crane.craneMaintenance();
        }
        resetEncoders();
    }
    public void resetEncoders(){
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void turnTo(double degrees){
        resetAngle();
        double yaw = imu.getYaw();

        System.out.println(yaw);
        double error = degrees - yaw - headingOffset;
        boolean one80 = false;

        if (error > 180){
            one80 = true;
            error -= 90;
        }else if(error < -180) {
            one80 = true;
            error += 90;
        }

        turn(error, one80);
    }

    public void turn(double degrees, boolean one80){

        double error = degrees;

        while (opMode.opModeIsActive() && Math.abs(error) > 0.75) {
            crane.craneMaintenance();
            double motorPower = (error < 0 ? -0.5 : 0.5);
            motorPower *= Math.min(1, Math.abs(error / 20));
            if(Math.abs(motorPower) < 0.1){
                motorPower = (error < 0 ? -0.1: 0.1);
            }
            topLeft.setPower(-motorPower);
            topRight.setPower(motorPower);
            bottomLeft.setPower(-motorPower);
            bottomRight.setPower(motorPower);
            if(error < degrees - getAngle() + 0.025 && error > degrees - getAngle() - 0.025 && error < 10 && error > -10){
                break;
            }
            error = degrees - getAngle();
            if(one80 && Math.abs(error) < 80){
                error += (error < 0 ? -90 : 90);
                one80 = false;
            }
            opMode.telemetry.addData("error", error);
            opMode.telemetry.addData("raw yaw", imu.getYaw());
            opMode.telemetry.update();
            crane.craneMaintenance();
        }

        topLeft.setPower(0);
        topRight.setPower(0);
        bottomLeft.setPower(0);
        bottomRight.setPower(0);
    }

    public double getAngle() {

        // Get current orientation
        double yaw = imu.getYaw();
        // Change in angle = current angle - previous angle
        double deltaAngle = yaw - lastAngle;

        // Gyro only ranges from -179 to 180
        // If it turns -1 degree over from -179 to 180, subtract 360 from the 359 to get -1
        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        // Add change in angle to current angle to get current angle
        lastAngle = currAngle;
        currAngle += deltaAngle;
        opMode.telemetry.addData("gyro", yaw);
        return currAngle;
    }

    public void resetAngle() {
        headingOffset = currAngle;
        lastAngle = 0;
        currAngle = 0;
        imu.resetYaw();
    }
}
