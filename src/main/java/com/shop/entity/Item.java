package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //상품 코드

    private String itemNm;

    @Column(nullable = false)
    private int price;

    private int stockNumber;

    @Lob
    private String itemDetail;  //상품 상세 설명

    @Enumerated(EnumType.STRING)    //enum type매핑
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    //상품 업데이트 로직
    //엔티티 클래스에 비즈니스 로직을 추가한다면 조금 더 객체 지향적으로 코딩이 가능해지고, 코드를 재활용할 수 있어짐
    //엔티티 클래스에 비즈니스 로직을 추가하는 것은 엔티티 자체의 동작과 관련된 로직을 정의하는 것을 의미
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }
}