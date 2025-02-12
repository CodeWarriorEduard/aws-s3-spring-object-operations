package com.rafael.s3_first_steps.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketResponse {

    private String name;
    private Instant creationDate;

}
