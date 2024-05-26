package com.dongil.schickenservice.commons.files;

import com.dongil.schickenservice.commons.aws.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileManager {
    private final S3Service s3Service;
    private final FileMapper fileMapper;

    public int saveFile(FileVO fileVO){
        if(fileVO.getParentId() == null) throw new RuntimeException("parentId is null");
        if(fileVO.getTblId() == null) throw new RuntimeException("tblId is null");

        return fileMapper.insertFile(fileVO);
    }

    public FileVO uploadFile(MultipartFile file) throws Exception{
        String uid = UUID.randomUUID().toString();

        FileVO fileVO = new FileVO();
        fileVO.setName(uid);
        fileVO.setOriginName(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")));
        fileVO.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));

        String url = s3Service.uploadFile(file, uid);
        if(url == null) throw new RuntimeException("uploadFile : url is null");

        fileVO.setUrl(url);
        return fileVO;
    }

    public FileVO fileUpdate(FileVO oldFile, MultipartFile newFile) throws Exception {
        if(!deleteFile(oldFile)){
            throw new RuntimeException("fileUpdate : file delete fail");
        }

        return uploadFile(newFile);
    }

    public boolean deleteFile(FileVO fileVO) throws Exception {
        int result = fileMapper.deleteFile(fileVO.getId());
        if(result == 0) throw new RuntimeException("deleteFile : file Delete fail");

        return s3Service.deleteFile(fileVO.getName());
    }

    public byte[] downloadFile(FileVO fileVO) throws Exception{

    	if(fileVO==null || fileVO.getId() == null) {
    		return null;
    	}
    	FileVO foundFile = fileMapper.selectFile(fileVO);
        if(foundFile == null) throw new RuntimeException("file not found");

		return s3Service.downFile(fileVO.getName());
    }
}
