package org.example.crowdfunding.mvc.config;

import com.google.gson.Gson;
import org.example.crowdfunding.constant.CrowdFundingConstant;
import org.example.crowdfunding.exception.LoginFailedException;
import org.example.crowdfunding.util.CrowdFundingUtil;
import org.example.crowdfunding.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//注解标明该类是基于注解的异常处理器类
@ControllerAdvice
public class CrowdFundingExceptionResolver {

    //处理空指针异常
    @ExceptionHandler(value = {NullPointerException.class})
    public ModelAndView resolveNullPointerException(
                                                    //这是实际捕获到的异常类型
                                                    NullPointerException exception,
                                                    //当前请求对象
                                                    HttpServletRequest request,
                                                    //当前响应对象
                                                    HttpServletResponse response
    ) throws IOException {
        return commonCode(exception,request,response,"system-error");
    }

    //处理数学异常,这里如果内部操作相同，跳转页面也相同，其实可以放在上面一个方法中，此处只是为了演示
    @ExceptionHandler(value = {ArithmeticException.class})
    public ModelAndView resolveArithmeticException(ArithmeticException exception,
                                                   HttpServletRequest request, HttpServletResponse response) throws IOException {
        return commonCode(exception,request,response,"system-error");

    }

    // 触发登录失败异常，则继续返回登陆页面
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolverLoginFailedException(
                                                     LoginFailedException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response
    ) throws IOException {
        String viewName = "admin-login";
        return commonCode(exception,request,response,viewName);
    }

    //整理出的不同异常的可重用代码
    private ModelAndView commonCode(
            //触发的异常，此处借助多态
            Exception exception,
            //客户器端的请求
            HttpServletRequest request,
            //服务端的响应
            HttpServletResponse response,
            //指定普通页面请求时，去的错误页面
            String viewName
    ) throws IOException {
        boolean judgeRequestType = CrowdFundingUtil.judgeRequestType(request);
        if (judgeRequestType){
            //if判断-是json请求
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            //创建Gson对象
            Gson gson = new Gson();
            //将ResultEntity对象转换成json格式
            String json = gson.toJson(failed);
            //通过原生servlet的response传回异常内容
            response.getWriter().write(json);
            //此时只需要返回null（因为是通过原生的response对象返回了响应 - json格式返回数据）
            return null;
        } else {
            //if判断-是普通页面请求
            //创建ModelAndView对象
            ModelAndView modelAndView = new ModelAndView();
            //设置触发异常跳转的页面（会自动被视图解析器加上前后缀）
            modelAndView.setViewName(viewName);
            //将异常信息加入
            modelAndView.addObject(CrowdFundingConstant.ATTR_NAME_EXCEPTION, exception);
            //返回设置完成的ModelAndView
            return modelAndView;
        }
    }
}
