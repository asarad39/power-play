package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ServoState implements State {

    ServoControl servoControl = null;
    String servoName = null;
    double targetPosition = 0;

    public ServoState(RobotHardware rh, String servoName, double targetPosition) {
//        servoControl =
    }

    public void update() {

    }

    public void init() {

    }

    public boolean getIsDone() {
        return false;
    }
}
