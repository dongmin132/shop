package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.serivce.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;  //불변의 이유
    private final PasswordEncoder passwordEncoder;


    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        System.out.println("------------------memberform!------------------------------");
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                             Model model) { //검증하려는 객체의 앞에 @valid 어노테이션을 선언하고, 파라미터로 bindingResult 객체를 추가합니다. 검사 후 결과는 bindingResult에 담아줌.
        if (bindingResult.hasErrors()) {        //vindingResult.hasErrors()를 호출하여 에러가있다면 회원가입 페이지로 이동함
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage()); //회원가입 시 중복회원 가입 예외가 발생하면 에러 메시지를 뷰로 전달함.
            return "member/memberForm";
        }
        return "redirect:/";

    }

    @GetMapping("/login")
    public String loginMember() {

        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
