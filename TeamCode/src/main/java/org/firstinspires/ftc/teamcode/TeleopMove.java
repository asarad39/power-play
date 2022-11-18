package org.firstinspires.ftc.teamcode;

// State for driving using game controller
public class TeleopMove implements State {

    RobotHardware rh = null;

    public TeleopMove(RobotHardware rh) {

        this.rh = rh;
    }

    public void update() {

        // Calculate the motor powers and send them to the DC motors
        double minSpeed = 0.2;
        double maxSpeed = 0.6;
        double scalar = minSpeed + (maxSpeed - minSpeed) * rh.gamepad1.right_trigger;

        double moveX = rh.gamepad1.left_stick_x;
        double moveY = rh.gamepad1.left_stick_y;
        double moveRotate = rh.gamepad1.right_stick_x;

        double powerFR = + moveX + moveY + moveRotate;
        double powerFL = + moveX - moveY + moveRotate;
        double powerBR = - moveX + moveY + moveRotate;
        double powerBL = - moveX - moveY + moveRotate;

        double divisor = findPowerDivisor(powerFR, powerFL, powerBR, powerBL);

        // The right trigger speeds up the robot between minSpeed and maxSpeed
        powerFR = powerFR * scalar / divisor;
        powerFL = powerFL * scalar / divisor;
        powerBR = powerBR * scalar / divisor;
        powerBL = powerBL * scalar / divisor;

        rh.drive(powerFR, powerFL, powerBR, powerBL);
    }

    public double findPowerDivisor(double a, double b, double c, double d) {

        // Find values for maxing out motor powers while preserving motor power ratios
        double maxPower = Math.abs(a);
        if(Math.abs(b)>maxPower) maxPower = Math.abs(b);
        if(Math.abs(c)>maxPower) maxPower = Math.abs(c);
        if(Math.abs(d)>maxPower) maxPower = Math.abs(d);
        return maxPower;
    }
}
