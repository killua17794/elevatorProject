package org.example.liftaction;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Lift;
import org.example.entity.LiftStates;

@Slf4j
public class TowardsDestinationLiftAction implements liftAction {
  @Override
  public void act(Lift lift) throws InterruptedException {
    lift.moveTo(lift.getCurrentRequest().destinationFloor);
    Thread.sleep(3000);
    log.info("fullfilled A request");
    lift.setCurrentRequest(null);
    lift.getRequestFromRequestQueue();
    if (lift.getCurrentRequest() != null) {
      lift.setCurrentState(LiftStates.MOVE_TO_SOURCE);
    } else {
      lift.setCurrentState(LiftStates.IDLE);
    }
  }
}
