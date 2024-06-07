package com.dongil.schickenservice.apis.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService service;

    @GetMapping("customers/{email}/login")
    public ResponseEntity<String> loginStart(CustomerVO customerVO){
        try {
            service.loginProcess(customerVO);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("메일 전송 실패");
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("customers/{email}/login")
    public ResponseEntity<LoginResultVO> loginCheck(@RequestBody CustomerVO customerVO){
        CustomerVO logonId = service.loginCheck(customerVO);

        return ResponseEntity.ok(new LoginResultVO(logonId == null ? "fail" : "success", logonId));
    }
}
