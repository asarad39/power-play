package org.firstinspires.ftc.teamcode;


public class TeleopMove {

    RobotHardware rh = null;

    public TeleopMove(RobotHardware rh) {

        this.rh = rh;
    }

    public void update() {

        double moveX = rh.gamepad1.left_stick_x;
        double moveY = rh.gamepad1.left_stick_y;
        double moveRotate = rh.gamepad1.right_stick_x;

        double powerFR = moveX + moveY + moveRotate;
        double powerFL = moveX - moveY + moveRotate;
        double powerBR = -moveX + moveY + moveRotate;
        double powerBL = -moveX - moveY + moveRotate;

        double divisor = findPowerDivisor(powerFR, powerFL, powerBR, powerBL);
        double maxSpeed = 0.6;
        double minSpeed = 0.2;
        double scalar = minSpeed + (maxSpeed - minSpeed) * rh.gamepad1.right_trigger;

        powerFR = powerFR * scalar / divisor;
        powerFL = powerFL * scalar / divisor;
        powerBR = powerBR * scalar / divisor;
        powerBL = powerBL * scalar / divisor;

        rh.drive(powerFR, powerFL, powerBR, powerBL);
    }

    public double findPowerDivisor(double a, double b, double c, double d) {
        double maxPower = Math.abs(a);
        if(Math.abs(b)>maxPower) maxPower = Math.abs(b);
        if(Math.abs(c)>maxPower) maxPower = Math.abs(c);
        if(Math.abs(d)>maxPower) maxPower = Math.abs(d);
        return maxPower;
    }
}
