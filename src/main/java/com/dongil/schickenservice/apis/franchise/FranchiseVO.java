package com.dongil.schickenservice.apis.franchise;

import lombok.Data;

@Data
public class FranchiseVO {
    String id;
    String name;
    String address;
    String addressDetail;
    String contactNumber;
    String coorX;
    String coorY;
    byte[] map;
}
