package com.team324.user.web;

import com.team324.user.pojo.Address;
import com.team324.user.service.IAddressService;
import com.team324.common.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @GetMapping("{id}")
    public Address findAddressById(@PathVariable("id") Long id) {
        return addressService.getById(id);
    }

    @GetMapping("/uid/{userId}")
    public List<Address> findAddressByUserId(@PathVariable("userId") Long userId) {
        String currentId = BaseContext.getCurrentId();
        return addressService.query().eq(currentId, userId).list();
    }
}
