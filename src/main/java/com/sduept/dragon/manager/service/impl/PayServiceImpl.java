package com.sduept.dragon.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.sduept.dragon.manager.common.AliPayConstants;
import com.sduept.dragon.manager.dto.ProductOrderDto;
import com.sduept.dragon.manager.entity.Order;
import com.sduept.dragon.manager.service.OrderService;
import com.sduept.dragon.manager.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    private OrderService orderService;

    @Override
    public void payWithAlipay(ProductOrderDto productOrderDto, HttpServletResponse response) {
        // 将支付数据保存到数据库
        Order order = new Order();
        order.setOrderNo(productOrderDto.getOrderNo());
        order.setProductName(productOrderDto.getProductName());
        order.setAmount(new BigDecimal(productOrderDto.getAmount().toString()));
        order.setUpdateTime(new Date());
        order.setPayType("支付宝");
        boolean saveSuccessed = orderService.save(order);
        if (!saveSuccessed) {
            return;
        }

        // 构建支付数据信息
        Map<String, String> data = Maps.newHashMap();
        data.put("subject", productOrderDto.getProductName());
        data.put("out_trade_no", productOrderDto.getOrderNo());
        data.put("timeout_express", "30m");
        data.put("total_amount", productOrderDto.getAmount().toString());
        data.put("product_code", "FAST_INSTANT_TRADE_PAY");

        try {
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(AliPayConstants.ASYNC_NOTIFY_URL);
            request.setReturnUrl(AliPayConstants.SYNC_RETURN_URL);
            request.setBizContent(JSON.toJSONString(data));
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(alipayClient.pageExecute(request).getBody());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e1) {
            log.error("调用支付宝错误:" + e1);
        } catch (IOException e2) {
            log.error("向前端输出错误:" + e2);
        } catch (Exception e) {
            log.error("未知错误:" + e);
        }
    }

    @Override
    public void notify(HttpServletRequest request) {
        boolean checkScuessed = checkAlipayRsaV1(request);

        Order order = new Order();
        order.setUpdateTime(new Date());
        order.setTradeNo(request.getParameter("trade_no"));
        order.setOrderNo(request.getParameter("out_trade_no"));
        order.setCompleted(1);

        if (checkScuessed) {
            if ("TRADE_FINISHED".equals(request.getParameter("trade_status")) || "TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
                order.setTradeStatus(1);
            } else {
                order.setReason("支付失败，原因未知");
            }
        } else {
            order.setReason("支付宝签名校验失败");
        }
        orderService.update(order, new QueryWrapper<Order>().lambda().eq(Order::getOrderNo, order.getOrderNo()));
    }

    /**
     * 校验签名
     */
    private boolean checkAlipayRsaV1(HttpServletRequest request) {
        try {
            Map<String, String> params = Maps.newHashMap();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            return AlipaySignature.rsaCheckV1(params, AliPayConstants.ALIPAY_PUBLIC_KEY, AliPayConstants.CHARSET, AliPayConstants.SIGN_TYPE);
        } catch (AlipayApiException e) {
            log.error("支付宝签名验证错误，错误原因为：" + e);
            return false;
        } catch (Exception e1) {
            log.error("支付宝签名验证失败,错误原因为:" + e1);
            return false;
        }
    }
}
