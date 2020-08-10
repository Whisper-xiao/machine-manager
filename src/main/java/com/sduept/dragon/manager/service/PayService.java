package com.sduept.dragon.manager.service;

import com.sduept.dragon.manager.dto.ProductOrderDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PayService {

    void payWithAlipay(ProductOrderDto productOrderDto, HttpServletResponse response);

    void notify(HttpServletRequest request);
}
