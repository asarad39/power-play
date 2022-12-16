package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class SeriesStack extends StackList implements State {

    boolean isDone = false;
    RobotHardware rh = null;


    public SeriesStack(RobotHardware rh) {

        super();
        this.rh = rh;
    }

    public void createStack(State[] states) {
        for (int i=0; i<states.length; i++) {
            stack.add(i, states[i]);
        }
    }

    public void init() {
        stack.get(0).init();
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }

    public void update() {

        State s = stack.get(0);
        s.update();
        rh.telemetry.addData("stack size", stack.size());

        if(s.getIsDone() == true) {
            stack.remove(0);

            if (stack.size() > 0) {
                State newS = stack.get(0);
                newS.init();
            } else {
                isDone = true;
                rh.telemetry.addData("isdone", isDone);
            }
        }
    }

    public void updateStackIndex(int index) {
        stack.get(index).update();
    }

//    public void init(OpMode op, int index) { }
}
