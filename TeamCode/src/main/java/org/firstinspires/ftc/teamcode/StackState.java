package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class StackState implements State {

    private ArrayList<State> stack;
    Telemetry telemetry;
    boolean isDone = false;

    public StackState() {
        stack = new ArrayList<State>();
    }

    public void push(State state) {
        stack.add(state);
    }

    public void pop(int index) {
        stack.remove(index);
    }

    public void init() {
        stack.get(0).init();
    }

    public boolean getIsDone() {
        return stack.size() > 1;
    }

    public void update() {

        State s = stack.get(0);
        s.update();

        if(s.getIsDone() == true) {
            pop(0);

            if (stack.size() > 0) {
                State newS = stack.get(0);
                newS.init();
            }
        }
    }
}
