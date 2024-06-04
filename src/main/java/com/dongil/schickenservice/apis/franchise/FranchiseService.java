package com.dongil.schickenservice.apis.franchise;

import com.dongil.schickenservice.commons.rest.NaverCloudRest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranchiseService {
    private static final Logger log = LoggerFactory.getLogger(FranchiseService.class);
    private final FranchiseMapper franchiseMapper;
    private final NaverCloudRest naverCloudRest;

    public List<FranchiseVO> getFranchises() {
        return franchiseMapper.getFranchises();
    }

    @Transactional
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

        if(franchiseVO.getMap() == null){
            /* 지도 집어넣기 */
        }

        return franchiseVO;
    }

    private boolean setCoordinate(FranchiseVO franchiseVO){
        return (franchiseMapper.setCoordinate(franchiseVO) == 1);
    }
}
