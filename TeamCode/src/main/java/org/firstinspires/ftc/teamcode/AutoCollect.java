package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class AutoCollect implements State {

    RobotHardware rh = null;
    Telemetry telemetry;

    private boolean isDone = false;



    public AutoCollect(RobotHardware rh) {
        this.rh = rh;
    }

    public void init() {   }

    public boolean getIsDone() {
        return isDone;
    }

    public void update() {

    }
}