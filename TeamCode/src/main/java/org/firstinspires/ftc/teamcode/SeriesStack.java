package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class SeriesStack implements AutoState {

    private ArrayList<State> stack;
    Telemetry telemetry;
    boolean isDone = false;

    public ArrayList<State> getStack() {
        return this.stack;
    }

    public SeriesStack() {
        stack = new ArrayList<State>();
    }

    public void push(State state) {
        stack.add(state);
    }

    public void pop(int index) {
        stack.remove(index);
    }

    public void init(OpMode op) {

        stack.get(0).init();

    }

    public void update() {

        State s = stack.get(0);
        s.update();

        if(s.getIsDone() == true) { //TODO: find alternative
            pop(0);

            if (stack.size() > 0) {
                State newS = stack.get(0);
                newS.init();
            }
        }
    }
}
