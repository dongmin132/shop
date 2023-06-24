//package com.shop.entity;
//
//import com.shop.dto.MemberFormDto;
//import com.shop.repository.CartRepository;
//import com.shop.repository.MemberRepository;
//import com.shop.serivce.MemberService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityNotFoundException;
//import javax.persistence.PersistenceContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//
//@TestPropertySource(locations = "classpath:application-test.properties")
//class CartTest {
//    @Autowired
//    CartRepository cartRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @PersistenceContext
//    EntityManager em;
//
//    public Member createMember() {      //회원정보를 입력한 멤버 엔티티를 만드는 메소드 작성
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setEmail("test@email.com");
//        memberFormDto.setName("홍길동");
//        memberFormDto.setAddress("asdasdasd");
//        memberFormDto.setPassword("1234");
//        return Member.createMember(memberFormDto, passwordEncoder);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
//    public void findCartAndMemberTest() {
//        Member member = createMember();
//        memberRepository.save(member);
//
//        Cart cart = new Cart();
//        cart.setMember(member);
//        cartRepository.save(cart);
//
//        em.flush();     //영속성: 데이터를 일시적인 메모리에서 벗어나 지속적인 저장소에 보존하는 능력을 의미하며, 데이터의 영구 보존과 지속적인 접근을 가능하게 합
//                        //영속성 컨텍스트에 데이터를 저장 후 트랜잭션이 끝날 때 flush()를 호출하여 데이터베이스에 반영
//                        //회원, 장바구니 엔티티를 영속성 컨텍스트에 저장 후 엔티티 매니저로부터 강제로 flush()를 호출하여 데이터베이스에 반영
//        em.clear();
//
//        Cart savedCart = cartRepository.findById(cart.getId())      //저장된 장바구니 엔티티 조회
//                .orElseThrow(EntityNotFoundException::new);
//        assertEquals(savedCart.getMember().getId(), member.getId());
//    }
//}