package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ArmMove implements State {

    RobotHardware rh = null;
    private double target;

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
