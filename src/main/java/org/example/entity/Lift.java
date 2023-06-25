package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.liftaction.IdleLiftAction;
import org.example.liftaction.TowardsDestinationLiftAction;
import org.example.liftaction.TowardsSourceLiftAction;
import org.example.liftaction.liftAction;
import org.example.manager.RequestDispatcher;

import java.util.HashMap;
import java.util.LinkedList;

// can use callable in future
@Slf4j
public class Lift implements Runnable {

  @Getter @Setter private String currentState = LiftStates.IDLE;

  private final Object lock = new Object();

  public Lift(String liftId) {
    this.liftId = liftId;
    stateliftActionHashMap = new HashMap<>();
    stateliftActionHashMap.put(LiftStates.IDLE, new IdleLiftAction());
    stateliftActionHashMap.put(LiftStates.MOVE_TO_SOURCE, new TowardsSourceLiftAction());
    stateliftActionHashMap.put(LiftStates.MOVE_TO_DESTINATION, new TowardsDestinationLiftAction());

    RequestDispatcher.subscribeLift(this);
  }

  @Getter @Setter String liftId;

  @Getter private int currentFloor = 0;

  @Setter @Getter private Request currentRequest;

  @Getter LinkedList<Request> fullFiledRequestQueue = new LinkedList<>();

  HashMap<String, liftAction> stateliftActionHashMap;

  @SneakyThrows
  @Override
  public void run() {

    while (true) {
      liftAction liftAction = stateliftActionHashMap.get(this.currentState);
      if (liftAction != null) liftAction.act(this);
      else throw new IllegalStateException("No Action defined for state" + this.currentState);
    }
  }

  public void moveTo(int destination) throws InterruptedException {
    if (currentFloor != destination) {
      while (true) {
        if (Math.abs(destination - currentFloor) == 0) {
          break;
        } else if (destination > currentFloor) {
          Thread.sleep(1000);
          currentFloor++;
        } else {
          Thread.sleep(1000);
          currentFloor--;
        }
        log.debug("Lift:{} is at floor {} ", liftId, currentFloor);
      }
    }
    log.debug("Lift is at {}", currentFloor);
  }

  public void getRequestFromRequestQueue() {
    LinkedList<Request> ll = RequestDispatcher.getRequestListForLift(this);
    if (!ll.isEmpty()) {
      currentRequest = ll.pollFirst();
    }
  }

  public void notifyLift() {
    synchronized (lock) {
      lock.notify();
    }
  }

  public void waitUntilRequestArrives() throws InterruptedException {
    synchronized (lock) {
      log.debug("Lift is waiting at {} and waiting for Request", this.currentFloor);
      lock.wait();
    }
  }
}
