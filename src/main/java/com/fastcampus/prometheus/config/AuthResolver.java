//package com.fastcampus.prometheus.config;
//
//
//import com.fastcampus.prometheus.domain.member.service.LoginService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import javax.security.auth.login.AppConfigurationEntry;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.client.HttpClientErrorException.Unauthorized;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//@Slf4j
//@RequiredArgsConstructor
//public class AuthResolver implements HandlerMethodArgumentResolver {
//
//    private static final String KEY = "dfadf";
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return false;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
////        log.info(">>>{}", appConfig.toString());
//
//        String jws = webRequest.getHeader("Authorization");;
//        if (jws == null || jws.equals("")) {
//            throw new Unauthorized();
//        }
//
//        byte[] decodedKey = Base64.decodeBase64(KEY);
//
//        try {
//            Jws<Claims> claims = Jwts.parser()
//                .setSigningKey(decodedKey)
//                .build()
//                .parseSignedClaims(jws);
//
//            log.info(">>>>>>>>>>{}", claims);
//
//        } catch (JwtException e) {
//            throw new Unauthorized();
//        }
//
//    }
//
//
//}
