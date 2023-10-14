package codingtechniques.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	
	private String bucketName = "images-coding-techniques";
	Regions regions = Regions.US_WEST_2;
	
	public void uploadToS3 (InputStream inputStream, String filename) throws IOException 
	 , AmazonServiceException, SdkClientException {
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				           .withRegion(regions).build();
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image.jpeg");
		metadata.setContentLength(inputStream.available());
		
		PutObjectRequest request = new PutObjectRequest(bucketName, filename, inputStream, metadata);
		s3Client.putObject(request);
		
	}

}
