package org.firstinspires.ftc.teamcode.universalCode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class crane {
    private DcMotorEx leftDrawerSlide, rightDrawerSlide, arm;
    private int targetPosition;
    private double power = 0;
    public boolean manualControl = false;
    private DistanceSensor proxy;

    private values.craneState status;

    public crane(HardwareMap hardwareMap, double power, boolean craneByPower, values.craneState startingState, DistanceSensor distSense){
        leftDrawerSlide = hardwareMap.get(DcMotorEx.class, "slide1");
        leftDrawerSlide.setDirection(DcMotorEx.Direction.REVERSE);
        leftDrawerSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightDrawerSlide = hardwareMap.get(DcMotorEx.class, "slide2");
        rightDrawerSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        resetEncoders();

        status = startingState;
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        arm.setTargetPosition(0);
        arm.setPower(0.15);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotorEx.Direction.REVERSE);
        targetPosition = 0;

        setPower(power);
        this.power = power;
        setTargetPosition(0);
        setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

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
        leftDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        setTargetPosition(0);
        setPower(power);

        leftDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public void resetArm(){
        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(0);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public void setArmPosition(int target){
        arm.setTargetPosition(target);
    }

    public void craneMaintenance(){
        if(leftDrawerSlide.getCurrentPosition() < 10 && leftDrawerSlide.getCurrentAlert(CurrentUnit.AMPS) > 0.5 && leftDrawerSlide.getTargetPosition() == 0){
            leftDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            leftDrawerSlide.setTargetPosition(0);
            leftDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            leftDrawerSlide.setPower(0);

        }
        if(rightDrawerSlide.getCurrentPosition() < 10 && rightDrawerSlide.getCurrentAlert(CurrentUnit.AMPS) > 0.5 && leftDrawerSlide.getTargetPosition() == 0){
            rightDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            rightDrawerSlide.setPower(0);
        }
        if(manualControl){
            return;
        }
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        arm.setTargetPosition(values.getArmTargetPosition(status));
        setTargetPosition(values.getSlidesTargetPosition(status));
        //Insert distance sensors nudged bs here
    }

    public void move(double movement, boolean byPower){
        if(byPower){
            manualControl = true;
        }
        if(movement > 0 && byPower){
            setTargetPosition(2150, -movement);
        }else if(movement < 0 && byPower){
            setTargetPosition(0, movement);
        }else if(byPower){
            setPower(0);
        }else{
            manualControl = false;
            setTargetPosition((int)movement);
        }
    }
    public void setPower(double power){
        leftDrawerSlide.setPower(power);
        rightDrawerSlide.setPower(power);
    }

    public int getCurrentSlidesPosition() { return (leftDrawerSlide.getCurrentPosition() + rightDrawerSlide.getCurrentPosition())/2; }
    public int getCurrentLeftPosition(){ return leftDrawerSlide.getCurrentPosition();}
    public int getCurrentRightPosition(){ return rightDrawerSlide.getCurrentPosition();}

    public int getCurrentArmPosition() { return arm.getCurrentPosition(); }

    public double getAmpsLeft(){return leftDrawerSlide.getCurrent(CurrentUnit.AMPS);}

    public double getAmpsRight(){return rightDrawerSlide.getCurrent(CurrentUnit.AMPS);}

    public void setMode(DcMotorEx.RunMode mode){
        leftDrawerSlide.setMode(mode);
        rightDrawerSlide.setMode(mode);
    }

    public void setStatus(values.craneState newState){
        status = newState;
    }
    public values.craneState getStatus(){
        return status;
    }

    public void setArmManual(double power){
        if(power > 0){
            arm.setTargetPosition(700);
        }else if(power < 0){
            arm.setTargetPosition(0);
        }else{
            arm.setTargetPosition(arm.getCurrentPosition());
            arm.setPower(0.25);
            return;
        }
        arm.setPower(power);
    }

    public boolean waitForArm(){
        return arm.getCurrentPosition() < arm.getTargetPosition() + 60 &&
                arm.getCurrentPosition() > arm.getTargetPosition() - 60;
    }
    public boolean waitForSlides(){
        return getCurrentSlidesPosition() < targetPosition + 60 &&
                getCurrentSlidesPosition() > targetPosition -60;
    }

    public void setArmPower(double power){
        arm.setPower(power);    }
    public double getArmPower(){
        return arm.getPower();    }
}