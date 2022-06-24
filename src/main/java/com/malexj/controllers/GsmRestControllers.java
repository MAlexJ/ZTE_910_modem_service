package com.malexj.controllers;


import com.malexj.models.requests.MessageRequest;
import com.malexj.models.responses.LoginResponse;
import com.malexj.models.responses.MessageResponse;
import com.malexj.services.GsmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class GsmRestControllers {

    private final GsmService gsmService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login() {
        return ResponseEntity.ok(gsmService.getLoginResponse());
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
        return ResponseEntity.ok(gsmService.sendMessage(request));
    }

}