package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;
// Represents the lift of the robot in code

public class ServoControl {

    Servo servo;
    String servoName;

    private final double maxStepSize = 0.007;

    private double currentPosition = 0;
    private double targetPosition = 0;
    private double stepSize;


    private Telemetry telemetry;


    public ServoControl(String servoName, double stepSize) {
        this.servoName = servoName;
        this.stepSize = stepSize;
    }

    public ServoControl(String servoName) {
        this.servoName = servoName;
        this.stepSize = maxStepSize;
    }

    public void initialize(OpMode op) {
//        this.rh = rh;
        this.telemetry = op.telemetry;

        this.servo = op.hardwareMap.get(Servo.class, servoName);

        if (stepSize > maxStepSize) {
            this.stepSize = maxStepSize;
        } else if (stepSize < 0) {
            this.stepSize = 0;
        } else {
            this.stepSize = stepSize;
        }
    }

    public void move() {
        if (Math.abs(currentPosition - targetPosition) < stepSize) {
            currentPosition = targetPosition;
//            telemetry.addLine("Arrived");
        } else if (currentPosition - targetPosition > 0) {
            currentPosition -= stepSize;
//            telemetry.addLine("Decreasing");
        } else {
            currentPosition += stepSize;
//            telemetry.addLine("Increasing");
        }
        servo.setPosition(currentPosition);
    }

    public double getCurrentPosition() {
        return currentPosition;
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(double target) {
        targetPosition = target;
    }

    public boolean isMoving() {
        return (currentPosition != targetPosition);
    }
}
