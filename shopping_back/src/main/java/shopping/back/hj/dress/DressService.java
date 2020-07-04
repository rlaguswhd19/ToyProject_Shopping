package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

	public ResponseEntity createDress(DressDto dressDto, MultipartFile[] files) throws IllegalStateException, IOException {

		Dress dress = modelMapper.map(dressDto, Dress.class);
		Dress newDress = dressRepository.save(dress);

		// TODO 여기서 dressDto의 이미지를 저장하고 이미지 경로를 링크로 만들어 추가한다.

		// 파일저장
		
		int cnt = 1; // 파일 번호
		
		// 옷등록 번호로 폴더 만들기
		String basePath = "C:\\Users\\rlagu\\OneDrive\\바탕 화면\\개발\\hjwork\\ToyProject_Shopping\\shopping_back\\src\\main\\resources\\static\\dress_images/"+dress.getId();
		
		File dir = new File(basePath);

		// 폴더 생성
		if (!dir.exists()) {
			dir.mkdir();
		}

		for (MultipartFile file : files) {
			// 파일 path
			String fileName = basePath + "/" + file.getOriginalFilename(); 
			
			// 빈파일 생성
			File saveImage = new File(fileName);
			
			// 파일 복사 multipartfile -> file
			file.transferTo(saveImage);
		}
		
		DressModel dressModel = new DressModel(newDress);

		WebMvcLinkBuilder selfLinkBuilder = linkTo(DressController.class).slash(newDress.getId());
		URI createUri = selfLinkBuilder.toUri();

		// link
		dressModel.add(new Link("/docs/dress.html#resources-create-Dress").withRel("profile"));
		dressModel.link_Lists(dressModel);

		// TODO account 등급에 따라 지정
		dressModel.link_Update(dressModel);

		// TODO 이미지 링크 추가하기 나중에
		return ResponseEntity.created(createUri).body(dressModel);
	}
}
