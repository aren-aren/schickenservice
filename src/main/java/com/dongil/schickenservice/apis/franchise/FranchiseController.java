package com.dongil.schickenservice.apis.franchise;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/")
@RequiredArgsConstructor
@CrossOrigin
public class FranchiseController {
    private final FranchiseService franchiseService;

    @GetMapping("franchises")
    public List<FranchiseVO> getFranchises(){
        return franchiseService.getFranchises();
    }

    @GetMapping("franchises/{franchiseId}")
    public FranchiseVO getFranchise(@PathVariable String franchiseId){
        return franchiseService.getFranchise(franchiseId);
    }
}
