package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String Email);       //회원 가입 시 중복된 회원이 있는지 검사하기 위해
                                            //이메일로 회원을 검사할 수 있도록 쿼리 메소드 작성

    @Query("select o from Member o "+
            "where o.email = :email"
    )
    List<Member> findMembers(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Member o " +
            "where o.email = :email"
    )
    Long countMember(@Param("email") String email);  //로그인한 회원의 주소가 몇개인지 조회
    
    
}
