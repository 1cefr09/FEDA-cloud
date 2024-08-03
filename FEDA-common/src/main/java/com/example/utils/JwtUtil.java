package com.example.utils;

import java.security.Signature;
import java.util.Map;

import com.example.exception.JwtParseFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     *生成Jwt校验
     * @param secretKey jwt密钥
     * @param ttlMillis jwt的过期时间
     * @param claims 设置的信息
     * @return
     */

    public static String createJWT(String secretKey, long ttlMillis, Map<String,Object> claims){

        //指定header部分签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //设置jwt生成时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp =new Date(expMillis);

        //设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //payload,私有声明，给Builder的claim赋值,一般有ID，用户名或者其他元数据
                .setClaims(claims)
                //Signature，设置签名的签名算法和密钥
                .signWith(signatureAlgorithm,secretKey.getBytes(StandardCharsets.UTF_8))
                //设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token的解密
     * @param secretKey jwt密钥，保存在服务器端
     * @param token 加密后的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token){

        //创建JWTparser对象用来解析JWT
        try {
            Claims claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    //设置需要解析的jwt
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
        throw new JwtParseFailedException("请重新登录");
        }
    }
}
