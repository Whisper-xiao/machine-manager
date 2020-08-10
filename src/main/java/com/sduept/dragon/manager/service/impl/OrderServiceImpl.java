package com.sduept.dragon.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sduept.dragon.manager.entity.Order;
import com.sduept.dragon.manager.mapper.OrderMapper;
import com.sduept.dragon.manager.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiao guang zhen
 * @since 2020-07-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
