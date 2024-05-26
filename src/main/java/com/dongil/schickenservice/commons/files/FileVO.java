package com.dongil.schickenservice.commons.files;

import lombok.Data;

@Data
public class FileVO {
    private String id;
    private String name;
    private String originName;
    private String url;
    private String extension;
    private String parentId;
    private String tblId;
}
