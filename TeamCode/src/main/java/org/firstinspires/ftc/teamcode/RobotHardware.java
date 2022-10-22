package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

// Represents the hardware of our robot in code

public class RobotHardware {

    // Create objects for all of the hardware subsystems of the robot
    private DriveTrain driveTrain = null;
    public Gamepad gamepad1 = null;

    public RobotHardware() {

        driveTrain = new DriveTrain();

    }

    public void initialize(OpMode op) {

        driveTrain.initialize(op);
        gamepad1 = op.gamepad1;
    }

    // Drive in teleop
    public void drive(double powerFR, double powerFL, double powerBR, double powerBL) {
        driveTrain.setPower(powerFR, powerFL, powerBR, powerBL);
    }

}
