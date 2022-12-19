package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Stack;

public class ParallelStack extends StackList implements State {

    Telemetry telemetry;
    boolean isDone = false;
    RobotHardware rh = null;


    public ParallelStack(RobotHardware rh) {
        super();
        this.rh = rh;
    }

    public void createStack(State[] states) {
        for (int i = 0; i<states.length; i++) {
            stack.add(i, states[i]);
        }
    }

    public void init() {
        for(State s: stack) {
            s.init();
        }
    }

    public void update() {

        rh.telemetry.addData("stack size", stack.size());

        if (stack.size() == 0) {

            isDone = true;
        }

        for(int i = stack.size() - 1; i >= 0; i--) {

            if (stack.get(i).getIsDone()) {

                stack.remove(i);

            } else {

                stack.get(i).update();
            }
        }
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }
}
