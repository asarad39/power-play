package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;
// Represents the lift of the robot in code

public class ServoControl {

    RobotHardware rh;
    Servo servo;

    private final double maxStepSize = 0.007;

    private double currentPosition = 0;
    private double targetPosition = 0;
    private double stepSize;

    public ServoControl(RobotHardware rh, Servo servo, double stepSize) {
        this.rh = rh;
        this.servo = servo;

        if (stepSize > maxStepSize) {
            this.stepSize = maxStepSize;
        } else if (stepSize < 0) {
            this.stepSize = 0;
        } else {
            this.stepSize = stepSize;
        }
    }

    public ServoControl(RobotHardware rh, Servo servo) {
        this.rh = rh;
        this.servo = servo;
        this.stepSize = maxStepSize;
    }

    public void move() {
        if (Math.abs(currentPosition - targetPosition) < stepSize) {
            currentPosition = targetPosition;
            rh.telemetry.addLine("Arrived");
        } else if (currentPosition - targetPosition > 0) {
            currentPosition -= stepSize;
            rh.telemetry.addLine("Decreasing");
        } else {
            currentPosition += stepSize;
            rh.telemetry.addLine("Increasing");
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
