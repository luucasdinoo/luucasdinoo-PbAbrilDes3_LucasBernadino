package br.com.bancoamericano.mscustomer.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Base64;

@Service
public class S3Util {

    @Value("${aws.credentials.access-key}")
    private String AWS_ACCESS_KEY;
    @Value("${aws.credentials.secret-key}")
    private String AWS_SECRET_KEY;
    @Value("${aws.credentials.access-token}")
    private String AWS_ACCESS_TOKEN;
    @Value("${aws.s3.bucket}")
    private String AWS_BUCKET;

    public String uploadFile(String fileName, File file)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {

        AwsSessionCredentials credentials = AwsSessionCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_ACCESS_TOKEN);
        StaticCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

        S3Client client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(provider)
                .build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(AWS_BUCKET)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(fileName)
                .build();

        //client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
        client.putObject(request, RequestBody.fromFile(file));
        return client.utilities().getUrl(url -> url.bucket(this.AWS_BUCKET).key(fileName)).toExternalForm();
    }

    public InputStreamResource downloadArquivo(String fileName) {

        AwsSessionCredentials credentials = AwsSessionCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_ACCESS_TOKEN);
        StaticCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

        S3Client client = S3Client.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(provider)
                .build();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(AWS_BUCKET)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> response = client.getObject(request);
        InputStreamResource resource = new InputStreamResource(response);

        return resource;
    }
}
