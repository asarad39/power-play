package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.roadrunnerPackages.drive.SampleMecanumDrive;

import java.util.List;

// Represents the hardware of our robot in code

public class RobotHardware {

    // Create objects for all of the hardware subsystems of the robot
    private DriveTrain driveTrain = null;

    private LiftClawArm lift = null;
//    private NewLiftSystem lift = null;

    private TensorFlow tensorFlow = null;
    private ColorSensor colorSensor = null;
    private Pose2d currentPose = null;

    public SampleMecanumDrive sampleMec = null;

    public Gamepad gamepad1 = null;
    public Gamepad gamepad2 = null;
    public Telemetry telemetry;
    private static int sleeve = 0;
    private boolean debugSwitch = false;

    public ElapsedTime time = new ElapsedTime();

    // If no pose is passed, in we start at (0, 0, 0) for roadrunner
    public RobotHardware() {

        driveTrain = new DriveTrain();
        lift = new LiftClawArm();
        tensorFlow = new TensorFlow();
        colorSensor = new ColorSensor();

        currentPose = new Pose2d();
//        lift = new NewLiftSystem();
    }

    // Overloaded ption for adding a starting pose when we create an autonomous program, if we want
    // to use the official coordinate system with (0, 0) in the center of the field
    public RobotHardware(Pose2d currentPose) {

        driveTrain = new DriveTrain();
        lift = new LiftClawArm();
        tensorFlow = new TensorFlow();
        colorSensor = new ColorSensor();

        this.currentPose = currentPose;
//        lift = new NewLiftSystem();
    }

    public void initialize(OpMode op) {

        driveTrain.initialize(op);
        lift.initialize(op);
        tensorFlow.initialize(op);
        colorSensor.initialize(op);

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

    // Lift system methods
    public void lift(double power) {

        if (lift.getGoHome()) {

            lift.moveMotorsHome();

        } else {

            lift.setPower(power);

        }
    }

    public void setLiftTarget(double target) {
        lift.setTarget(target);
    }

    public void liftServos(double positionArmL, double positionClawL) {

        lift.setLiftServos(positionArmL, positionClawL);
        lift.setLiftServoEncoders();
    }

//    public void setServoPositions(double armPosition,
//                                  double flipPosition,
//                                  double rotatePosition,
//                                  double clawPosition) {
//
//        lift.setServosPositions(armPosition, flipPosition, rotatePosition, clawPosition);
//        lift.getServoPositions();
//    }

    // --------------------- old lift below

    public int getLiftEncoder1() {
        return lift.getLiftEncoder1();
    }

    public int getLiftEncoder2() {
        return lift.getLiftEncoder2();
    }

    public double getLiftArmEncoder() {
        return lift.getLiftArmEncoder();
    }

    public double getLiftClawEncoder() {
        return lift.getLiftClawEncoder();
    }

    public void resetLiftEncoders() {
        lift.resetEncoders();
    }

    public boolean getTouch() {
        return lift.getTouch();
    }

    // -------------------- new lift below

//    public int getLiftEncoderLeft() {
//        return lift.getLiftEncoderLeft();
//    }
//
//    public int getLiftEncoderRight() {
//        return lift.getLiftEncoderRight();
//    }
//
//    public double getArmPos() {
//        return lift.getArmPos();
//    }
//
//    public double getFlipPos() {
//        return lift.getFlipPos();
//    }
//
//    public double getRotatePos() {
//        return lift.getRotatePos();
//    }
//
//    public double getClawPos() {
//        return lift.getClawPos();
//    }
//
//    public void resetLiftEncoders() {
//        lift.resetEncoders();
//    }
//
//    public boolean getTouch() {
//        return lift.getTouch();
//    }

    // ------------------------------

    // prepare tensorflow for recognizing the cone sleeve
    public List<Recognition> getRecognitions() throws Exception {
        return tensorFlow.getRecognitions();
    }

    public static void setSleeve(int sleeve) {
        RobotHardware.sleeve = sleeve;
    }

    public static int getSleeve() {
        return RobotHardware.sleeve;
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
    public double getRed() {
        return colorSensor.getRed();
    }

    public double getGreen() {
        return colorSensor.getGreen();
    }

    public double getBlue() {
        return colorSensor.getBlue();
    }

    public double getDistance() {
        return colorSensor.getDistance();
    }

    public int getLeftEncoder() { return driveTrain.getLeftEncoder(); }
    public int getRightEncoder() { return driveTrain.getRightEncoder(); }
    public int getBackEncoder() { return driveTrain.getBackEncoder(); }
}
