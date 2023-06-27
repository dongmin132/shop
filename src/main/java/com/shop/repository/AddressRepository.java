package com.shop.repository;

import com.shop.entity.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("select o from Address o " +
            "where o.member.email=:email "
    )
    List<Address> findMembers(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Address o " +
            "where o.member.email = :email"
    )
    Long countMember(@Param("email") String email);  //로그인한 회원의 주소가 몇개인지 조회
}
