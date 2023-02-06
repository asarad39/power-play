package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

import java.util.Arrays;

// State for driving using game controller
public class TeleopMove implements State {

    RobotHardware rh = null;
    double[] inputs = {0.0, 0.0 , 0.0};

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
        double scalar = getAdaptiveScalar();
//                baseSpeed
//                + ((maxSpeed - baseSpeed) * rh.gamepad2.right_trigger)
//                - ((baseSpeed - minSpeed)  * rh.gamepad2.left_trigger);

        double moveX = getAdjustedLeftStickX(); // rh.gamepad2.left_stick_x;
        double moveY = rh.gamepad2.left_stick_y;
        double moveRotate = rh.gamepad2.right_stick_x;

        double powerFR = (- moveX - moveY + moveRotate);
        double powerFL = (- moveX + moveY + moveRotate);
        double powerBR = (+ moveX - moveY + moveRotate);
        double powerBL = (+ moveX + moveY + moveRotate);

        double divisor = findPowerDivisor(powerFR, powerFL, powerBR, powerBL);

        // The right trigger speeds up the robot between minSpeed and maxSpeed
        powerFR = powerFR * scalar / divisor;
        powerFL = powerFL * scalar / divisor;
        powerBR = powerBR * scalar / divisor;
        powerBL = powerBL * scalar / divisor;

        rh.drive(powerFR, powerFL, powerBR, powerBL);
        updateInputs();

        rh.telemetry.addData("Adaptive Scalar", getAdaptiveScalar());
        rh.telemetry.addData("Inputs", Arrays.toString(inputs));

        rh.telemetry.addData("Encoder BL", rh.getEncoderBL());
        rh.telemetry.addData("Encoder FL", rh.getEncoderFL());
        rh.telemetry.addData("Encoder BR", rh.getEncoderBR());
        rh.telemetry.addData("Encoder FR", rh.getEncoderFR());

        rh.telemetry.addData("Power BL", powerBL);
        rh.telemetry.addData("Power FL", powerFL);
        rh.telemetry.addData("Power BR", powerBR);
        rh.telemetry.addData("Power FR", powerFR);
    }

    public double findPowerDivisor(double a, double b, double c, double d) {

        // Find values for maxing out motor powers while preserving motor power ratios
        double maxPower = Math.abs(a);
        if(Math.abs(b)>maxPower) maxPower = Math.abs(b);
        if(Math.abs(c)>maxPower) maxPower = Math.abs(c);
        if(Math.abs(d)>maxPower) maxPower = Math.abs(d);
        return maxPower;
    }

    // Make the robot move faster as one or more the inputs approaches 1
    public double getAdaptiveScalar() {

        double sum = 0.0;
        double terms = 0.0;

        for (double input : inputs) {

            if (input != 0.0) {

                sum += input;
                terms++;
            }
        }

        return sum / terms;
    }

    public void updateInputs() {

        inputs[0] = Math.abs(getAdjustedLeftStickX());
        inputs[1] = Math.abs(rh.gamepad2.left_stick_y);
        inputs[2] = Math.abs(rh.gamepad2.right_stick_x);
    }

    public double getAdjustedLeftStickX() {

        double raw = rh.gamepad2.left_stick_x;
        double sign = raw / Math.abs(raw);
        double abs = Math.abs(raw);

        if (abs <= 0.1) { // tested to the extreme, it works!

            return 0.0;

        } else{

            return (((1.0 - 0.0) / (1.0 - 0.1)) * (abs - 0.1)) * sign;
        }
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
