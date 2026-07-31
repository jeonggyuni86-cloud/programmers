package com.example.basicboard_token.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtil {

    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    public static void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAge
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // JS 접근불가
        cookie.setSecure(false); // 로컬 (HTTP) 개발용, 운영에선 HTTPS 써야 한다.
        // Path : 브라우저가 이 쿠키를 어떤 URL 경로에 붙일지를 결정한다.
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name
    ) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) return;
        for(Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }
    }

}
