package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Stack;

public class ParallelStack implements AutoState {

    private ArrayList<State> stack;
    Telemetry telemetry;
    boolean isDone = false;

    public ArrayList<State> getStack() {
        return this.stack;
    }

    public ParallelStack() {
        stack = new ArrayList<State>();
    }

    public void push(State state) {
        stack.add(state);
    }

    public void pop(int index) {
        stack.remove(index);
    }

    public void init(OpMode op, int index) {
        stack.get(index).init();

    }

    public void update() {
        
        for(State s: stack) {

            s.init();
        }

        if (stack.size() > 0) {

            for(State s: stack) {
                s.update();
            }
        }
    }

    @Override
    public void init(OpMode op) {

    }
}
