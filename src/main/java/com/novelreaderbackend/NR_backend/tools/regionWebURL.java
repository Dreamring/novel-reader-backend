package com.novelreaderbackend.NR_backend.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class regionWebURL {
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * 生成类似 jQuery 风格的唯一标识符
     * <p>该方法模拟 jQuery 的 uniqueId 生成方式，创建一个 20-22 位的数字字符串，
     * 用于构建 JSONP 回调函数名等需要唯一标识符的场景。</p>
     *
     * @return 20-22 位的数字字符串，作为唯一标识符
     */
    public static String getjQueryHas(long timestamp) {
        // 1. 生成一个 20~22 位的数字字符串（模拟 jQuery 的 uniqueId）
        // 方法：取当前纳秒 + 随机数，去掉非数字字符（其实全是数字）
        long base = System.nanoTime(); // 高精度时间
        long rand = Math.abs(random.nextLong() % 10000000000L); // 10位随机数
        String uniquePart = String.valueOf(base + rand).replaceAll("\\D", ""); // 确保纯数字

        // 调整长度至 20-22 位范围
        while (uniquePart.length() < 20) {
            uniquePart += Math.abs(random.nextInt() % 10);
        }
        if (uniquePart.length() > 22) {
            uniquePart = uniquePart.substring(0, 22);
        }

        String jsonpCallback = "jQuery" + uniquePart + "_" + timestamp;
        return uniquePart;
    }

    /**
     * 判断给定的秒级时间戳是否距离当前时间已超过 30 分钟
     *
     * @param timestampInSeconds 过去的秒级时间戳（如 1763537657）
     * @return true 如果时间差 > 30 分钟
     */
    public static boolean isExpired(long timestampInSeconds) {
        long currentTimeMillis = System.currentTimeMillis();
        long pastTimeMillis = TimeUnit.SECONDS.toMillis(timestampInSeconds);
        long diffMillis = currentTimeMillis - pastTimeMillis;
        return diffMillis > TimeUnit.MINUTES.toMillis(30);
    }

    public static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * 生成起点/阅文扫码登录 URL
     *
     * @param appId       应用 ID（如 10）
     * @param returnUrl   登录成功后跳转地址（如 "https://www.qidian.com/"）
     * @param callback    JSONP 回调函数名（可为 null，自动生成）
     * @return 完整的 HTTPS 登录 URL 字符串
     */
    public static String getRegionWebURL( int appId, String returnUrl, String callback) {
        // 2. 当前毫秒时间戳
        long timestamp = System.currentTimeMillis();
        if (callback == null || callback.isEmpty()) {
            callback = getjQueryHas(timestamp);
        } else if (isExpired(Integer.getInteger(callback.split("_")[1]))) {
            // 如果 callback 后段时间戳与当前时间小于30分钟，生成类似新的 jQuery 时间戳风格的名称
            callback = getjQueryHas(timestamp);
        }
        StringJoiner sj = new StringJoiner("&");
        sj.add("callback=" + encode(callback));
        sj.add("appId=" + appId);
        sj.add("areaId=1");
        sj.add("source=");
        sj.add("returnurl=" + encode(returnUrl));
        sj.add("version=");
        sj.add("imei=");
        sj.add("qimei=");
        sj.add("target=top");
        sj.add("ticket=0");
        sj.add("autotime=30");
        sj.add("jumpdm=yuewen");
        sj.add("ajaxdm=yuewen");
        sj.add("auto=1");
        sj.add("sdkversion=");
        sj.add("method=LoginV1.qrCodeCallback");
        sj.add("uuid=");
        sj.add("pageId=");
        sj.add("bookId=");
        sj.add("chapterId=");
        sj.add("format=jsonp");
        sj.add("_=" + timestamp);

        return "https://ptlogin.yuewen.com/login/qrcode?" + sj.toString();
    }


}