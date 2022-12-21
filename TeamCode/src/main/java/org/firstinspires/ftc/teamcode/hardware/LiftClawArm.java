package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

// Represents the lift of the robot in code
public class LiftClawArm {

    // The system contains for DC motors
    private DcMotor liftMotor1 = null;
    private DcMotor liftMotor2 = null;

    private Servo liftArm = null;
    private Servo liftClaw = null;

    private TouchSensor touch = null;
    // private TouchSensor upperBound = null;

    private int liftEncoder1 = 0;
    private int liftEncoder2 = 0;
    private double liftArmEncoder = 0;
    private double liftClawEncoder = 0;

    public void initialize(OpMode op) {

        // Links the code to the ports on the robot
        liftMotor1 = op.hardwareMap.get(DcMotor.class, "liftMotor1");
        liftMotor2 = op.hardwareMap.get(DcMotor.class, "liftMotor2");

        liftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftMotor1.setTargetPosition(0);
        liftMotor2.setTargetPosition(0);
        resetEncoders();

        liftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);

        liftArm = op.hardwareMap.get(Servo.class, "liftArm");
        liftClaw = op.hardwareMap.get(Servo.class, "liftClaw");

        touch = op.hardwareMap.get(TouchSensor.class, "touch");
    }

    public void resetEncoders() {
        liftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setTarget(double target) {
        liftMotor1.setTargetPosition((int)target);
        liftMotor2.setTargetPosition((int)target);
    }

    public void setPower(double powerL) {
        //TODO: swap sign if going backwards
        liftMotor1.setPower(powerL);
        liftMotor2.setPower(powerL);

        liftEncoder1 = liftMotor1.getCurrentPosition();
        liftEncoder2 = liftMotor2.getCurrentPosition();
    }

    public void setLiftServos(double positionArmL, double positionClawL) {
        liftArm.setPosition(positionArmL);
        liftClaw.setPosition(positionClawL);
    }

    public void setLiftServoEncoders() {
        liftArmEncoder = liftArm.getPosition();
        liftClawEncoder = liftClaw.getPosition();
    }

    public int getLiftEncoder1() { return liftEncoder1; }
    public int getLiftEncoder2() { return liftEncoder2; }

    public double getLiftArmEncoder() { return liftArmEncoder; } // servo
    public double getLiftClawEncoder() { return liftClawEncoder; } // servo

    public boolean getTouch() { return touch.isPressed(); }

}
