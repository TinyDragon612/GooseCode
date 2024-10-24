package org.firstinspires.ftc.teamcode.universalCode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

public abstract class universalOpMode extends LinearOpMode {

    public crane slides;
    public driveTrain wheels;
    public Servo clawServo;
    public DistanceSensor proxy;


    public void setup(){
        setup(0.5);
    }
    public void setup(double cranePower){
        slides = new crane(hardwareMap, cranePower, false, values.craneState.ON_GROUND, proxy);
        wheels = new driveTrain(hardwareMap, this);
        clawServo = hardwareMap.get(Servo.class, "claw");
        proxy = hardwareMap.get(DistanceSensor.class, "proxy");
    }

    //0 is Front Auton, 1 is Back Auton, any other value is TeleOp
    //(Though with the new placement system, TeleOp isn't REALLY applicable for placePixel)
    private int opModeType = 2;
    public void setOpModeType(int type){
        opModeType = type;
    }

    public void foward(int distance){
        wheels.foward(distance);
    }

    public void side(int distance){
        wheels.side(distance);
    }

}
