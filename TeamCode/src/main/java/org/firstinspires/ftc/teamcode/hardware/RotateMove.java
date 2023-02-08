package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class RotateMove implements State {

    RobotHardware rh = null;
    private double target = 1.0;

    public RotateMove(RobotHardware rh, double target) {
        this.rh = rh;

        this.target = target;
    }

    @Override
    public void update() {
        rh.armNew.rotate.move();
    }

    @Override
    public void init() {
        rh.armNew.rotate.setTargetPosition(target);
    }

    @Override
    public boolean getIsDone() {
        return !(rh.armNew.rotate.isMoving());
    }
}
