package com.team324.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team324.user.mapper.AddressMapper;
import com.team324.user.pojo.Address;
import com.team324.user.service.IAddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}