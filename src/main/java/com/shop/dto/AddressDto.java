package com.shop.dto;

import com.shop.entity.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDto {
    private String address;
    private Long id;

    public AddressDto() {

    }
    public AddressDto(Address address) {
        this.address = address.getAddress();
    }

    public static AddressDto toAddressDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress(address.getAddress());
        addressDto.setId(address.getId());

        return addressDto;
    }
}
