package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public abstract class Mathematics {

    public static double getLogisticCurve(double maxPower, double targetPosition, double currentPosition, double steepness) {
        // high steepness corresponds to sharper curve
        // steepness is always positive
        double exponent = Math.pow(Math.E, steepness * (currentPosition - targetPosition));
        double logisticPower = 2 * maxPower / (1 + exponent) - maxPower;

        return logisticPower;
    }

    public static double PIDcontrol(double maxPower, double scalar, double currentPosition, double targetPosition, double currentTime, double startTime) {

        // smaller scalar will be shallower curve
        double PIDpower = (targetPosition - currentPosition) * (currentTime - startTime) * scalar;
        if (PIDpower > 1) {
            PIDpower = 1;
        }
        PIDpower = PIDpower * maxPower;

        return PIDpower;
    }



}
