package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class OrderTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상품 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    public Order createOrder() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }


    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10); //수량
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);   //아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담아줌

        }

        orderRepository.saveAndFlush(order);     //order 엔티티를 저장하면서 강제로 flush를 호출하여 영속성 컨텍스트에 있는 객체들을 데이터베이스에 반영
        em.clear();     //영속성 컨텍스트의 상태를 초기화

        Order saveOrder = orderRepository.findById(order.getId())   //영속성 컨테스트를 초기화했기 때문에 데이터베이스에서 주문 엔티티를 조회함
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, saveOrder.getOrderItems().size());  //itemOrder 엔티티 3개가 실제로 디비에 저장되었는 지 검사


    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazeLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();        //기존에 만들었던 주문 생성 메소드를 이용하여 주문 데이터를 저장
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class: " + orderItem.getOrder().getClass());//orderItem 엔티티에 있는 order 객체의 클래스를 출력
                                                                                //Order클래스가 출력됨.
                                                        //지연 로딩으로 설정하면 실제 엔티티 대신에 프록시 객체를 넣어둠.
        System.out.println("=========================");
        orderItem.getOrder().getOrderDate();//주문일을 조회할 때 select 쿼리문이 실행되는것을 확인할수 있음
        System.out.println("============================");
    }
}