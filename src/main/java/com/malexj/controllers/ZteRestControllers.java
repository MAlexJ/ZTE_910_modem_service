package com.malexj.controllers;

import com.malexj.models.requests.MessageRequest;
import com.malexj.models.responses.BatteryResponse;
import com.malexj.models.responses.LoginResponse;
import com.malexj.models.responses.MessageResponse;
import com.malexj.services.ZteRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class ZteRestControllers {

    private final ZteRestService service;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login() {
        return ResponseEntity.ok(service.login());
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
        return ResponseEntity.ok(service.sendMessage(request));
    }

    @GetMapping("/battery")
    public ResponseEntity<BatteryResponse> batteryChargingStatus() {
        String batteryChargingRequest = "battery_charging,battery_vol_percent,battery_pers";
        return ResponseEntity.ok(service.getInfo(batteryChargingRequest));
    }

}