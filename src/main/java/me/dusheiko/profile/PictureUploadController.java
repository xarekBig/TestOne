package me.dusheiko.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.dusheiko.config.PictureUploadProperties;

@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
	private final Resource picturesDir;
	private final Resource anonymousPicture;
	
	@Autowired
	public PictureUploadController(PictureUploadProperties uploadProperties) {
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPicture();
	}
	
	@RequestMapping("upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException{
		if(file.isEmpty() || !isImage(file)) {
			redirectAttrs.addFlashAttribute("error", "Incorect file. Please upload a picture");
			return "redirect:/upload";
		}
		
		Resource picturePath = copyFileToPictures(file);
		model.addAttribute("picturePath", picturePath);
		
		return "profile/uploadPage";
	}
	
	private Resource copyFileToPictures(MultipartFile file) throws IOException{
		String fileExtention = getFileExtension(file.getOriginalFilename());
		File tempFile = File.createTempFile("pic", fileExtention, picturesDir.getFile());
		try (InputStream in = file.getInputStream();
			 OutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return new FileSystemResource(tempFile);
	}
	
	@ModelAttribute("picturePath")
	public Resource picturePath() {
		return anonymousPicture;
	}
	
	@RequestMapping(value = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response, @ModelAttribute("picturePath") Resource picturePath) throws IOException {
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
		IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
	}
	
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(IOException exception) {
		ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
		modelAndView.addObject("error", exception.getMessage());
		return modelAndView;
	}
	
	public static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}
	
	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}
}
