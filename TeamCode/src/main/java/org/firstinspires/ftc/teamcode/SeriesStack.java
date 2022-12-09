package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class SeriesStack extends StackList implements State {

    Telemetry telemetry;
    boolean isDone = false;

    public SeriesStack() {

    }

    public void init() {

        getStack().get(0).init();
    }

    @Override
    public boolean getIsDone() {
        return false;
    }

    public void update() {

        State s = getStack().get(0);

        if(s.getIsDone() == true) { //TODO: find alternative
            pop(0);

            if (getStack().size() > 0) {
                State newS = getStack().get(0);
                newS.init();
            }
        }
    }

    public void updateStack() {
        for (State s : getStack()) {
            s.update();
        }
    }

    public void updateStackIndex(int index) {
        getStack().get(index).update();
    }


    public void pop(int index) {
        getStack().remove(index);
    }

    public void init(OpMode op, int index) {

    }
}
