package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

// Represents the hardware of our robot in code

public class RobotHardware {

    // Create objects for all of the hardware subsystems of the robot
    private DriveTrain driveTrain = null;
    private Lift lift = null;
    public TensorFlow tensorFlow = null;

    public Gamepad gamepad1 = null;
    public Gamepad gamepad2 = null;
    public Telemetry telemetry;
    private static int sleeve = 0;

    public ElapsedTime time = new ElapsedTime();

    public RobotHardware() {

        driveTrain = new DriveTrain();
        lift = new Lift();
        tensorFlow = new TensorFlow();

    }

    public void initialize(OpMode op) {

        driveTrain.initialize(op);
        lift.initialize(op);
        tensorFlow.initialize(op);

        gamepad1 = op.gamepad1;
        gamepad2 = op.gamepad2;
        telemetry = op.telemetry;
    }

    // Drive in teleop
    public void drive(double powerFR, double powerFL, double powerBR, double powerBL) {
        driveTrain.setPower(powerFR, powerFL, powerBR, powerBL);
    }

    // Lift in teleop
    public void lift(double powerL) {
        lift.setPower(powerL);
    }
    public void liftTarget(double target) {
        lift.setTarget(target);
    }

    public void liftServos(double positionArmL, double positionClawL) {
        lift.setLiftServos(positionArmL, positionClawL);
        lift.setLiftServoEncoders();
    }

    public Telemetry getTelemetry() {
        return this.telemetry;
    }

    // Drive returns
    public int getLeftDriveEncoder() {
        return driveTrain.getLeftEncoder();
    }
    public int getRightDriveEncoder() {
        return driveTrain.getRightEncoder();
    }
    public int getBackDriveEncoder() {
        return driveTrain.getBackEncoder();
    }

    // Lift returns
    // lift height
    public int getLiftEncoder1() {
        return lift.getLiftEncoder1();
    }
    public int getLiftEncoder2() {
        return lift.getLiftEncoder2();
    }

    // lift arm position
    public double getLiftArmEncoder() {
        return lift.getLiftArmEncoder();
    }
    public double getLiftClawEncoder() {
        return lift.getLiftClawEncoder();
    }

    // lift reset encoders
    public void resetLiftEncoders() {
        lift.resetEncoders();
    }

    // lift claw position
    public boolean getTouch() { return lift.getTouch(); }

    public List<Recognition> getRecognitions() throws Exception {
        return tensorFlow.getRecognitions();
    }

    public static void setSleeve(int sleeve) {
        RobotHardware.sleeve = sleeve;
    }
    public static int getSleeve() { return RobotHardware.sleeve; }
    public boolean getCustom() { return tensorFlow.getCustom(); }
}
