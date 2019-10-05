package com.cone.orange.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * @Author Cone
 * @Date 2019/9/14 11:25
 */
public class TokenHelper {

    /**
     * 根据id与pwd生成默认token，有效时间1小时
     * @param id
     * @param pwd
     * @return
     */
    public static String createToken(String id, String pwd) {
        Date startDate = new Date();
        Long expireTime = System.currentTimeMillis() + 60*60*1000;//默认有效时间1小时
        Date endDate = new Date(expireTime);
        String token = JWT.create().withAudience(id).withIssuedAt(startDate).withExpiresAt(endDate)
                .sign(Algorithm.HMAC256(pwd));
        return token;
    }

    /**
     * 根据指定过期时间生成token
     * @param id
     * @param pwd
     * @param expect 期望过期时间(min)
     * @return
     */
    public static String createToken(String id, String pwd, long expect) {
        Date startDate = new Date();
        Long expireTime = System.currentTimeMillis() + expect*60*1000;//默认有效时间1小时
        Date endDate = new Date(expireTime);
        String token = JWT.create().withAudience(id).withIssuedAt(startDate).withExpiresAt(endDate)
                .sign(Algorithm.HMAC256(pwd));
        return token;
    }

    /**
     * 解密token，得到id
     * @return
     */
    public static String decodeToken() {
        try {
            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return "无效的请求";
            } else {
                String token = requestAttributes.getRequest().getHeader("token");
                if (null == token) {
                    return "身份验证失败";
                }
                return JWT.decode(token).getAudience().get(0);
            }
        } catch (JWTDecodeException e) {
            return "身份验证失败";
        } catch (Exception e) {
            return "系统异常";
        }

    }
}
