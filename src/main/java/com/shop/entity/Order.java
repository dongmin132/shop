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

    @OneToMany(mappedBy = "orderId", cascade=CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)      //주문 상품 엔티티와 일대다 매핑을 함
                                        //외래키가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티가 됨.
                                        //Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정
                                        //"orderId" 속성을 적은 이유는 OrderItem에 있는 OrderId에 의해 관리된다는 의미로 해석(테스트 해보기 위해서)

                                        //cascade: 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정

    private List<OrderItem> orderItems = new ArrayList<>(); //하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑함
}
