package org.example;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private int currentFloor = 1;
    private int numFloors=10;


    @GetMapping("/floor/{floorNumber}")
    public String goToFloor(@PathVariable Integer floorNumber) {
        if (floorNumber < 1 || floorNumber > numFloors) {
            return "Invalid floor number";
        }

        String result = "Moving from floor " + currentFloor + " to floor " + floorNumber;
        currentFloor = floorNumber;

        return result;
    }
}
