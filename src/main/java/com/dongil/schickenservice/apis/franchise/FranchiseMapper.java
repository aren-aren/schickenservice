package com.dongil.schickenservice.apis.franchise;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FranchiseMapper {
    @Select("""
    SELECT
          id
        , name
        , address
        , address_detail
        , contact_number
    FROM franchise
    """)
    List<FranchiseVO> getFranchises();
}
