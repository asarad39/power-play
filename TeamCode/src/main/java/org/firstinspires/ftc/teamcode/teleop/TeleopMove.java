package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopMove implements State {

    RobotHardware rh = null;

    public TeleopMove(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

//        rh.driveTrain.setCoast(); // Uncomment if we want the robot to coast with 0 motor power
    }

    public void update() {

        // Calculate the motor powers and send them to the DC motors
        double baseSpeed = 0.8; // 0.4;
        double minSpeed = 0.2;
        double maxSpeed = 1.0; // 0.8;
        double scalar = baseSpeed
                + ((maxSpeed - baseSpeed) * rh.gamepad2.right_trigger)
                - ((baseSpeed - minSpeed)  * rh.gamepad2.left_trigger);

        double moveX = rh.gamepad2.left_stick_x;
        double moveY = rh.gamepad2.left_stick_y;
        double moveRotate = rh.gamepad2.right_stick_x;

        double powerFR = (- moveX - moveY + moveRotate) / 3;
        double powerFL = (- moveX + moveY + moveRotate) / 3;
        double powerBR = (+ moveX - moveY + moveRotate) / 3;
        double powerBL = (+ moveX + moveY + moveRotate) / 3;

        double divisor = findPowerDivisor(powerFR, powerFL, powerBR, powerBL);

        // The right trigger speeds up the robot between minSpeed and maxSpeed
        powerFR = powerFR * scalar / divisor;
        powerFL = powerFL * scalar / divisor;
        powerBR = powerBR * scalar / divisor;
        powerBL = powerBL * scalar / divisor;

        rh.drive(powerFR, powerFL, powerBR, powerBL);

        rh.telemetry.addData("Encoder BL", rh.getEncoderBL());
        rh.telemetry.addData("Encoder FL", rh.getEncoderFL());
        rh.telemetry.addData("Encoder BR", rh.getEncoderBR());
        rh.telemetry.addData("Encoder FR", rh.getEncoderFR());

        rh.telemetry.addData("Encoder BL", powerBL);
        rh.telemetry.addData("Encoder FL", powerFL);
        rh.telemetry.addData("Encoder BR", powerBR);
        rh.telemetry.addData("Encoder FR", powerFR);
    }

    public double findPowerDivisor(double a, double b, double c, double d) {

        // Find values for maxing out motor powers while preserving motor power ratios
        double maxPower = Math.abs(a);
        if(Math.abs(b)>maxPower) maxPower = Math.abs(b);
        if(Math.abs(c)>maxPower) maxPower = Math.abs(c);
        if(Math.abs(d)>maxPower) maxPower = Math.abs(d);
        return maxPower;
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
