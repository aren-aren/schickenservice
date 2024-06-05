package com.dongil.schickenservice.apis.franchise;

import com.dongil.schickenservice.commons.rest.NaverCloudRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "franchises")
public class FranchiseService {
    private final FranchiseMapper franchiseMapper;
    private final NaverCloudRest naverCloudRest;

    FranchiseService(FranchiseMapper franchiseMapper, NaverCloudRest naverCloudRest){
        this.franchiseMapper = franchiseMapper;
        this.naverCloudRest = naverCloudRest;
    }

    @Cacheable
    public List<FranchiseVO> getFranchises() {
        return franchiseMapper.getFranchises();
    }

    @Transactional
    @Cacheable(key = "#franchiseId")
    public FranchiseVO getFranchise(String franchiseId) {
        FranchiseVO franchiseVO = franchiseMapper.getFranchise(franchiseId);

        if(franchiseVO.getCoorX() == null){
            /* x,y좌표 찾아와서 집어넣기 */
            var address = naverCloudRest.getAddrPoint(franchiseVO.getAddress()).getAddresses().get(0);
            franchiseVO.setCoorX(address.getX());
            franchiseVO.setCoorY(address.getY());
            if(!setCoordinate(franchiseVO)){
                log.info("coordinate insert fail");
                throw new RuntimeException("coordinate insert fail");
            }
        }

        byte[] map = getMapData(franchiseVO);

        franchiseVO.setMap(map);

        return franchiseVO;
    }

    private byte[] getMapData(FranchiseVO franchiseVO){
        return naverCloudRest.getAddrMap(franchiseVO.getCoorX() + " " + franchiseVO.getCoorY());
    }

    private boolean setCoordinate(FranchiseVO franchiseVO){
        return (franchiseMapper.setCoordinate(franchiseVO) == 1);
    }
}
