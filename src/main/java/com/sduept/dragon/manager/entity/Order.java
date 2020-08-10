package com.sduept.dragon.manager.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Xiao guang zhen
 * @since 2020-07-09
 */
@TableName(value = "product_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 交易状态:0:失败，1：成功
     */
    private Integer tradeStatus;

    /**
     * 交易失败的原因
     */
    private String reason;

    /**
     * 支付宝订单号
     */
    private String tradeNo;

    /**
     * 支付类型：支付宝或者是微信
     */
    private String payType;

    /**
     * 是否付块完成，0：未完成，1：已完成
     */
    private Integer completed;

    /**
     * 更新时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.orderNo;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", tradeStatus=" + tradeStatus +
                ", reason='" + reason + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", payType='" + payType + '\'' +
                ", completed=" + completed +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
