package com.rafael.s3_first_steps.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    // S3 client allows us to perform operations over the s3 buckets
    // Upload objects
    // List buckets
    // Download object
    // Create new buckets

    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Bean
    public S3Client s3Client(){
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}
