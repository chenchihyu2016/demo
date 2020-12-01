package com.example.demo.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ApiAspect {
    @Around("execution(* com.example.demo.api..*(..))")
    public void commonAspect(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
        Object result = null;
        String resultString = "";
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            result = pjp.proceed();

            @SuppressWarnings(value = "unchecked")
            Map<String, Object> map = (Map<String, Object>) result;
            if (map.containsKey("errorCode")) {
                switch (map.get("errorCode").toString()) {
                    case "400":
                        response.setStatus(400);
                        break;
                    case "404":
                        response.setStatus(404);
                        break;
                    case "500":
                        response.setStatus(500);
                        break;
                    default:
                        response.setStatus(500);
                        break;
                }
            } else {
                response.setStatus(200);
            }
        } catch (Exception e) {
            response.setStatus(500);
            Map<String, Object> map = new HashMap<>();
            map.put("errorCode", "500");
            result = map;
            e.printStackTrace();
        } finally {
            resultString = new Gson().toJson(result);
            System.out.println("Response Status = " + response.getStatus());
            System.out.println("Response Body = " + resultString);
            response.getWriter().write(resultString);
        }
    }

}
