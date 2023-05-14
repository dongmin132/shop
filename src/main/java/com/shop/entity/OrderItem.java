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
    private Order order;    //한번의 주문은 여러 주문 상품을 주문할 수 있으므로
                            //주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑을 먼저 설정함.

    private int orderPrice;

    private int count;

    //주문할 상품과 주문 수량을 통해 OrderItem 객체를 만드는 메소드
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);        //주문할 상품 세팅
        orderItem.setCount(count);      //주문 수량을 세팅
        orderItem.setOrderPrice(item.getPrice());       //현재 시간 기준으로 상품 가격을 주문 가격으로 세팅한다.
                                                        //상품 가격은 시간에 따라서 달라질 수 있다.
                                                        //쿠폰이나 할인을 적용하는 케이스도 있을 수 있음.(여기서는 x)

        item.removeStock(count);    //주문 수량만큼 상품의 재고 수량을 감소시킨다.
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice*count;    //주문 가격과 주문 수량을 곱해서 해당 상품을 주문한 총 가격을 계산하는 메소드.
    }
}
