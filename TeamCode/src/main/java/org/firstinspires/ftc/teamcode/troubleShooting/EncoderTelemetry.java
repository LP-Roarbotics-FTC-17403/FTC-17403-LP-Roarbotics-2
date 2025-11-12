package org.firstinspires.ftc.teamcode.troubleShooting;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DecodeClasses.CameraSystem;
import org.firstinspires.ftc.teamcode.DecodeClasses.ColorSensorCode;
import org.firstinspires.ftc.teamcode.DecodeClasses.DriveMotorTest;
import org.firstinspires.ftc.teamcode.DecodeClasses.Feeder;
import org.firstinspires.ftc.teamcode.DecodeClasses.Firecracker;
import org.firstinspires.ftc.teamcode.DecodeClasses.Hammer;
import org.firstinspires.ftc.teamcode.DecodeClasses.Inhaler;
import org.firstinspires.ftc.teamcode.DecodeClasses.LED;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.ArrayList;

@TeleOp
public final class EncoderTelemetry extends LinearOpMode {

    private Pose2d beginPose;
    private MecanumDrive drive;
    private CameraSystem camera;
    private Firecracker rightFirecracker;
    private Firecracker leftFirecracker;
    private Inhaler inhaler;
    private Feeder leftFeeder;
    private Feeder rightFeeder;
    private LED leftLight;
    private LED middleLight;
    private LED rightLight;
    private ColorSensorCode leftColor;
    private ColorSensorCode rightColor;
    private DriveMotorTest vroom;

    private Hammer hammer;

    final double FEED_TIME_SECONDS = 0.80; //The feeder servos run this long when a shot is requested.
    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;

    final double LAUNCHER_CLOSE_TARGET_VELOCITY = 1200; //in ticks/second for the close goal.
    final double LAUNCHER_CLOSE_MIN_VELOCITY = 1175; //minimum required to start a shot for close goal.

    final double LAUNCHER_FAR_TARGET_VELOCITY = 1350; //Target velocity for far goal
    final double LAUNCHER_FAR_MIN_VELOCITY = 1325; //minimum required to start a shot for far goal.

    double launcherTarget = LAUNCHER_CLOSE_TARGET_VELOCITY; //These variables allow
    double launcherMin = LAUNCHER_CLOSE_MIN_VELOCITY;

    final double LEFT_POSITION = 0.2962; //the left and right position for the diverter servo
    final double RIGHT_POSITION = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        beginPose                = new Pose2d(0, 0, 0);
        drive              = new MecanumDrive(hardwareMap, beginPose);
        camera             = new CameraSystem(hardwareMap);
        rightFirecracker    =  new Firecracker(hardwareMap, "right_launcher");
        leftFirecracker     =  new Firecracker(hardwareMap, "left_launcher");
        inhaler                 = new Inhaler(hardwareMap, "intake");
        leftFeeder               = new Feeder(hardwareMap, "left_feeder");
        rightFeeder              = new Feeder(hardwareMap, "right_feeder");
        leftLight                   = new LED(hardwareMap, "left_light");
        middleLight                 = new LED(hardwareMap, "middle_light");
        rightLight                  = new LED(hardwareMap, "right_light");
        leftColor       = new ColorSensorCode(hardwareMap, "left_color_sensor");
        rightColor      = new ColorSensorCode(hardwareMap, "right_color_sensor");
        vroom            = new DriveMotorTest(hardwareMap);

        ArrayList<DcMotorEx> dcMotorList = new ArrayList<DcMotorEx>();
        ArrayList<CRServo> crServoList = new ArrayList<CRServo>();
        ArrayList<Servo> servoList = new ArrayList<Servo>();



        boolean reverse = true;
        boolean notReverse = false;
        int detectedID;

        //adjust reverse if needed
        leftFeeder.initialize(reverse);
        rightFeeder.initialize(notReverse);
        leftFirecracker.initialize(notReverse);
        rightFirecracker.initialize(reverse);
        inhaler.initialize(reverse);
        vroom.initialize(true);
        //camera.cameraOn();


        waitForStart();

        while(opModeIsActive()) {
            //encoder values for motors
            for(int i = 0; i<dcMotorList.size(); i ++){
                telemetry.addData("Motor" + (i+1) + "encoder ticks: ",dcMotorList.get(i).getCurrentPosition());
                telemetry.update();
            }

            for(int i = 0; i<crServoList.size(); i++){
                telemetry.addData("crServo" + (i+1) + "power: ", crServoList.get(i).getPower());
                telemetry.update();
            }

            for(int i = 0; i<servoList.size(); i++){
                telemetry.addData("Servo " + (i+1) + " position: ", servoList.get(i).getPosition());
            }


        }
    }

}

