package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

// Represents the lift of the robot in code
public class NewLiftSystem {

    // The system contains for DC motors
    private DcMotor liftMotorLeft = null;
    private DcMotor liftMotorRight = null;

    private TouchSensor touch = null;
    // private TouchSensor upperBound = null;

    private int liftEncoderLeft = 0;
    private int liftEncoderRight = 0;

    private Servo armServo = null;
    private Servo flipServo = null;
    private Servo rotateServo = null;
    private Servo clawServo = null;

    private double armPos = 0;
    private double flipPos = 0;
    private double rotatePos = 0;
    private double clawPos = 0;

    private boolean goHome =  true;

    public void initialize(OpMode op) {

        // Links the code to the ports on the robot
        liftMotorLeft = op.hardwareMap.get(DcMotor.class, "liftMotorLeft");
        liftMotorRight = op.hardwareMap.get(DcMotor.class, "liftMotorRight");

        liftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftMotorLeft.setTargetPosition(0);
        liftMotorRight.setTargetPosition(0);
        resetEncoders();

        liftMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        touch = op.hardwareMap.get(TouchSensor.class, "touch");

        armServo = op.hardwareMap.get(Servo.class, "arm");
        flipServo = op.hardwareMap.get(Servo.class, "flip");
        rotateServo = op.hardwareMap.get(Servo.class, "rotate");
        clawServo = op.hardwareMap.get(Servo.class, "claw");
    }

    public void resetEncoders() {

        liftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setTarget(double target) {

        liftMotorLeft.setTargetPosition((int)target);
        liftMotorRight.setTargetPosition((int)target);
    }

    public void setPower(double power) {
        //TODO: swap sign if going backwards
        liftMotorLeft.setPower(power);
        liftMotorRight.setPower(power);

        liftEncoderLeft = liftMotorLeft.getCurrentPosition();
        liftEncoderRight = liftMotorRight.getCurrentPosition();
    }

    public void moveMotorsHome() {

        liftMotorLeft.setPower(-0.3);
        liftMotorRight.setPower(-0.3);

        if (touch.isPressed()) {

            goHome = false;
            liftMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

    }

    public boolean getGoHome() {

        return this.goHome;
    }

    public int getLiftEncoderLeft() {
        return liftEncoderLeft;
    }

    public int getLiftEncoderRight() {
        return liftEncoderRight;
    }

    public boolean getTouch() {
        return touch.isPressed();
    }

    public void setServosPositions(double armPosition,
                                   double flipPosition,
                                   double rotatePosition,
                                   double clawPosition) {

        armServo.setPosition(armPosition);
        flipServo.setPosition(flipPosition);
        rotateServo.setPosition(rotatePosition);
        clawServo.setPosition(clawPosition);
    }

    public void getServoPositions() {

        armPos = armServo.getPosition();
        flipPos = flipServo.getPosition();
        rotatePos = rotateServo.getPosition();
        clawPos = clawServo.getPosition();
    }

    public double getArmPos() {
        return armPos;
    }

    public double getFlipPos() {
        return flipPos;
    }

    public double getRotatePos() {
        return rotatePos;
    }


    public double getClawPos() {
        return clawPos;
    }
}
