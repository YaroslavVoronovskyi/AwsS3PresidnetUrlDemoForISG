package com.gmail.voronovskyi.yaroslav.isg.AWSS3PresidnetUrlDemoForISG.service;

import com.gmail.voronovskyi.yaroslav.isg.AWSS3PresidnetUrlDemoForISG.dto.CarDto;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Duration;

@Service
public class CarService {

    public CarDto get(int id) {
        String presidnetUrl = getUrl("demo-car-sharing-bucket", "cars/images.jpeg");
        return new CarDto()
                .setId(1)
                .setPresidnetUrl(presidnetUrl);
    }

    public String getUrl(String bucketName, String keyName) {
        Region region = Region.EU_CENTRAL_1;
        S3Presigner presigner = S3Presigner
                .builder()
                .region(region)
                .build();
        String presidnetUrl = getPresidnetUrl(presigner, bucketName, keyName);
        presigner.close();
        return presidnetUrl;
    }

    public String getPresidnetUrl(S3Presigner presignet, String bucketName, String keyName) {
        String presidnetUrl = null;
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(60))
                    .getObjectRequest(getObjectRequest)
                    .build();
            PresignedGetObjectRequest presignedGetObjectRequest =
                    presignet.presignGetObject(getObjectPresignRequest);
            presidnetUrl = presignedGetObjectRequest.url().toString();
            HttpURLConnection connection = (HttpURLConnection) presignedGetObjectRequest
                    .url().openConnection();
            presignedGetObjectRequest.httpRequest().headers().forEach((header, values) -> {
                values.forEach(value -> {
                    connection.addRequestProperty(header, value);
                });
            });
        } catch (S3Exception | IOException exception) {
            exception.getStackTrace();
        }
        return presidnetUrl;
    }
}
