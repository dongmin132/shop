package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;      //한 명의 회원은 여러 번 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일 단방향 매핑을 함

    private LocalDateTime orderDate;    //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;     //주문 상태


    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)      //주문 상품 엔티티와 일대다 매핑을 함
                                        //외래키가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티가 됨.
                                        //Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정(양방향)
                                        //"orderId" 속성을 적은 이유는 OrderItem 에 있는 OrderId에 의해 관리된다는 의미로 해석(테스트 해보기 위해서)

                                        //cascade: 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정

    private List<OrderItem> orderItems = new ArrayList<>(); //하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑함


    //생성한 주문 상품 객체를 이용하여 주문 객체를 만드는 메소드
    public void addOrderItem(OrderItem orderItem) {     //orderItems에는 주문 상품 정보들을 담게됨. orderItem 객체를 order 객체의 orderItems에 추가함.
        orderItems.add(orderItem);
        orderItem.setOrder(this);     //Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계 이므로, orderItem 객체에도 order 객체를 세팅함

    }



    //createOrder 메서드는 주어진 회원과 주문 상품 목록을 사용하여 새로운 Order 객체를 생성하고,
    // 각 주문 상품을 Order 객체에 추가하는 역할을 합니다. 이렇게 생성된 Order 객체는 이후 다른 곳에서 사용될 수 있습니다.
    // 그러나 현재 코드에서는 Order 객체를 반환하지 않고 있으므로 메서드의 반환 타입이 void입니다.
    // 만약 반환 값을 사용하려면 메서드의 반환 타입을 Order로 변경해야 합니다.
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);                        //해당 주문을 한 회원의 정보를 설정
        for (OrderItem orderItem : orderItemList) {     //상품 페이지에서는 1개의 상품을 주문하지만, 장바구니 페이지에서는 한 번에 여러개의 상품을 주문할 수 있음.
                                                        //따라서 여러 개의 주문 상품을 담을 수 있도록 리스트형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를추가함
                                                        //orderItemList 는  여러 개의 주문 상품을 담고 있는 리스트
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice() {
        int totalPrice=0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
