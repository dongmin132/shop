package com.shop.serivce;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static com.shop.entity.Member.createMember;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional          //테스트 클래스에 @Transactional 어노테이션을 선언할경우, 테스트 실행 후 롤백 처리가 됨.
//이를 통해 같은 메소드를 반복적으로 테스트 가능
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    PasswordEncoder passwordEncoder;
//    public Member createMember() {      //회원정보를 입력한 멤버 엔티티를 만드는 메소드 작성
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setEmail("test@email.com");
//        memberFormDto.setName("홍길동");
//        memberFormDto.setAddress("asdasdasd");
//        memberFormDto.setPassword("1234");
//        return Member.createMember(memberFormDto,passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("회원가입테스트")
//    public void saveMemberTest() {
//        Member member = createMember();
//        Member savedMember = memberService.saveMember(member);
//
//        assertEquals(member.getEmail(),savedMember.getEmail());
//        assertEquals(member.getName(), savedMember.getName());
//        assertEquals(member.getAddress(), savedMember.getAddress());
//        assertEquals(member.getPassword(), savedMember.getPassword());
//        assertEquals(member.getRole(),savedMember.getRole());
//    }
//
//    @Test
//    @DisplayName("중복 회원 가입 테스트")
//    public void saveDuplicateMemberTest() {
//        Member member1 = createMember();
//        Member member2 = createMember();
//        memberService.saveMember(member1);
//        Throwable e = assertThrows(IllegalStateException.class, () -> {
//            memberService.saveMember(member2); });  //Assertions클래스의 assertThrows 메소드를 이용하면 에외 처리 테스트 가능
//                                                    //첫번째 파라미터에 발생할 예외 타입을 넣어줌.
//
//        assertEquals("이미 가입된 회원입니다", e.getMessage());
//    }
}