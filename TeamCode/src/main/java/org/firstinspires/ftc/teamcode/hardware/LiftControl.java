package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class LiftControl {

    RobotHardware rh;
//    PID liftPID = new PID(5700); // for new robot
    public DcMotor left;
    public DcMotor right;

    final int[] positions = {0, 100, 200, 300, 400};
    int posIndex;
    int offset;

    public LiftControl(RobotHardware rh) {
        this.rh = rh;
    }

    public void initialize() {
        posIndex = 0;
        offset = 0;
        // get DcMotors
        // set encoders
    }

    public void setPosition() {
        // TODO: Calculate power before setting motor speed
        left.setPower(0.1);
        right.setPower(0.1);

        // Calculate max/min position
        int newPosition = positions[posIndex] + offset;

        int min = 0;
        int max = 500;

        if (true) { // for homing
            if (newPosition < min) {
                newPosition = min;
            }
            if (newPosition > max) {
                newPosition = max;
            }
        }

        left.setTargetPosition(newPosition);
        right.setTargetPosition(newPosition);
    }

    public static enum Positions {
        UP,
        DOWN
    }
    // liftcontrol.adjustPosition(LiftControl.Positions.UP)
    public void adjustPosition(Positions p) {
        int d = 0;
        switch(p) {
            case UP: d = 1;
            case DOWN: d = -1;
        }
        // d is either 1 or -1
        posIndex = (posIndex + d) % positions.length;
        offset = 0;
        this.setPosition();
    }

    public void adjustOffset(int o) {
        offset += o;

        this.setPosition();
    }



//    public void setLiftTarget() {
//        liftPID.setTargetPosition(positions[posIndex] + offset);
//        rh.setLiftTarget(liftPID.getTargetPosition());
//    }

}
