package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
//데이터를 주고받을 때는 entity 클래스 자체를 반환하면 안 되고 데이터 전달용 객체를 생성해서 사용해야 함. 데이터베이스의 설계를 외부에
//노출할 필요도 없으며,요청과 응답객체가 항상 엔티티와 같지 않기 때문


public class MemberFormDto {
    @NotBlank(message="이름은 필수 입력 값입니다")          //javax.validation 어노테이션
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 값입니다")
    private String address;


}
