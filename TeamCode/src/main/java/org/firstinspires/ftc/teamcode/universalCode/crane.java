package org.firstinspires.ftc.teamcode.universalCode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class crane {
    private DcMotor leftDrawerSlide, rightDrawerSlide, arm;
    private int targetPosition;
    private double power = 0;
    private DistanceSensor proxy;

    private values.craneState status;

    public crane(HardwareMap hardwareMap, double power, boolean craneByPower, values.craneState startingState, DistanceSensor distSense){
        leftDrawerSlide = hardwareMap.get(DcMotor.class, "slide1");
        leftDrawerSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrawerSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightDrawerSlide = hardwareMap.get(DcMotor.class, "slide2");
        rightDrawerSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetEncoders();

        status = startingState;
        arm = hardwareMap.get(DcMotor.class, "arm");
        resetArm();
        arm.setTargetPosition(0);
        arm.setPower(0.6);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        targetPosition = 0;

        setPower(power);
        this.power = power;
        setTargetPosition(0);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //This gets cloned into here because the crane uses it a LOT. It's in uniOp in case we want it to trigger something else later.
        proxy = distSense;
    }

    public void setTargetPosition(int target){
        setTargetPosition(target, 0.8);
    }

    public void setTargetPosition(int target, double power){
        setPower(power);
        leftDrawerSlide.setTargetPosition(target);
        rightDrawerSlide.setTargetPosition(target);
        targetPosition = target;
    }

    public void resetEncoders(){
        leftDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setTargetPosition(0);
        setPower(power);

        leftDrawerSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrawerSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void resetArm(){
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setArmPosition(int target){
        arm.setTargetPosition(target);
    }

    public void craneMaintenance(){
        if(offCheck() && targetPosition == 0) {
            resetEncoders();
        }
        arm.setTargetPosition(values.getArmTargetPosition(status));
        setTargetPosition(values.getSlidesTargetPosition(status));
        //Insert distance sensors nudged bs here
    }

    public void move(double movement, boolean byPower){
        if(movement > 0 && byPower){
            setTargetPosition(values.slidesMaximum, -movement);
        }else if(movement < 0 && byPower){
            setTargetPosition(0, movement);
        }else if(byPower){
            setPower(0);
        }else{
            setTargetPosition((int)movement);
        }
    }
    public void setPower(double power){
        leftDrawerSlide.setPower(power);
        rightDrawerSlide.setPower(power);
    }

    public int getCurrentLeftPosition() { return leftDrawerSlide.getCurrentPosition(); }

    public int getCurrentRightPosition() { return rightDrawerSlide.getCurrentPosition(); }
    public int getCurrentArmPosition() { return arm.getCurrentPosition(); }

    public boolean offCheck(){ return getCurrentLeftPosition() < 0 || getCurrentRightPosition() < 0; }
    public void setMode(DcMotor.RunMode mode){
        leftDrawerSlide.setMode(mode);
        rightDrawerSlide.setMode(mode);
    }

    public void setStatus(values.craneState newState){
        status = newState;
    }
    public values.craneState getStatus(){
        return status;
    }
}