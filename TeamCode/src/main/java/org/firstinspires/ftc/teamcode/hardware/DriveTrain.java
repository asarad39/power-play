package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Represents the four motors of the robot in code
public class DriveTrain {

    // The system contains for DC motors
    private DcMotor motorFR = null;
    private DcMotor motorBR = null;
    private DcMotor motorFL = null;
    private DcMotor motorBL = null;

    private int encoderOnBR = 0;
    private int encoderOnFR = 0;
    private int encoderOnBL = 0;
    private int encoderOnFL = 0;

    public void initialize(OpMode op) {

        // Links the code to the ports on the robot
        motorFR = op.hardwareMap.get(DcMotor.class, "motorFR");
        motorFL = op.hardwareMap.get(DcMotor.class, "motorFL");
        motorBR = op.hardwareMap.get(DcMotor.class, "motorBR");
        motorBL = op.hardwareMap.get(DcMotor.class, "motorBL");

        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setCoast() {

        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setPower(double powerFR, double powerFL, double powerBR, double powerBL) {
        motorFR.setPower(-powerFR);
        motorFL.setPower(powerFL);
        motorBR.setPower(-powerBR);
        motorBL.setPower(powerBL);

        encoderOnBR = motorBR.getCurrentPosition();
        encoderOnFR = motorFR.getCurrentPosition();
        encoderOnBL = motorBL.getCurrentPosition();
        encoderOnFL = motorFL.getCurrentPosition();
    }

    public int getEncoderBL() { return -encoderOnBR; }
    public int getEncoderFL() { return -encoderOnFR; }
    public int getEncoderBR() { return -encoderOnBL; }
    public int getEncoderFR() { return -encoderOnFL; }

}
