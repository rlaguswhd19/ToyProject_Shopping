package shopping.back.hj.common;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidator {
	
	private String[] image_extensions = {"jpge","jpg","png","gif","rle","dib","bmp","tif","tiff","raw"};
	
	public void validate(MultipartFile[] files, Errors errors) {
		
		for (MultipartFile file : files) {
			String extension = getExtension(file.getOriginalFilename());
			
			boolean isOk = false;
			
			for (int i = 0; i < image_extensions.length; i++) {
				if(extension.equalsIgnoreCase(image_extensions[i])) {
					isOk = true;
					break;
				}
			}
			
			if(!isOk) {
				// TODO errors rejectValue
				errors.reject("Wrong File", "이미지 파일 이외의 파일은 업로드 할 수 없습니다.");
			}
		}
	}
	
	private String getExtension(String originalFileName) {

		int len = originalFileName.length();
		int idx = 0;

		for (int i = len - 1; i >= len - 10; i--) {
			char temp = originalFileName.charAt(i);
			if (temp == '.') {
				idx = i + 1;
				break;
			}
		}

		return originalFileName.substring(idx, len);
	}
}
