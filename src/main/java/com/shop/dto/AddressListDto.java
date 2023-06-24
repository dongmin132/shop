package com.shop.dto;

import com.shop.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressListDto {

    private Long memberId;  //멤버 아이디
    private String name;

    private String email;

    private String password;

    private List<AddressDto> addressList = new ArrayList<>();

    public void addAddressDto(AddressDto addressDto){     //AddressDto 객체를 주소 리스트에 추가하는 메서드
        addressList.add(addressDto);
    }


}
