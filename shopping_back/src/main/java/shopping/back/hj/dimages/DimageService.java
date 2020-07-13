package shopping.back.hj.dimages;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DimageService {

	public ResponseEntity<?> uploadBasic(MultipartFile[] files) {
		return null;
	}
	
	private String writeFile(MultipartFile[] files) throws IllegalStateException, IOException {
		// dress_images path
		String basePath = "C:\\Users\\rlagu\\OneDrive\\바탕 화면\\개발\\hjwork\\ToyProject_Shopping\\shopping_back\\src\\main\\resources\\static\\images/basic";
		
		File dir = new File(basePath);

		if (!dir.exists()) {
			dir.mkdir();
		}
		
		basePath += "/" +LocalDate.now();
		
		File timeDir = new File(basePath);
		
		if (!timeDir.exists()) {
			timeDir.mkdir();
		}
		
		StringBuilder image_paths = new StringBuilder();
		
		for (MultipartFile file : files) {
			// 파일 path
			image_paths.append(file.getOriginalFilename()+"/");
			
			String fileName = basePath + "/" + file.getOriginalFilename();
			
			// 빈파일 생성
			File saveImage = new File(fileName);

			// 파일 복사 multipartfile -> file
			file.transferTo(saveImage);
		}
		
		return image_paths.toString();
	}
}
