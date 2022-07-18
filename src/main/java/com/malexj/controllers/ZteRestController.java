package com.malexj.controllers;

import com.malexj.controllers.base.AbstractController;
import com.malexj.models.requests.MessageRequest;
import com.malexj.models.responses.BatteryResponse;
import com.malexj.models.responses.LoginResponse;
import com.malexj.models.responses.MessageResponse;
import com.malexj.services.ZteRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class ZteRestController extends AbstractController {

    public static final String BATTERY_CHARGING_REQUEST = "battery_charging,battery_vol_percent,battery_pers";
    private final ZteRestService service;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login() {
        LoginResponse response = service.login();
        log.info("GET:'/login', response - " + response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
        log.info("POST:'/send', request - " + request);
        var response = service.sendMessage(validateMessageRequest(request));
        log.info("POST:'/send', response - " + response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/battery")
    public ResponseEntity<BatteryResponse> batteryChargingStatus() {
        var response = service.getInfo(BATTERY_CHARGING_REQUEST);
        log.info("GET:'/battery', response - " + response);
        return ResponseEntity.ok(response);
    }

}