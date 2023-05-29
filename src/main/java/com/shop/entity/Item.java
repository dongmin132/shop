package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //상품 코드

    private String itemNm;

    @Column(nullable = false)
    private int price;

    private int stockNumber;        //재고 수량

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

    //상품을 주문할 경우 상품의 재고를 감소시키는 로직
    // 엔티티 클래스 안에 비즈니스 로직을 메소드롤 작성하면
    // 코드의 재사용과 데이터의 변경 포인트를 한군데로 모을 수 있음.
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;     //상품의 재고 수량에서 주문 후 남은 재고 수량을 구한다.
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량: " + this.stockNumber + ")");
                            //상품의 재고가 주문 수량보다 적을 경우 재고 부족 예외를 발생 시킨다.
        }
        this.stockNumber = restStock;       //주문 후 남은 재고 수량을 현재 재고 값으로 할당한다.
        System.out.println("현재 재고 수량은 "+this.stockNumber+"입니다");
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}