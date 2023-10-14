package codingtechniques.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import codingtechniques.model.User;
import codingtechniques.service.S3Service;

@Controller
public class UserController {
	
	@Autowired
	S3Service s3Service;
	
	
	@GetMapping("/file-upload")
	public String fileUploadPage () {
		return "form";
	}
	
	@PostMapping("/file-upload")
	public String saveFiles(Model model,
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile multipartFile) throws AmazonServiceException {
		
		String filename = multipartFile.getOriginalFilename();
		 User user = new User();
		 user.setFilename(filename);
		 user.setDescription(description);
		 
		  try {
			s3Service.uploadToS3(multipartFile.getInputStream(), filename);
		} catch (AmazonServiceException e) {
			model.addAttribute("error", "AWS Service Error");
			e.printStackTrace();
		} catch (SdkClientException e) {
			model.addAttribute("error", "SDK Client Error");
			e.printStackTrace();
		} catch (IOException e) {
			model.addAttribute("error", "Error Uploading file");
			e.printStackTrace();
		}
		  
		  model.addAttribute("message", "File Succesffuly Upload");
		
		return "form";
	}

}
