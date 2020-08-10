package com.sduept.dragon.manager.controller;

import com.sduept.dragon.manager.dto.ProductOrderDto;
import com.sduept.dragon.manager.service.PayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "订单支付接口")
@RestController
@RequestMapping("/payapi/")
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("alipay/pay")
    public void payWithAlipay(ProductOrderDto productOrderDto, HttpServletResponse response) {
        payService.payWithAlipay(productOrderDto, response);
    }

    @PostMapping("alipay/async-notify")
    public void notify(HttpServletRequest request) {
        payService.notify(request);
    }

    @GetMapping("alipay/sync-notify")
    public String notifyWithSync(HttpServletRequest request) {
        return "支付完成";
    }
}
