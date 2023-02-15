package com.eureka.authentication.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul Pre Filter for setting the userId in the request Header
 * which is passed to the other services
 */
@Configuration
public class EurekaZuulPreFilter extends ZuulFilter {

    @Autowired private JwtTokenUtil jwtTokenUtil;


    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            String jwtToken = authHeader.substring(7);;
            String userId = jwtTokenUtil.getUsernameFromToken(jwtToken);
            ctx.addZuulRequestHeader("userId", userId);
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
