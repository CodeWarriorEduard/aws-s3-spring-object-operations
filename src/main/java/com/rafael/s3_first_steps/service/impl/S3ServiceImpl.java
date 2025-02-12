package com.rafael.s3_first_steps.service.impl;

import com.rafael.s3_first_steps.response.BucketResponse;
import com.rafael.s3_first_steps.service.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3ServiceImpl implements IS3Service {


    private final S3Client s3Client;


    @Autowired
    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }



    @Override
    public List<BucketResponse> obtainBucketList(){

        List<Bucket> response = s3Client.listBuckets().buckets();
        List<BucketResponse> buckets;

        buckets = response.stream()
                .map(bucket -> new BucketResponse(bucket.name(), bucket.creationDate()))
                .collect(Collectors.toList());

        return buckets;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        List<BucketResponse> response = obtainBucketList();
        return response.stream().anyMatch(b -> bucketName.equals(b.getName()));
    }

    @Override
    public void downloadObjectFromBucket(String bucketName, String objectName) {

        try{
            Path downloadPath = Paths.get("../../../downloads"+objectName);
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();
           s3Client.getObject(request, ResponseTransformer.toFile(downloadPath));
        } catch (AwsServiceException | SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadObjectToBucket(String bucketName, String objectName, String filePath) {
        try{
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();
            s3Client.putObject(request, RequestBody.fromFile(Paths.get(filePath)));
        }catch (AwsServiceException e){
            System.err.println(e.getMessage());
        }
    }
}
