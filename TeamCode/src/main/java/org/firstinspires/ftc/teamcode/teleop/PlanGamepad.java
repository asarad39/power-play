//// Before writing this:
//// - Push code
//// - Add power curve to lift
//// - Find correct positions using LiftTestTeleop
//// - Adjust min and max positions for lift!
//
//boolean mirrored = false; // front positions
//boolean canMirror = true;
//boolean canLift = true;
//boolean canJump = true;
//
//int[] front = {0, 1, 1, 1};
//int[] back = {4, 5, 5, 5};
//int index = 0
//
//// mirror button (modifier)
//if (rh.gamepad1.right_bumper) {
//  if (canMirror) {
//    mirrored = !mirrored;
//    canMirror = false;
//  }
//} else {
//    canMirror = true;
//}
//
//int offsetSize = 2;
//
//// adjust lift height
//if (gamepad1.dpad_down) {
//  rh.liftNew.adjustOffset(-offsetSize);
//} else if (gamepad1.dpad_up) {
//  rh.liftNew.adjustOffset(offsetSize);
//}
//
//if (rh.gamepad1.b && index >= 0) {
//  if (canLift) {
//
//    index--;
//    canLift = false;
//
//    if(mirrored) {
//
//      rh.armNew.setPosition(back[index]);
//
//    } else {
//
//      rh.armNew.setPosition(front[index]);
//    }
//
//  }
//} if (rh.gamepad1.y && index <= 3) {
//  if (canLift) {
//
//    index++;
//    canLift = false;
//
//    if(mirrored) {
//
//      rh.armNew.setPosition(back[index]);
//
//    } else {
//
//      rh.armNew.setPosition(front[index]);
//    }
//
//  }
//} else {
//    canLift = true;
//}
//
//rh.liftNew.adjustPositions(UP, gamepad1.y)
//rh.liftNew.adjustPositions(DOWN, gamepad1.b)
//
//// Move to collect
//if (gamepad1.a) {
//
//  if (mirrored) {
//    rh.armNew.setPosition(5);
//  } else {
//    rh.armNew.setPosition(0);
//  }
//
//  rh.liftNew.setPositionsIndex(0);
//}
//
//// TODO: We need to make the claw vertical!
//if (gamepad1.x) {
//
//  if (mirrored) {
//    rh.armNew.setPosition(3);
//  } else {
//    rh.armNew.setPosition(2);
//  }
//}
//
//// jump!
//// TODO: add final click adjustment
//if (rh.gamepad1.dpad_right) {
//  if (canJump) {
//
//    rh.liftNew.adjustOffset(100);
//    canJump = false;
//
//    if (mirrored) {
//      rh.armNew.setPosition(3);
//    } else {
//      rh.armNew.setPosition(2);
//    }
//  }
//} else {
//    canJump = true;
//}
//
//rh.armNew.moveClaw(gamepad1.right_bumper);
//rh.armNew.clawUpdate();
//rh.liftNew.setPosition() {
//
//
//// --------------- method for LiftControl ------------------
//
//public void setPositionsIndex(int index) {
//
//  posIndex = index;
//  offset = 0;
//  this.setPosition();
//}
//
//// Speed up claw transitions?