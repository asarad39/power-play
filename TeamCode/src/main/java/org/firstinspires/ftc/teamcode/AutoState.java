package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public interface AutoState {
    
    public void update();
    public void init(OpMode op);
    public void init(OpMode op, int index);
}
