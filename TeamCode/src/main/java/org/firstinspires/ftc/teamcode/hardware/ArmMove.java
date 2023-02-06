package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ArmMove implements State {

    RobotHardware rh = null;
    private double target;

    public ArmMove(RobotHardware rh, String goal) {
        this.rh = rh;

        if (goal.equals("FrontDown")) {
            target = 0.0;
        }
        if (goal.equals("FrontUp")) {
            target = 0.2;
        }
        if (goal.equals("Vertical")) {
            target = 0.4;
        }
        if (goal.equals("BackUp")) {
            target = 0.282;
        }
        if (goal.equals("BackDown")) {
            target = 0;
        }
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
