package com.shop.dto;

import com.shop.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String address;

    public AddressDto() {

    }
    public AddressDto(Address address) {
        this.address = address.getAddress();
    }
}
