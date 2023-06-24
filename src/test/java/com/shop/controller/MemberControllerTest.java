package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Address;
import com.shop.entity.Member;
import com.shop.serivce.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")

public class MemberControllerTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void addressTest() {
        Member member = new Member();
        List<Address> addresses = new ArrayList<>();
        Address address1 = new Address();
        address1.setAddress("123 Main Street");


        Address address2 = new Address();
        address2.setAddress("456 Elm Street");

        addresses.add(address1);
        addresses.add(address2);
        member.setAddresses(addresses);
        System.out.println("========================================================");
        System.out.println(member.getAddresses());

    }

//    public Member createMember(String email, String password) {
//        Address address = new Address();
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setEmail(email);
//        memberFormDto.setName("김동민");
//        memberFormDto.setAddress("서울시 마포구");
//        memberFormDto.setPassword(password);
//        Member member = Member.createMember(memberFormDto, passwordEncoder);
//        return memberService.saveMember(member);
//    }
//
//    @Test
//    @DisplayName("로그인 성공테스트")
//    public void loginSuccessTest() throws Exception {
//        String email = "test@email.com";
//        String password = "1234";
//        this.createMember(email, password);
//        mockMvc.perform(formLogin().userParameter("email")
//                .loginProcessingUrl("/members/login")
//                .user(email).password(password))
//                .andExpect(SecurityMockMvcResultMatchers.authenticated());
//
//    }
}
