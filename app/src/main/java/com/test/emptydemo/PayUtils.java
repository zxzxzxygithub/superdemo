package com.test.emptydemo;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by weaiken on 2017/2/14.
 * weaiken@126.com
 */

public class PayUtils {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    public static void alipay(final Activity mActivity, String userID , String session , String priceId, final RebackPayInterface mRebackPayInterface){

//        String url = Define.URL_ALIPAY;
        String url = "YOUR URL";
        Map map = new HashMap();
        map.put("id", userID);
        map.put("session", session);
        map.put("priceId", priceId);

        OkGo.post(url).params(map).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if (TextUtils.isEmpty(s)) {
//                    ToastUtils.showToast(mActivity.getApplicationContext(), "调用支付宝失败，请重新尝试");
                    return;
                }
                AlipayRequestInfo mAlipayRequestInfo = JSON.parseObject(s, AlipayRequestInfo.class);
//                L.e("alipay-request" + mAlipayRequestInfo);
                alipayAppRequest(mActivity , mAlipayRequestInfo,mRebackPayInterface);
            }
        });

    }

    private static void alipayAppRequest(final Activity mActivity, AlipayRequestInfo mAlipayRequestInfo, final RebackPayInterface mRebackPayInterface) {
        Map<String, String> params = buildOrderParamMap(mAlipayRequestInfo);
        String orderParam = buildOrderParam(params);
        final String orderInfo = orderParam + "&" + buildKeyValue("sign", mAlipayRequestInfo.getSign(), true);
//        L.e("alipay-request-orderInfo--" + orderInfo.toString());
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Log.i("msp", result.toString());
                mRebackPayInterface.rebackInfo( new Object[]{SDK_PAY_FLAG , result});
//                L.e("alipay-response" + result.toString());
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private static Map<String, String> buildOrderParamMap(AlipayRequestInfo appAlipayRequestInfo) {
        Map<String, String> keyValues = new HashMap<String, String>();
        keyValues.put("app_id", appAlipayRequestInfo.getApp_id());
        keyValues.put("biz_content", appAlipayRequestInfo.getBiz_content());
        keyValues.put("charset", appAlipayRequestInfo.getCharset());
        keyValues.put("method", appAlipayRequestInfo.getMethod());
        keyValues.put("notify_url", appAlipayRequestInfo.getNotify_url());
        keyValues.put("sign_type", appAlipayRequestInfo.getSign_type());
        keyValues.put("timestamp", appAlipayRequestInfo.getTimestamp() + "");
        keyValues.put("version", appAlipayRequestInfo.getVersion() + "");

        return keyValues;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    private static String buildOrderParam(Map<String, String> map) {

        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, false));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, false));
//
        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }


    public interface RebackPayInterface {
        public void rebackInfo(Object[] objects);
    }

}
