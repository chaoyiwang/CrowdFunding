package org.example.crowdfunding.util;

import javax.servlet.http.HttpServletRequest;

public class CrowdFundingUtil {
    /**
     * @param request
     * @return true==json请求 ; false==普通页面请求
     */
    public static boolean judgeRequestType(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");
        return (accept != null && accept.contains("application/json"))
                ||
                (header != null && header.equals("XMLHttpRequest"));
    }
}
