package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class StackList {

    private ArrayList<State> stack;
    public StackList() {
        stack = new ArrayList<State>();
    }

    public ArrayList<State> getStack() {
        return stack;
    }

    public int size() {
        return stack.size();
    }

    public void update() {

    }

    public void init() {

    }
}
