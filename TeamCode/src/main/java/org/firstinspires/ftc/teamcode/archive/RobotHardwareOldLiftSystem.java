package org.firstinspires.ftc.teamcode.archive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.LiftControl;
import org.firstinspires.ftc.teamcode.hardware.ServoControl;
import org.firstinspires.ftc.teamcode.hardware.TensorFlow;
import org.firstinspires.ftc.teamcode.roadrunnerPackages.drive.SampleMecanumDrive;

import java.util.List;

// Represents the hardware of our robot in code

public class RobotHardwareOldLiftSystem {

    // Create objects for all of the hardware subsystems of the robot
    public DriveTrain driveTrain = null;

    public NewLiftSystem lift = null;
    public LiftControl liftNew = null;

    private TensorFlow tensorFlow = null;
//    private ColorSensor colorSensor = null;
    private Pose2d currentPose = null;

    public SampleMecanumDrive sampleMec = null;

    public Gamepad gamepad1 = null;
    public Gamepad gamepad2 = null;
    public Telemetry telemetry;
    private static int sleeve = 0;
    private boolean debugSwitch = false;

    private ServoControl arm = null;
    private ServoControl flip = null;
    private ServoControl claw = null;
    private ServoControl rotate = null;
    private ServoControl tiny = null;

    public ElapsedTime time = new ElapsedTime();

    // If no pose is passed, in we start at (0, 0, 0) for roadrunner
    public RobotHardwareOldLiftSystem() {

        driveTrain = new DriveTrain();
//        lift = new NewLiftSystem();
        tensorFlow = new TensorFlow();


//        colorSensor = new ColorSensor();

        currentPose = new Pose2d();

        liftNew = new LiftControl();
    }

    // Overloaded ption for adding a starting pose when we create an autonomous program, if we want
    // to use the official coordinate system with (0, 0) in the center of the field
    public RobotHardwareOldLiftSystem(Pose2d currentPose) {

        driveTrain = new DriveTrain();
        lift = new NewLiftSystem();
        tensorFlow = new TensorFlow();
//        colorSensor = new ColorSensor();

        this.currentPose = currentPose;
        liftNew = new LiftControl();
    }

    public void initialize(OpMode op) {

        driveTrain.initialize(op);
        lift.initialize(op);
        tensorFlow.initialize(op);
//        liftNew.initialize(op,t);
//        colorSensor.initialize(op);

        gamepad1 = op.gamepad1;
        gamepad2 = op.gamepad2;
        telemetry = op.telemetry;
        sampleMec = new SampleMecanumDrive(op.hardwareMap);
    }

    public Telemetry getTelemetry() {
        return this.telemetry;
    }

    // Drive in teleop
    public void drive(double powerFR, double powerFL, double powerBR, double powerBL) {
        driveTrain.setPower(powerFR, powerFL, powerBR, powerBL);
    }

    //TODO
//     Lift system methods
    public void lift(double power) {

        if (lift.getGoHome() != 0) {

            lift.moveMotorsHome();

        } else {

            lift.setPower(power);

        }
    }

    public void setLiftTarget(double target) {

        if (lift.getGoHome() == 2) {

            lift.setTarget(300);

        } else if (lift.getGoHome() == 1) {

            lift.setTarget(-100);

        } else {

            lift.setTarget(target);

        }
    }

//    public void liftServos(double positionArmL, double positionClawL) {
//
//        lift.setLiftServos(positionArmL, positionClawL);
//        lift.setLiftServoEncoders();
//    }

    public void setServoPositions(double armPosition,
                                  double flipPosition,
                                  double rotatePosition,
                                  double clawPosition) {
// TODO
//        lift.setServosPositions(armPosition, flipPosition, rotatePosition, clawPosition);
//        lift.getServoPositions();
    }

    // --------------------- old lift below

//    public int getLiftEncoder1() {
//        return lift.getLiftEncoder1();
//    }
//
//    public int getLiftEncoder2() {
//        return lift.getLiftEncoder2();
//    }
//
//    public double getLiftArmEncoder() {
//        return lift.getLiftArmEncoder();
//    }
//
//    public double getLiftClawEncoder() {
//        return lift.getLiftClawEncoder();
//    }
//
//    public void resetLiftEncoders() {
//        lift.resetEncoders();
//    }
//
//    public boolean getTouch() {
//        return lift.getTouch();
//    }

    // -------------------- new lift below

//    TODO
    public int getLiftEncoderLeft() {
        return lift.getLiftEncoderLeft();
    }

    public int getLiftEncoderRight() {
        return lift.getLiftEncoderRight();
    }

    public double getArmPos() {
        return lift.getArmPos();
    }

    public double getFlipPos() {
        return lift.getFlipPos();
    }

    public double getRotatePos() {
        return lift.getRotatePos();
    }

    public double getClawPos() {
        return lift.getClawPos();
    }

    public boolean getTouch() {
        return lift.getTouch();
    }

    // ------------------------------

    // prepare tensorflow for recognizing the cone sleeve
    public List<Recognition> getRecognitions() throws Exception {
        return tensorFlow.getRecognitions();
    }

    public static void setSleeve(int sleeve) {
        RobotHardwareOldLiftSystem.sleeve = sleeve;
    }

    public static int getSleeve() {
        return RobotHardwareOldLiftSystem.sleeve;
    }

    public void setCustom(boolean c) {

        tensorFlow.setCustom(c);
    }

    // set roadrunner robot position
    public Pose2d getCurrentPose() {
        return currentPose;
    }

    public void setCurrentPose(Pose2d currentPose) {
        this.currentPose = currentPose;
    }

    // Color sensor values
//    public double getRed() {
//        return colorSensor.getRed();
//    }
//
//    public double getGreen() {
//        return colorSensor.getGreen();
//    }
//
//    public double getBlue() {
//        return colorSensor.getBlue();
//    }
//
//    public double getDistance() {
//        return colorSensor.getDistance();
//    }

    public int getEncoderBL() { return driveTrain.getEncoderBL(); }
    public int getEncoderFL() { return driveTrain.getEncoderFL(); }
    public int getEncoderBR() { return driveTrain.getEncoderBR(); }
    public int getEncoderFR() { return driveTrain.getEncoderFR(); }
}
