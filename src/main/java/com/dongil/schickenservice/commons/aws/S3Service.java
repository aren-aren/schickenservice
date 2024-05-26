package com.dongil.schickenservice.commons.aws;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String filename) throws Exception {
        try{
            String fileUrl = "https://s3.ap-northeast-2.amazonaws.com/"+bucket+"/"+filename;
            ObjectMetadata data = new ObjectMetadata();
            data.setContentType(file.getContentType());
            data.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, filename, file.getInputStream(), data);

            return fileUrl;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean deleteFile(String filename) throws Exception {
        try{
            amazonS3Client.deleteObject(bucket,filename);
            return true;
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public byte[] downFile(String filename) throws IOException{
    	S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, filename));
    	S3ObjectInputStream objectInputStream = o.getObjectContent();

//    	String fileName = URLEncoder.encode(oriName, "UTF-8").replaceAll("WW+", "%20");
//    	HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        httpHeaders.setContentLength(bytes.length);
//
//
//
//        httpHeaders.setContentDispositionFormData("attachment", fileName);
//        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);

        return IOUtils.toByteArray(objectInputStream);
    }
}
