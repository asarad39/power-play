package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ClawMove implements State {

    RobotHardware rh = null;
    private double target = 1.0;
    private boolean setStart = false;

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

    public ClawMove(RobotHardware rh, double target, boolean setStart) {
        this.rh = rh;
        this.target = target;
        this.setStart = setStart;
    }

    @Override
    public void update() {
        rh.armNew.claw.move();
    }

    @Override
    public void init() {
        rh.armNew.claw.setTargetPosition(target);
        if (setStart) {
            rh.armNew.claw.setCurrentPosition(target + 0.01);
        }
    }

    @Override
    public boolean getIsDone() {
        return !(rh.armNew.claw.isMoving());
    }
}
