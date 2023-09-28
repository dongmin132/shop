package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "cart")

//장바구니 엔티티가 일방적으로 회원 엔티티를 참조하고 있음.
//장바구니 엔티티가 회원 엔티티를 참조하는 일대일 단방향 매핑임.
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)       //회원 엔티티와 일대일로 매핑
    @JoinColumn(name = "member_id")     //name 속성에는 매핑할 외래키의 이름을 설정함.
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
