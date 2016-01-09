package me.dusheiko.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PictureUploadController {
	public static final Resource PIRCTURE_DIR = new FileSystemResource("./pictures");
	
	@RequestMapping("upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs) throws IOException{
		if(file.isEmpty() || !isImage(file)) {
			redirectAttrs.addFlashAttribute("error", "Incorect file. Please upload a picture");
			return "redirect:/upload";
		}
		
		copyFileToPictures(file);
		
		return "profile/uploadPage";
	}
	
	private Resource copyFileToPictures(MultipartFile file) throws IOException{
		String fileName = file.getOriginalFilename();
		File tempFile = File.createTempFile("pic", getFileExtension(fileName), PIRCTURE_DIR.getFile());
		try (InputStream in = file.getInputStream();
			 OutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return new FileSystemResource(tempFile);
	}
	
	@RequestMapping(value = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource("/images/anonymous.png");
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(classPathResource.getFilename()));
		IOUtils.copy(classPathResource.getInputStream(), response.getOutputStream());
	}
	
	public static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}
}