package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ArmMove implements State {

    RobotHardware rh = null;
    private double target;

    /**
     * Servo positions:
     * arm:
     *      0 = down back
     *      0.282 = lifted (back)
     *      0.48 = vertical (transition)
     *      0.668 = lifted (front)
     *      1 = down front
     **/

    public ArmMove(RobotHardware rh, double target) {
        this.rh = rh;
        this.target = target;
    }

    @Override
    public void update() {
        rh.armNew.arm.move();
    }

    @Override
    public void init() {
        rh.armNew.arm.setTargetPosition(target);
    }

    @Override
    public boolean getIsDone() {
        return !(rh.armNew.arm.isMoving());
    }
}
