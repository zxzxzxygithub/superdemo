package com.test.emptydemo;

import java.io.Serializable;

/**
 * Created by weaiken on 2017/2/13.
 * weaiken@126.com
 */

public class AlipayRequestInfo implements Serializable {


    /**
     * app_id : 2017012205346404
     * method : alipay.trade.app.pay
     * charset : utf-8
     * sign_type : RSA2
     * timestamp : 2017-02-13 15:03:31
     * version : 1.0
     * notify_url : http://106.75.45.20:5000/mall/alipay/notify
     * biz_content : {"subject":"私聊支付宝充值","out_trade_no":"170213150396659","total_amount":"0.01","product_code":"QUICK_MSECURITY_PAY"}
     * sign : JHg+r4FmZpFIs6dwHcyp5zuNjCSQ+wmCZswJrOLZxae4rxYbOQphKg2inzDskZqJYIUyEjEdMSJILVpkLp1Or7YfUcS61oNDcWImsTpVnvjXzpS6cD5KUdXZVaVm6stek5Md3vqHsUKH8l2ZBEoETDpMNfQ935VoMxypOpiNptfiBiQQP71JPMqHoaAssyJ6x1ARXZg8k2Fhz/Jv/pj9awO7eFhDPCRJ660yZDEvwLgRMsRVGQOGZW4YLKQrWqPEsS9sCb12NcRjb4PYVs9GPV2hTKgTx8WkxgRYGuok2BgWG0Hp4F2txWrCWbFueUo0H6t2pLQ//4Q4kmebg3OZSg==
     * code : 0
     */

    private String app_id;
    private String method;
    private String charset;
    private String sign_type;
    private String timestamp;
    private String version;
    private String notify_url;
    private String biz_content;
    private String sign;
    private int code;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AlipayRequestInfo(String app_id, String method, String charset, String sign_type,
                             String timestamp, String version, String notify_url,
                             String biz_content, String sign, int code) {
        this.app_id = app_id;
        this.method = method;
        this.charset = charset;
        this.sign_type = sign_type;
        this.timestamp = timestamp;
        this.version = version;
        this.notify_url = notify_url;
        this.biz_content = biz_content;
        this.sign = sign;
        this.code = code;
    }

    public AlipayRequestInfo() {
    }

    @Override
    public String toString() {
        return "AlipayRequestInfo{" +
                "app_id='" + app_id + '\'' +
                ", method='" + method + '\'' +
                ", charset='" + charset + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", biz_content='" + biz_content + '\'' +
                ", sign='" + sign + '\'' +
                ", code=" + code +
                '}';
    }
}
