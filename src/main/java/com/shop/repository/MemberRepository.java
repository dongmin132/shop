package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String Email);       //회원 가입 시 중복된 회원이 있는지 검사하기 위해
                                            //이메일로 회원을 검사할 수 있도록 쿼리 메소드 작성

    @Query("select o from Member o "+
            "where o.email = :email"
    )


}
