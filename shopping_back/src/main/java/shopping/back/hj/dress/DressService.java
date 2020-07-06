package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DressService {

	@Autowired
	private DressRepository dressRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity createDress(DressDto dressDto, MultipartFile[] files)
			throws IllegalStateException, IOException {

		Dress dress = modelMapper.map(dressDto, Dress.class);
		Dress newDress = dressRepository.save(dress);
		
		// TODO 여기서 dressDto의 이미지를 저장하고 이미지 경로를 링크로 만들어 추가한다.
		
		// dress_images path
		String basePath = "C:\\Users\\rlagu\\OneDrive\\바탕 화면\\개발\\hjwork\\ToyProject_Shopping\\shopping_back\\src\\main\\resources\\static\\dress_images/"
				+ newDress.getId();

		
		File dir = new File(basePath);

		// dress id로 폴더 생성
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		StringBuilder image_paths = new StringBuilder();
		
		for (MultipartFile file : files) {
			// 파일 path
			image_paths.append(file.getOriginalFilename()+"/");
			
			//String extension = getExtension(file.getOriginalFilename());

			String fileName = basePath + "/" + file.getOriginalFilename();
			
			// 빈파일 생성
			File saveImage = new File(fileName);

			// 파일 복사 multipartfile -> file
			file.transferTo(saveImage);
		}
		
		// file 이름들 저장
		newDress.setImage_paths(image_paths.toString());
		
		newDress = dressRepository.save(newDress);
		// db 저장
		
		DressModel dressModel = new DressModel(newDress);
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(DressController.class).slash(newDress.getId());
		URI createUri = selfLinkBuilder.toUri();

		// link
		dressModel.add(new Link("/docs/dress.html#resources-create-Dress").withRel("profile"));
		dressModel.link_Lists(dressModel);

		// TODO account 등급에 따라 지정
		dressModel.link_Update(dressModel);

		// TODO 이미지 링크 추가하기 나중에
		dressModel.link_imagePath(dressModel, newDress.getId());
		
		return ResponseEntity.created(createUri).body(dressModel);
	}

	private String getExtension(String originalFileName) {

		int len = originalFileName.length();
		int idx = 0;

		for (int i = len - 1; i >= len - 10; i--) {
			char temp = originalFileName.charAt(i);
			if (temp == '.') {
				idx = i;
				break;
			}
		}

		return originalFileName.substring(idx, len);
	}
}
