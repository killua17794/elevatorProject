package org.example.liftaction;

import org.example.entity.Lift;

public interface liftAction {

    void act(Lift lift) throws InterruptedException;
}
