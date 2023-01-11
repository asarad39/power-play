package org.firstinspires.ftc.teamcode.stateStructure;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

import java.util.ArrayList;

public class SeriesStack extends StackList implements State {

    boolean isDone = false;
    RobotHardware rh = null;


    public SeriesStack(RobotHardware rh) {

        super();
        this.rh = rh;
    }

    public void createStack(State[] states) {
        for (int i = 0; i < states.length; i++) {
            stack.add(i, states[i]);
        }
    }

    public void createStack(ArrayList<State> states) {
        for (int i = 0; i < states.size(); i++) {
            stack.add(i, states.get(i));
        }
    }

    public void init() {
        stack.get(0).init();
    }

    public void update() {

        State s = stack.get(0);
        s.update();

        rh.telemetry.addData("stack size", stack.size());
//        rh.telemetry.addData("isDone [0]", this.getStack().get(0).getIsDone());
        rh.telemetry.addData("isDone stack[0]", stack.get(0).getIsDone());

        if (s.getIsDone() == true) {
            stack.remove(0);

            if (stack.size() > 0) {
                State newS = stack.get(0);
                newS.init();
            } else {
                isDone = true;
            }
        }
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }
}
