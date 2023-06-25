package org.example.liftaction;

import org.example.entity.Lift;
import org.example.entity.LiftStates;

public class TowardsSourceLiftAction implements liftAction {
    @Override
    public void act(Lift lift) throws InterruptedException {
        lift.moveTo(lift.getCurrentRequest().sourceFloor);
        Thread.sleep(3000);
        lift.setCurrentState(LiftStates.MOVE_TO_DESTINATION);
    }
}
