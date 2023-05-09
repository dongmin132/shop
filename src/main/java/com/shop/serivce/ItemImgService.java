package com.shop.serivce;


import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

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

            imgUrl = "/image/item" + imgName;           //저장한 상품 이미지를 불러올 경로를 설정함.
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);     //oriImgName: 업로드했던 상품 이미지 파일의 원래 이름,
                                                                //imgName: 실제 로컬에 저장된 상품 이미지 파일의 이름,
                                                                //imgUrl: 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는경로
        itemImgRepository.save(itemImg);
    }

}
