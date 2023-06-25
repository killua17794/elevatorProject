package org.example.liftaction;

import org.example.entity.Lift;
import org.example.entity.LiftStates;

public class IdleLiftAction implements liftAction {
  @Override
  public void act(Lift lift) throws InterruptedException {
    lift.waitUntilRequestArrives();
    lift.getRequestFromRequestQueue();
    lift.setCurrentState(LiftStates.MOVE_TO_SOURCE);
  }
}
