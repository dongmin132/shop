package com.shop.entity;

import com.shop.constant.ItemSellStatus;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    //상품 코드

    private String itemNm;

    @Column(nullable = false)
    private int price;

    private int stockNumber;

    @Lob
    private String itemDetail;  //상품 상세 설명

    @Enumerated(EnumType.STRING)    //enum type매핑
    private ItemSellStatus itemSellStatus;  //상품 판매 상태


}