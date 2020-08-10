package com.sduept.dragon.manager.dto;

/**
 * 商品订单模型
 */
public class ProductOrderDto {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品金额
     */
    private Double amount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
