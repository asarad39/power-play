package org.firstinspires.ftc.teamcode.archive;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

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