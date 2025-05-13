package com.example.demo.controller;
import com.example.demo.dto.SimulationRequest;
import com.example.demo.dto.SimulationResponse;
import com.example.demo.service.simulationService.SimulationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

   @Autowired
    private SimulationService simulationService;

    @PostMapping("/simulate")
    public SimulationResponse simulateLoan(@RequestBody SimulationRequest request) {
        return simulationService.simulate(request);
    }
}

