package com.dongil.schickenservice.apis.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    @Select("""
    SELECT
          email
        , phone_number
        , address
        , coor_x
        , coor_y
    FROM customer
    WHERE email = #{email}
    """)
    CustomerVO getCustomer(CustomerVO customerVO);

    @Insert("""
    INSERT INTO customer(email)
    VALUES (#{email})
    """)
    int insertCustomer(CustomerVO customerVO);
}
