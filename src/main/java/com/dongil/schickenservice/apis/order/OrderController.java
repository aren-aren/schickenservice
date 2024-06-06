package com.dongil.schickenservice.apis.order;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<String> loginStart(CustomerVO customerVO, HttpSession session){
        try {
            CustomerVO loginId = service.loginProcess(customerVO);
            session.setAttribute("loginId", loginId);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("메일 전송 실패");
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("customers/{email}/login")
    public ResponseEntity<CustomerVO> loginCheck(@RequestBody CustomerVO customerVO, HttpSession session){
        CustomerVO loginId = (CustomerVO) session.getAttribute("loginId");
        CustomerVO logonId = service.loginCheck(customerVO, loginId);

        return ResponseEntity.ok(logonId);
    }
}
