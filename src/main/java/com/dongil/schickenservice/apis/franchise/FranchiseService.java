package com.dongil.schickenservice.apis.franchise;

import com.dongil.schickenservice.commons.rest.NaverCloudRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FranchiseService {
    private final FranchiseMapper franchiseMapper;
    private final NaverCloudRest naverCloudRest;
    private final Map<String, byte[]> mapCache;

    FranchiseService(FranchiseMapper franchiseMapper, NaverCloudRest naverCloudRest){
        this.franchiseMapper = franchiseMapper;
        this.naverCloudRest = naverCloudRest;
        this.mapCache = new HashMap<>();
    }

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

        byte[] map = getMapData(franchiseVO);

        franchiseVO.setMap(map);

        return franchiseVO;
    }

    private byte[] getMapData(FranchiseVO franchiseVO){
        if(mapCache.containsKey(franchiseVO.getId())){
            /* 지도 집어넣기 */
            return mapCache.get(franchiseVO.getId());
        }

        byte[] map = naverCloudRest.getAddrMap(franchiseVO.getCoorX() + " " + franchiseVO.getCoorY());
        mapCache.put(franchiseVO.getId(), map);

        return map;
    }

    private boolean setCoordinate(FranchiseVO franchiseVO){
        return (franchiseMapper.setCoordinate(franchiseVO) == 1);
    }
}
