package org.example.manager;

import org.example.entity.Lift;
import org.example.entity.LiftStates;
import org.example.entity.Request;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

// rename to request storage
public class RequestDispatcher {

  /// A lift will subscribe to a RD, in turn L will be assigned a queue, when request is submitted
  // in this
  // queue L is notified if it is busy it will ignore, else L will change its state

  public static HashMap<Lift, LinkedList<Request>> queue = new HashMap<>();

  public static void subscribeLift(Lift l) {
    if (!queue.containsKey(l)) {
      LinkedList<Request> linkedList = new LinkedList<>();
      queue.put(l, linkedList);
    }
  }

  public static LinkedList<Request> getRequestListForLift(Lift l) {
    return queue.get(l);
  }

  public static void addRequestForLift(Lift l, Request r) {
    if(!isRequestValidated(r)) return;
    LinkedList<Request> queue = getRequestListForLift(l);
    if (queue != null) queue.addLast(r);
    if (Objects.equals(l.getCurrentState(), LiftStates.IDLE)) l.notifyLift();
  }

  private static boolean isRequestValidated(Request r) {
    return r.sourceFloor <= 10
            && r.destinationFloor <= 10
            && r.sourceFloor >= 0
            && r.destinationFloor >= 0;
  }
}
