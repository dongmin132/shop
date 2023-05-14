package com.shop.serivce;


import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    @Value("${itemImgLocation}")        //application.properties 파일에 등록한 itemImgLocation 값을 불러와서 변수에 넣어줌
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
                                                                //사용자가 상품의 이미지를 등록했다면 저장할 경로와 파일의 이름, 파일의 바이트 배열을 파일 업로드 파라미터로 uploadifle 메소드를 호출
                                                                //호출 결과 로컬에 저장된 파일의 이름을 imgName 변수에 저장합니다.

            imgUrl = "/images/item/" + imgName;           //저장한 상품 이미지를 불러올 경로를 설정함.
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);     //oriImgName: 업로드했던 상품 이미지 파일의 원래 이름,
                                                                //imgName: 실제 로컬에 저장된 상품 이미지 파일의 이름,
                                                                //imgUrl: 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는경로
        itemImgRepository.save(itemImg);
    }

    //상품 이미지 수정 (변경 감지 기능 사용)
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if (!itemImgFile.isEmpty()) {       //상품 이미지를 수정한 경우 상품 이미지를 업데이트 한다
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)    //상품 이미지 아이디를 이용하여 기존에 저장했던 상품 이미지 엔티티를 조회한다.
                    .orElseThrow(EntityExistsException::new);

            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {      //기존에 등록된 상품 이미지 파일이 있을 경우 해당 파일을 삭제.
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());   //업데이트한 상품 이미지 파일을 업로드
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);    //변경된 상품 이미지 정보를 세팅한다.
                                                                        //itemImgRepository.save()로직을 호출하지 않고
                                                                        //savedItemImg 엔티티는 현재 영속 상태이므로 데이터를 변경하는 것만으로 변경 감지 기능이 동작하게 된다.
                                                                        //트랜잭션이 끝날 때 update 쿼리가 실행된다.

        }
    }
}
