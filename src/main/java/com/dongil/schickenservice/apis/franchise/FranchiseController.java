package com.dongil.schickenservice.apis.franchise;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/")
@RequiredArgsConstructor
public class FranchiseController {
    private final FranchiseService franchiseService;

    @GetMapping("franchises")
    public List<FranchiseVO> getFranchises(){
        return franchiseService.getFranchises();
    }
}
