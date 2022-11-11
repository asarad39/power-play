package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

// Represents the four motors of the robot in code
public class Lift {

    // The system contains for DC motors
    private DcMotor liftMotor1 = null;
    private DcMotor liftMotor2 = null;

    private Servo liftArm = null;
    private Servo liftClaw = null;

    private TouchSensor lowerBound = null;
    private TouchSensor upperBound = null;

    private int liftEncoder1 = 0;
    private int liftEncoder2 = 0;
    private double liftArmEncoder = 0;
    private double liftClawEncoder = 0;

    public void initialize(OpMode op) {

        // Links the code to the ports on the robot
        liftMotor1 = op.hardwareMap.get(DcMotor.class, "liftMotor1");
        liftMotor2 = op.hardwareMap.get(DcMotor.class, "liftMotor2");
    }

    public void setPower(double powerL, double positionArmL, double positionClawL) {
        liftMotor1.setPower(powerL);
        liftMotor2.setPower(-powerL);

        liftArm.setPosition(positionArmL);
        liftClaw.setPosition(positionClawL);

        liftEncoder1 = liftMotor1.getCurrentPosition();
        liftEncoder2 = liftMotor2.getCurrentPosition();

        liftArmEncoder = liftArm.getPosition();
        liftClawEncoder = liftClaw.getPosition();
    }

    public int getLiftEncoder1() { return liftEncoder1; }
    public int getLiftEncoder2() { return liftEncoder2; }

    public double getLiftArmEncoder() { return liftArmEncoder; }
    public double getLiftClawEncoder() { return liftClawEncoder; }

    public boolean getUpperBound() { return upperBound.isPressed(); }
    public boolean getLowerBound() { return lowerBound.isPressed(); }

}
