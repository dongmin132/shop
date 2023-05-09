package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString

public class Member extends BaseEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;       //동일한 값이 데이터 베이스에 들어올 수 없도록 unique 속성 지정
    private String password;
    private String address;

    //enum 타입을 엔티티의 속성으로 저장 가능
    //Enum을 사용할 때 기본적으로 순서가 저장되는데, enum의 순서가 바뀔 경우 문제가 발생할 수 있으므로 "ENumType.STRING" 옵션을
    //사용해서 String으로 저장하기를 권장.
    @Enumerated(EnumType.STRING)

    private Role role;


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {    //Member 엔티티를 생성하는 메소드
                                                                        //Member엔티티에 회원을 생성하는 메소드를 만들어 관리하면 코드가 변경되더라도 한 군데만 수정하면 됨

        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }
}
