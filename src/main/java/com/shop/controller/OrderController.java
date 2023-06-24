package com.shop.controller;

import com.shop.dto.*;
import com.shop.entity.Address;
import com.shop.entity.Member;
import com.shop.repository.AddressRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import com.shop.serivce.ItemService;
import com.shop.serivce.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final AddressRepository addressRepository;

//    @PostMapping("/order")
    //@ResponseBody 어노테이션이 메서드 선언에 위치할 경우(지금 경우), 전체 응답을 나타내는 객체를 반환하며(ResponseEntity),
    // 메서드 내부에 위치할 경우, 해당 메서드가 반환하는 값 자체가 응답 본문으로 사용됩니다.
//    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult,
//                                              Principal principal) {
//        if (bindingResult.hasErrors()) {   //주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는지 검사.
//            StringBuilder sb = new StringBuilder();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            for (FieldError fieldError : fieldErrors) {
//                sb.append(fieldError.getDefaultMessage());
//            }
//            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);   //에러 정보를 ResponseEntity 객체에 담아서 반환
//        }
//
//        String email = principal.getName();     //현재 로그인 유저의 정보를 얻기 위해 @Controller 어노테이션이 선언된 클래스에서 메소드 인자로 principal 객체를 넘겨 줄 경우
//        //해당 객체에 직접 접근할 수 있다.
//        //prinicipal 객체에서 현재 로그인한 회원의 이메일 정보를 조회한다.
//        Long orderId;
//        try {
//            orderId = orderService.order(orderDto, email);  //화면으로부터 넘어오는 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출
//        } catch (Exception e) {
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<Long>(orderId, HttpStatus.OK);        //결과값으로 생성된 주문 정보와 요청이 성공했다는 HTTP 응답 상태 코드 반환
//    }

    @GetMapping("/orders/orderForm")
    public String orderForm(Principal principal, Model model,
                            @RequestParam(value = "itemId") Long itemId) {
        String email = principal.getName();
        Member member = memberRepository.findByEmail(email);
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        model.addAttribute("member", member);
        model.addAttribute("item", itemFormDto);
        return "orders/orderForm";
    }


    @GetMapping("/orders/payment")
    public String orders(Model model) {
        Address address = new Address();
        model.addAttribute("addressDto", new AddressDto());
        return "orders/orderpayment";
    }

    @PostMapping("/orders/new")
    public String ordersRegister(AddressDto addressDto, Principal principal) {
        System.out.println("======================================================오류========================================================================");
        String email = principal.getName();
        Member member = memberRepository.findByEmail(email);
        Address address = Address.createAddress(member, addressDto);
        addressRepository.save(address);
        member.addAddress(address);
        return "redirect:/";
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String addressList(@PathVariable("page") Optional<Integer> page,
                              Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 1); //한페이지에 보이는 주문 리스트

        Page<AddressListDto> addressListDtoList =
                orderService.getAddressList(principal.getName(), pageable);  //현재 로그인한 이메일과 페이징 객체를 파라미터로 전달하여 화면에 전달한 주문 목록 데이터 리턴
        model.addAttribute("addresses", addressListDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "orders/addressList";
    }
}

