package shopping.back.hj.dimages;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DimageValidator {
	
	private String[] image_extensions = {"jpge","jpg","png","gif","rle","dib","bmp","tif","tiff","raw"};
	
	public boolean validate(MultipartFile[] files) {
		
		for (MultipartFile file : files) {
			String extension = getExtension(file.getOriginalFilename());
			
			boolean isOk = false;
			
			for (int i = 0; i < image_extensions.length; i++) {
				if(extension.equalsIgnoreCase(image_extensions[i])) {
					isOk = true;
					break;
				}
			}
			
			// image파일이 아니면 false
			if(!isOk) {
				return isOk;
			}
		}
		
		return true;
	}
	
	private String getExtension(String originalFileName) {

		int len = originalFileName.length();
		int idx = 0;

		for (int i = len - 1; i >= 0; i--) {
			char temp = originalFileName.charAt(i);
			if (temp == '.') {
				idx = i + 1;
				break;
			}
		}

		return originalFileName.substring(idx, len);
	}
}
