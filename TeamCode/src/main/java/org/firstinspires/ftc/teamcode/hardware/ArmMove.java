package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ArmMove implements State {

    RobotHardware rh = null;
    private double target;

//    double[] armPositions = {
//            1,
//            0.668,
//            0.48,
//            0.48,
//            0.282,
//            0,
//    };
//    private final String[] positions = {
//            "front down",
//            "front up",
//            "front vertical",
//            "back vertical",
//            "back up",
//            "back down",
//    };

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
