package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Stack;

public class ParallelStack extends StackList implements State {

    Telemetry telemetry;
    boolean isDone = false;


    public ParallelStack() {

    }

    public void update() {

        if (getStack().size() > 0) {
            for(State s: getStack()) {
                s.init();
                s.update();
            }
        }
    }

    @Override
    public boolean getIsDone() {
        return false;
    }

    public void updateStack() {
        for (State s : getStack()) {
            s.update();
        }
    }

    public void updateStackIndex(int index) {
        getStack().get(index).update();
    }


}
