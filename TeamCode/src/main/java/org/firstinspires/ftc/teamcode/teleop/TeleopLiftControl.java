package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.hardware.LiftControl;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopLiftControl implements State {

    RobotHardware rh = null;

    private boolean canJump = true;

    public TeleopLiftControl(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

    }

    public void update() {
        rh.liftNew.adjustPosition(LiftControl.Positions.UP, rh.gamepad1.b);
        rh.liftNew.adjustPosition(LiftControl.Positions.DOWN, rh.gamepad1.a);

        // jumping
        if (rh.gamepad1.dpad_right) {
            if (canJump) {
                rh.liftNew.adjustOffset(100);
                canJump = false;
            }
        } else {
            canJump = true;
        }

        int offsetSize = 2;
        // adjust lift height
        if (rh.gamepad1.dpad_down) {
            rh.liftNew.adjustOffset(-offsetSize);
        } else if (rh.gamepad1.dpad_up) {
            rh.liftNew.adjustOffset(offsetSize);
        }
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
