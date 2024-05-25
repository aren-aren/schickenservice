package com.dongil.schickenservice.apis.menu;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MenuInsertVO {
    private String id;
    private String menu;
    private String price;
    private MultipartFile attach;
}
