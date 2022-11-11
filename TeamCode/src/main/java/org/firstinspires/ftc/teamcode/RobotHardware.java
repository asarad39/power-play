package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

// Represents the hardware of our robot in code

public class RobotHardware {

    // Create objects for all of the hardware subsystems of the robot
    private DriveTrain driveTrain = null;
    private Lift lift = null;
    public Gamepad gamepad1 = null;

    public RobotHardware() {

        driveTrain = new DriveTrain();
        lift = new Lift();

    }

    public void initialize(OpMode op) {

        driveTrain.initialize(op);
        lift.initialize(op);
        gamepad1 = op.gamepad1;
    }

    // Drive in teleop
    public void drive(double powerFR, double powerFL, double powerBR, double powerBL) {
        driveTrain.setPower(powerFR, powerFL, powerBR, powerBL);
    }

    public void lift(double powerL, double positionArmL, double positionClawL) {
        lift.setPower(powerL, positionArmL, positionClawL);
    }

    public int getLeftDriveEncoder() {
        return driveTrain.getLeftEncoder();
    }
    public int getRightDriveEncoder() {
        return driveTrain.getRightEncoder();
    }
    public int getBackDriveEncoder() {
        return driveTrain.getBackEncoder();
    }


    public int getLift1Encoder() {
        return lift.getLiftEncoder1();
    }
    public int getLift2Encoder() {
        return lift.getLiftEncoder2();
    }

    public double getArmEncoder() {
        return lift.getLiftArmEncoder();
    }
    public double getClawEncoder() {
        return lift.getLiftClawEncoder();
    }

    public boolean getLiftUpperBound() { return lift.getUpperBound(); }
    public boolean getLiftLowerBound() { return lift.getLowerBound(); }
}
