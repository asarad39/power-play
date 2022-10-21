package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class RobotHardware {

    private DriveTrain driveTrain = null;
    public Gamepad gamepad1 = null;

    public RobotHardware() {

        driveTrain = new DriveTrain();

    }

    public void initialize(OpMode op) {

        driveTrain.initialize(op);
        gamepad1 = op.gamepad1;
    }
    public void drive(double powerFR, double powerFL, double powerBR, double powerBL) {
        driveTrain.setPower(powerFR, powerFL, powerBR, powerBL);
    }

}
