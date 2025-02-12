package com.rafael.s3_first_steps.service;

import com.rafael.s3_first_steps.response.BucketResponse;
import java.util.List;

public interface IS3Service {

    List<BucketResponse> obtainBucketList();
    boolean doesBucketExist(String bucketName);
    void downloadObjectFromBucket(String bucketName, String objectName);
    void uploadObjectToBucket(String bucketName, String objectName, String filePath);

}
