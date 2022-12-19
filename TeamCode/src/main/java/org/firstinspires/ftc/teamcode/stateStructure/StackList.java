package org.firstinspires.ftc.teamcode.stateStructure;

import java.util.ArrayList;

public class StackList {

    public ArrayList<State> stack;
    public StackList() {
        stack = new ArrayList<State>();
    }

    public ArrayList<State> getStack() {
        return stack;
    }

    public int size() {
        return stack.size();
    }
}
