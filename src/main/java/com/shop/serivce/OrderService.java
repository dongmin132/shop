package com.shop.serivce;

import com.shop.dto.AddressDto;
import com.shop.dto.AddressListDto;
import com.shop.dto.OrderDto;
import com.shop.entity.*;
import com.shop.repository.AddressRepository;
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
    private final AddressRepository addressRepository;

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
        List<Address> addresses = addressRepository.findMembers(email, pageable);
        Long totalCount = addressRepository.countMember(email);
        List<AddressListDto> addressListDtos = new ArrayList<>();

        for (Address address : addresses) {
            AddressListDto addressListDto = new AddressListDto(address.getMember());
            AddressDto addressDto = new AddressDto(address);
            addressListDto.addAddressDto(addressDto);

            addressListDtos.add(addressListDto);
        }

        for (AddressListDto addressListDto : addressListDtos) {
            System.out.println(addressListDto); // AddressListDto의 toString() 메서드가 정의되어 있다면 해당 메서드가 호출되어 값이 출력됩니다.
            // 또는 원하는 속성을 개별적으로 출력할 수도 있습니다.
            // ...
        }

        return new PageImpl<AddressListDto>(addressListDtos,pageable,totalCount);
    }
    public void setAddressForMember(Address address,String email) {
        Member member = memberRepository.findByEmail(email);
        Address existingAddress = addressRepository.findById(address.getId()).
                orElseThrow(EntityNotFoundException::new);
        member.setAddressFromAddressList(existingAddress);
    }

    public void setAddressUpdate(AddressDto addressDto) {
        //Member member = memberRepository.findByEmail(email);
        Address address = addressRepository.findById(addressDto.getId()).
                orElseThrow(EntityNotFoundException::new);
        address.updateForm(addressDto);
    }
}
