import org.example.entity.Lift;
import org.example.entity.LiftStates;
import org.example.entity.Request;
import org.example.manager.RequestDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class LiftTest {

  @Test
  public void testLiftInitialStateEqualsIdle() throws InterruptedException {
    Assertions.assertTimeoutPreemptively(
        Duration.ofMinutes(1),
        () -> {
          Lift l = new Lift("348979");
          Thread liftTread = new Thread(l);
          liftTread.start();
          boolean res = l.getCurrentState().equals("IDLE");
          Assertions.assertTrue(res);
          Request request = new Request(0, 4);
          Request request1 = new Request(3, 4);
          RequestDispatcher.addRequestForLift(l, request);
          RequestDispatcher.addRequestForLift(l, request1);
          Thread.sleep(1000);
          RequestDispatcher.addRequestForLift(l,new Request(2,5));
          while (l.getCurrentState() != LiftStates.IDLE) {
            Thread.sleep(1000);
          }
        });
  }
}
