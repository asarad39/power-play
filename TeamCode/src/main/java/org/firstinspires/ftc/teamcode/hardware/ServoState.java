package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ServoState implements State {

    ServoControl arm = new ServoControl("arm");
    ServoControl flip = new ServoControl("flip");
    ServoControl rotate = new ServoControl("rotate");
    ServoControl claw = new ServoControl("claw");

    public void update() {

    }

    public void init() {

    }

    public boolean getIsDone() {
        return false;
    }
}
