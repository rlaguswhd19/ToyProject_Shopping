package shopping.back.hj.dimages;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.dress.DressController;

@Service
public class DimageService {

	@Autowired
	DimageRepository dimageRepository;

	public ResponseEntity<?> uploadBasic(MultipartFile[] files) throws IllegalStateException, IOException {
		// 이미지를 생성한다.
		Dimage dimage = Dimage.builder().build();

		Dimage newDimage = dimageRepository.save(dimage);
		
		// 파일을 쓰면서 String을 받아서 이미지 도메인에 저장
		String filesNames = writeFile(files, newDimage.getId());
		
		newDimage.setImage_files(filesNames);
		
		// 갱신
		dimageRepository.save(newDimage);

		// Location 생성
		WebMvcLinkBuilder selfLinkBuilder = linkTo(DimageController.class).slash(newDimage.getId());
		URI createUri = selfLinkBuilder.toUri();

		return ResponseEntity.created(createUri).body(newDimage);
	}

	private String writeFile(MultipartFile[] files, Long id) throws IllegalStateException, IOException {
		// dress_images path
		String basePath = "C:\\Users\\rlagu\\OneDrive\\바탕 화면\\개발\\hjwork\\ToyProject_Shopping\\shopping_back\\src\\main\\resources\\static\\images\\basic";
		
//		String basePath = "./src/main/resources/static/images/basic";
		
		File dir = new File(basePath);
		
		if (!dir.exists()) {
			dir.mkdir();
		}

		basePath += "/" + id;

		File timeDir = new File(basePath);

		// id로 된것이 없으면 파일을 만든다.
		if (!timeDir.exists()) {
			timeDir.mkdir();
		}

		StringBuilder files_name = new StringBuilder();

		for (MultipartFile file : files) {
			// 파일 path
			files_name.append(file.getOriginalFilename() + "/");

			String fileName = basePath + "/" + file.getOriginalFilename();

			// 빈파일 생성
			File saveImage = new File(fileName);

			// 파일 복사 multipartfile -> file
			file.transferTo(saveImage);
		}

		return files_name.toString();
	}
}
