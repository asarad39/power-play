package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ClawMove implements State {

    RobotHardware rh = null;
    private double target = 1.0;

    /**
     * Servo positions:
     * claw:
     *      0.30 = closed
     *      0 = open
     **/

    public ClawMove(RobotHardware rh, double target) {
        this.rh = rh;

        this.target = target;
    }

    @Override
    public void update() {
        rh.armNew.claw.move();
    }

    @Override
    public void init() {
        rh.armNew.claw.setTargetPosition(target);
    }

    @Override
    public boolean getIsDone() {
        return !(rh.armNew.claw.isMoving());
    }
}
