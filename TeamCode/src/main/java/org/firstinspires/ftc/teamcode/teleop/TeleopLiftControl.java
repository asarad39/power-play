package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.hardware.LiftControl;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopLiftControl implements State {

    RobotHardware rh = null;

    private boolean canJump = true;
    private boolean canLift = true;

    public TeleopLiftControl(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

    }

    public void update() {
        if (rh.gamepad1.y || rh.gamepad1.b) {

            if (canLift) {
                if (rh.gamepad1.y) {
                    rh.liftNew.adjustPosition(LiftControl.Positions.UP);
                }
                if (rh.gamepad1.b) {
                    rh.liftNew.adjustPosition(LiftControl.Positions.DOWN);
                }
                canLift = false;
            }

        } else {
            canLift = true;
        }

        // collect
        if (rh.gamepad1.a) {
            rh.liftNew.setPositionsIndex(0);
        }

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

        rh.liftNew.setPosition();
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
