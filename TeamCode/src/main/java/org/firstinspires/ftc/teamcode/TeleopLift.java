package org.firstinspires.ftc.teamcode;

// State for driving using game controller
public class TeleopLift implements State {

    RobotHardware rh = null;

    public TeleopLift(RobotHardware rh) {

        this.rh = rh;
    }

    public void update() {

        // set lift movement speed
        double maxLiftSpeed = 0.75;
        double liftMove;
        if (rh.gamepad1.dpad_up == true) {
            liftMove = + maxLiftSpeed;
        } else if (rh.gamepad1.dpad_down == true) {
            liftMove = - maxLiftSpeed;
        } else {
            liftMove = 0;
        }

        // stop points
        if (rh.getLiftLowerBound() == true || rh.getLiftUpperBound() == true) {
            liftMove = 0;
        }

        // move arm up and down
        double liftArmPosition;
        double minArmPosition = -1;
        double maxArmPosition = 1;
        if (rh.gamepad1.a == true) {
            liftArmPosition = maxArmPosition;
        } else {
            liftArmPosition = minArmPosition;
        }

        // claw open and shut
        double liftClawPosition;
        double minClawPosition = -1;
        double maxClawPosition = 1;
        if (rh.gamepad1.b == true) {
            liftClawPosition = maxClawPosition;
        } else {
            liftClawPosition = minClawPosition;
        }
        rh.lift(liftMove, liftArmPosition, liftClawPosition);
    }
}
