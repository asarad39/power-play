package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// Represents the four motors of the robot in code
public class DriveTrain {

    // The system contains for DC motors
    private DcMotor motorFR = null;
    private DcMotor motorBR = null;
    private DcMotor motorFL = null;
    private DcMotor motorBL = null;

    private int leftEncoder = 0;
    private int rightEncoder = 0;
    private int backEncoder = 0;

    public void initialize(OpMode op) {

        // Links the code to the ports on the robot
        motorFR = op.hardwareMap.get(DcMotor.class, "motorFR");
        motorFL = op.hardwareMap.get(DcMotor.class, "motorFL");
        motorBR = op.hardwareMap.get(DcMotor.class, "motorBR");
        motorBL = op.hardwareMap.get(DcMotor.class, "motorBL");
    }

    public void setPower(double powerFR, double powerFL, double powerBR, double powerBL) {
        motorFR.setPower(powerFR);
        motorFL.setPower(powerFL);
        motorBR.setPower(powerBR);
        motorBL.setPower(powerBL);

        leftEncoder = motorBR.getCurrentPosition();
        rightEncoder = motorFR.getCurrentPosition();
        backEncoder = motorFL.getCurrentPosition();
    }

    public int getLeftEncoder() { return leftEncoder; }
    public int getRightEncoder() { return rightEncoder; }
    public int getBackEncoder() { return backEncoder; }

}
