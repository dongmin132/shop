package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);        // 매개 변수로 넘겨준 상품 아이디를 가지며, 상품 이미지 아이디의 오름차 순으로 가져오는 쿼리메소드
}
