package com.dongil.schickenservice.apis.franchise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseService {
    private final FranchiseMapper franchiseMapper;

    public List<FranchiseVO> getFranchises() {
        return franchiseMapper.getFranchises();
    }
}
