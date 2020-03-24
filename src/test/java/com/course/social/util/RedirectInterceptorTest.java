package com.course.social.util;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

public class RedirectInterceptorTest {


    @Test
    public void testInterceptor() throws Exception {
        RedirectInterceptor redirectInterceptor = new RedirectInterceptor();
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        ModelAndView modelAndView = new ModelAndView("test");
        Object handler = new Object();
        redirectInterceptor.postHandle(httpServletRequest,httpServletResponse,handler,null);

     //   redirectInterceptor.postHandle(null,httpServletResponse,handler,modelAndView);

        //redirectInterceptor.postHandle(httpServletRequest,httpServletResponse,handler,modelAndView);

    }
}
