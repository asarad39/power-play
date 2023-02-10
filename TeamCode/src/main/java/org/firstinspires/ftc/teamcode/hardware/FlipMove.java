package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class FlipMove implements State {

    RobotHardware rh = null;
    private double target = 1.0;

    /**
     * Servo positions:
     * flip:
     *      1 = for the front
     *      0.5 = vertical
     *      0 = for the back
     **/

    public FlipMove(RobotHardware rh, double target) {
        this.rh = rh;

        this.target = target;
    }

    @Override
    public void update() {
        rh.armNew.flip.move();
    }

    @Override
    public void init() {
        rh.armNew.flip.setTargetPosition(target);
    }

    @Override
    public boolean getIsDone() {
        return !(rh.armNew.flip.isMoving());
    }
}
