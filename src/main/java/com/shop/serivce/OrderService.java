package com.shop.serivce;

import com.shop.dto.AddressDto;
import com.shop.dto.AddressListDto;
import com.shop.dto.OrderDto;
import com.shop.entity.*;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())       //주문할 상품을 조회
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);    //현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보를 조회

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem =
                OrderItem.createOrderItem(item, orderDto.getCount());   //주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티 생성
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<AddressListDto> getAddressList(String email, Pageable pageable) {
        List<Member> members = memberRepository.findMembers(email, pageable);

        List<AddressListDto> addressListDtos = new ArrayList<>();
        Long totalCount = memberRepository.countMember(email);

        for(Member member : members) {
            AddressListDto addressListDto = new AddressListDto(member);
            List<Address> addresses = member.getAddresses();
            for (Address address : addresses) {
                AddressDto addressDto = new AddressDto(address);//이거는 new addressDto를 따로 만들어야 될것 같기도?
                addressListDto.addAddressDto(addressDto);
            }
            addressListDtos.add(addressListDto);
        }
        return new PageImpl<AddressListDto>(addressListDtos,pageable,totalCount);
    }
}
