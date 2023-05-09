package com.shop.serivce;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional          //로직을 처리하다가 에러가 발생하였다면, 변경된 데이터를 로직을 수행하기 이전 상태로 콜백 시켜줌.
@RequiredArgsConstructor        //final이나 @NonNull이 붙은 필드에 생성자를 생성해줌.
                                //빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로 등록이 가능하다면 @Autowired 어노테이션 없이 의존성 주입이 가능
public class MemberService implements UserDetailsService {  //UserDetailsService(interface)를 구현함
    //UserDetailsService 인터페이스는 db에서 회원정보를 가져오는 역할을 담당함.
    private final MemberRepository memberRepository;    //
    private final PasswordEncoder passwordEncoder;
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {       //이미 가입된 회원의 경우 예외 발생
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  //UsderDetailService인터페이스의 loadUserByUsername() 메소드를 오버라이딩 함
                                                                                            //로그인 할 유저의 email을 파라미터로 전달 받음
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()           //UserDetail을 구현하고 있는 User 객체를 반환해줌. User 객체를 생성하기 위해 생성자로 회원의
                                        //이메일,비밀번호,role을 파라미터로 넘겨줌
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

    }

}
