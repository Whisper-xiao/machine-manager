package com.sduept.dragon.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sduept.dragon.manager.entity.Customer;
import com.sduept.dragon.manager.mapper.CustomerMapper;
import com.sduept.dragon.manager.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiao guang zhen
 * @since 2020-04-05
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
