package com.rafael.s3_first_steps.controller;

import com.rafael.s3_first_steps.response.BucketResponse;
import com.rafael.s3_first_steps.service.impl.S3ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class S3Controller {

    private final S3ServiceImpl s3Service;

    @Autowired
    public S3Controller(S3ServiceImpl s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/buckets")
    public List<BucketResponse> getAvailable(){
        return s3Service.obtainBucketList();
    }

    @PostMapping("/upload")
    public void uploadFileToBucket(@RequestParam("bucketName") String bucketName, @RequestParam("objectName") String objectName, @RequestParam("filePath") String filePath){
         s3Service.uploadObjectToBucket(bucketName, objectName, filePath);
    }

    @GetMapping("/download")
    public void downloadFileFromBucket(@RequestParam("bucketName") String bucketName, @RequestParam("objectName") String objectName){
        s3Service.downloadObjectFromBucket(bucketName, objectName);
    }
}
