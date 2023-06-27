package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString

public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;       //동일한 값이 데이터 베이스에 들어올 수 없도록 unique 속성 지정
    private String password;

    //    private String address;
//
//    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)      //주문 상품 엔티티와 일대다 매핑을 함
//    //외래키가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티가 됨.
//    //Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정(양방향)
//    //"orderId" 속성을 적은 이유는 OrderItem 에 있는 OrderId에 의해 관리된다는 의미로 해석(테스트 해보기 위해서)
//
//    //cascade: 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정
//    private List<Address> addresses = new ArrayList<>(); //하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑함

    private String address;

    // 추가 주소들
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();


    //enum 타입을 엔티티의 속성으로 저장 가능
    //Enum을 사용할 때 기본적으로 순서가 저장되는데, enum의 순서가 바뀔 경우 문제가 발생할 수 있으므로 "ENumType.STRING" 옵션을
    //사용해서 String으로 저장하기를 권장.
    @Enumerated(EnumType.STRING)
    private Role role;


    public void addAddress(Address address) {     //orderItems에는 주문 상품 정보들을 담게됨. orderItem 객체를 order 객체의 orderItems에 추가함.
        addresses.add(address);
        address.setMember(this);

    }


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {    //Member 엔티티를 생성하는 메소드
        //Member엔티티에 회원을 생성하는 메소드를 만들어 관리하면 코드가 변경되더라도 한 군데만 수정하면 됨

        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        //member.updateDefaultAddress(newDefaultAddress);

        member.setAddress(memberFormDto.getAddress());


        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);


        Address address = new Address();
        address.setAddress(memberFormDto.getAddress());
        member.addAddress(address);

        return member;
    }


    public void setAddressFromAddressList(Address selectedAddress) {
        this.address = selectedAddress.getAddress();
    }

}
