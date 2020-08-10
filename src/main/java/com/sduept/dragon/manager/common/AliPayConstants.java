package com.sduept.dragon.manager.common;

/**
 * 支付宝通用配置，沙箱环境何正式有些属性是不同的
 *
 * @author xiao guang zhen
 * @since v1.0
 */
public class AliPayConstants {

    // 服务器域名
    private static final String DOMAIN_URL = "http://windrunner.vipgz1.idcfengye.com";

    // 支付宝网关
    public static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝用户ID
    public static final String APP_ID = "2016102900776843";

    // 商户私钥
    public static final String MERCHANT_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC3RAK9gwz3o/quZvRWnSswlBOd+rqR7vGP+/TAtSsyS63Htzi0Q+vn62P5WePP/AHLvD5QNmTK0wiJYNgErJsTXIErCzIzFvDPBs9HPbTqmTSiHmy8tFstkrt1nunUwHA25DyqC/3McMtlol7syzUJU29t9YfTP6QHbkEfBn32TZiJJzi17658qq91UieH8CIpmCIlZ4cJT/XiIns5+jGXmUZbHL7FrfrP2emSKEtPITegNICoGOUAJAaCaFOKfbyMWb1XxmmS4TfiGBHsAXS0RxT7AXIAygd9lmjlwdY9ynngykq5RMJKMEHHh221VgaDZhKKn63XiC6IgQ6Z0ZQ9AgMBAAECggEAGL90Z0cVLzPTnE2G3VNoC61TYNgE7cQIltr9WDCe3rHdi8SwQZeSecUNwRRkcSekznBjMc3GmqUY2ARd53OvlVO+laNzafbFGg/fWc15aty5gNL++BSa43KXIfENvo8mGCOGFah842iv7s+YZf2i8dl0iLLJXruPWPmIjboAtIvXQQ/8XnyrYHrss6BCpb4dB5EBVL3V+FqiqZeOpy4h+C1bSnMxy7JrR4QvU3OOe98HsnTO2aBLafXgi5sB2wfIskRG6AGBVIUFDbLAQlZ9X7pc/6UPqgIiimaK3qoY7z/SjOxsrqiMw1ZLG8CgfAsnczK9VxshXsIKaThXzJ8fQQKBgQDr3J561rqgMkSHrsq1r+EkKjB8owY2NZ9Tu4Eg7D3RvkqbF+R6TcLwOnnI4gFs1px/iij/KQOgt3VRnjI1LOsevS/FyH1v4r9xttIKVK3rf6ZyHt1lukAJRkSiwVVDbY+Qtoo/mQcNHGCVrX7NxMj6oko7qzpNBCsvZF9ORHZ4iQKBgQDG6cPEr1UrEXZ+XZ0S6C11W58o19pwhmF2AJ1Lwt2bUBlm0/RIbzvkSV88S2dvWfxt/btmPCv5EUeZBW6ZTWJf9nnUB5uNCgaJokh18X9lnp86B6UFw9rMRfuYDml2BX2t2BIPx90cGleOa/Ya260rZipYS2Tm0RDxn/k5gf3pFQKBgQCZIF5zeb0oC/qZ7E0Gr+0HQazA/kYmI6IRBobLDRUFevmMv3JCRQHuXMh8Nt6d/qcT1mrPXZ1RVkoAdyg5hbQP/NtrGML3RMrt1LeApYFUh0dUh91NEiJI4ynkUIg+gn7WgOw4Ze1CAc5i3oSYqs2gioSPQJdVMQOofQ2zDperoQKBgAp2SySyMCGWxou48sUGzzVH/2QPjl6ywreJKrzMYAmGOf/NkCCrMRQ9w7ORJ7yUmBFU8iFbctijzwc+YOXRSyZO+XNsfwL5DwT4MyV48UFwCWHs+wIFniF2YNhLkcJyYXzeS3zm/zs2K3rDNOuOuFn7AqkoNgNp5aE/z8T1kFxNAoGBAIlMHzq9ViC1gHyIi1qPFJft8OlvKxoXJbu7VQ+F6fpexkIJjlmkO95nigSGJ5muXs0lW0nIs2jairSsoYg0FrKBSuQKZj6fbS0fURhs5n2LYQtsYjjC7DKKafh3dHLgRrrsPACuGOhfNa9qnAP/Sso5v5zKvG2XCkFMo7gyyfbZ";

    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3YZU3RpQIqG2TewCT5cK1fB4XjSrKnN8ZG7+9UzaEMF3gE1qK8a96EcUiyPx6m85n+ESg6m+sCvYlBAcePSe+15zkgeAQkhxABsfCdwj/VziYCwHfWQi7UIIcyWZx7E3OWR1/16wOqC0gQvglC2t5bnZD+0kBm8sNyCdDcILINIDS8Q2E3nbClb9KQRrsZPZi6iTGfIIQ3ZfTlCjsMitN8gUB0wdDn4E9vkjSjcNR3lQuUO20dt3ervLWiPBNpuGXI3spf6wUC3yomD8auPJJXX43jqUEEHN2RtXHLTCTb91sqplKSv2VZvfRKn3gSMVmbmElWGP8QDkKB+eJsMHQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    public static final String ASYNC_NOTIFY_URL = DOMAIN_URL + "/payapi/alipay/async-notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    public static final String SYNC_RETURN_URL = DOMAIN_URL + "/payapi/alipay/sync-notify";

    // 签名方式
    public static final String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static final String CHARSET = "utf-8";
}
