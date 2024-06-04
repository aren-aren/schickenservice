package com.dongil.schickenservice.apis.franchise;

import org.apache.ibatis.annotations.Insert;
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

    @Select("""
    SELECT
          f.id
        , f.name
        , f.address
        , f.address_detail
        , f.contact_number
        , fc.coor_x
        , fc.coor_y
    FROM franchise f
        LEFT JOIN franchise_coordinate fc ON f.id = fc.id
    WHERE f.id = #{franchiseId}
    """)
    FranchiseVO getFranchise(String franchiseId);

    @Insert("""
    INSERT INTO franchise_coordinate(id, coor_x, coor_y)
    VALUES (#{id}, #{coorX}, #{coorY})
    """)
    int setCoordinate(FranchiseVO franchiseVO);
}
