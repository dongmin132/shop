package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.AddressDto;
import com.shop.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
@ToString
public class Address extends BaseEntity{


        @Id
        @Column(name="address_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        private Member member;    //한번의 주문은 여러 주문 상품을 주문할 수 있으므로
        //주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑을 먼저 설정함.

        @Column(nullable = false)
        private String address;


        public static Address createAddress(Member member, AddressDto addressDto) {
                Address address = new Address();
                address.setMember(member);
                address.setAddress(addressDto.getAddress());            //Setter를 이용

                return address;
        }
        public void updateForm(AddressDto addressDto) {
                this.address = addressDto.getAddress();                 //this를 이용

        }

}
