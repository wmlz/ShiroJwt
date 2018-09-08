package com.wang.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wang.util.common.Base64ConvertUtil;
import com.wang.util.common.PropertiesUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * JAVA-JWT工具类
 * @author Wang926454
 * @date 2018/8/30 11:45
 */
public class JWTUtil {

    /**
     * 过期时间改为从配置文件获取
     */
    // private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     * @author Wang926454
     * @date 2018/8/31 9:05
     */
    public static boolean verify(String token) {
        try {
            // 获取Token私钥，读取配置文件
            PropertiesUtil.readProperties("config.properties");
            String secret = getClaim(token, "account") + Base64ConvertUtil.decode(PropertiesUtil.getProperty("encrypJWTKey"));
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/7 16:54
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成签名
     * @param account 帐号
     * @return java.lang.String 返回加密的Token
     * @author Wang926454
     * @date 2018/8/31 9:07
     */
    public static String sign(String account, String currentTimeMillis) {
        try {
            // 获取Token过期时间以及私钥，读取配置文件
            PropertiesUtil.readProperties("config.properties");
            String tokenExpireTime = PropertiesUtil.getProperty("tokenExpireTime");
            String secret = account + Base64ConvertUtil.decode(PropertiesUtil.getProperty("encrypJWTKey"));
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(tokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}