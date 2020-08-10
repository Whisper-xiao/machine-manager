package com.sduept.dragon.manager.config;

import com.alipay.api.DefaultAlipayClient;
import com.sduept.dragon.manager.common.AliPayConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PayConfig {

    @Bean(name = "alipayClient")
    public DefaultAlipayClient getAlipayClient() {
        return new DefaultAlipayClient(
                AliPayConstants.GATEWAY_URL,
                AliPayConstants.APP_ID,
                AliPayConstants.MERCHANT_PRIVATE_KEY,
                "json",
                AliPayConstants.CHARSET,
                AliPayConstants.ALIPAY_PUBLIC_KEY,
                AliPayConstants.SIGN_TYPE);
    }
}
