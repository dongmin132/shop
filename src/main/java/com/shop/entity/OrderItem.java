package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;      //하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로
                            //주문 상품 기준으로 다대일 단방향 매핑을 설정함.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order orderId;    //한번의 주문은 여러 주문 상품을 주문할 수 있으므로
                            //주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑을 먼저 설정함.

    private int orderPrice;

    private int count;

}
