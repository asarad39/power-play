package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Stack;

public class ParallelStack extends StackList implements State {

    Telemetry telemetry;
    boolean isDone = false;


    public ParallelStack() {
        super();
    }

//    public void createStack(State[] states) {
//        for (int i=0; i<states.length; i++) {
//            stack.add(i, states[i]);
//        }
//    }

    public void update() {

        if (stack.size() > 0) {
            for(State s: stack) {
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
        for (State s : stack) {

            if (!s.getIsDone()) {
                s.update();
            }
        }
    }


}
