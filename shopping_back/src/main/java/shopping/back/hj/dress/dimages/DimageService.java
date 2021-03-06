package shopping.back.hj.dress.dimages;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.config.StaticResourcesProperties;

@Service
public class DimageService {

	@Autowired
	DimageRepository dimageRepository;

	@Autowired
	private StaticResourcesProperties staticResourcesProperties;

	public ResponseEntity<?> createDimage(MultipartFile[] files, int repIdx) throws IllegalStateException, IOException {
		// 이미지를 생성한다.
		Dimage dimage = Dimage.builder()
				.image_repIdx(repIdx)
				.build();

		Dimage newDimage = dimageRepository.save(dimage);
		
		// 파일을 쓰면서 String을 받아서 이미지 도메인에 저장
		String filesNames = writeFiles(files, newDimage.getId());
		
		if(filesNames == null || filesNames.equals("")) {
			return ResponseEntity.badRequest().build();
		}

		newDimage.setImage_files(filesNames);

		// 갱신
		dimageRepository.flush();

		// Location 생성
		WebMvcLinkBuilder selfLinkBuilder = linkTo(DimageController.class).slash(newDimage.getId());
		URI createUri = selfLinkBuilder.toUri();

		return ResponseEntity.created(createUri).body(newDimage);
	}
	
	public Dimage findById(Long id) {
		
		Optional<Dimage> optionalDimage = dimageRepository.findById(id);
		
		if(optionalDimage.isEmpty()) {
			return null;
		}
		
		Dimage dimage = optionalDimage.get();
		
		return dimage;
	}

	private String writeFiles(MultipartFile[] files, Long id) throws IllegalStateException, IOException {
		String basePath = staticResourcesProperties.getSave_location();

		File dir = new File(basePath);

		if (!dir.exists()) {
			dir.mkdir();
		}

		basePath += "/" + id;
		File idDir = new File(basePath);

		// id로 된것이 없으면 파일을 만든다.
		if (!idDir.exists()) {
			idDir.mkdir();
		}


		for (MultipartFile file : files) {

			String fileName = basePath + "/" + file.getOriginalFilename();

			// 빈파일 생성
			File saveImage = new File(fileName);

			// 파일 복사 multipartfile -> file
			file.transferTo(saveImage);
		}
		
		File[] FileList = idDir.listFiles();
		StringBuilder filesName = new StringBuilder();
		for (int i = 0; i < FileList.length; i++) {
			filesName.append(FileList[i].getName() + "/");
		}
		
		return filesName.toString();
	}

	public ResponseEntity<?> deleteDimage(Dimage dimage) {
		String basePath = staticResourcesProperties.getSave_location() + "/" + dimage.getId();

		File dir = new File(basePath);

		// 없으면 컨텐츠가 없다고 보냄..
		if(!dir.exists()) {
			return ResponseEntity.noContent().build();
		}
		
		// 폴더의 파일 리스트 가져오기
		File[] file_lists = dir.listFiles();
		
		// 삭제
		for (File file : file_lists) {
			file.delete();
		}
		
		// 폴더, db 삭제
		dir.delete();
		dimageRepository.delete(dimage);
		
		return ResponseEntity.ok().build();
	}

}
