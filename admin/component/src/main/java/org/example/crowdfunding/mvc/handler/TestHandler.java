package org.example.crowdfunding.mvc.handler;

import org.example.crowdfunding.entity.Admin;
import org.example.crowdfunding.service.api.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping("test/ssm.html")
    public String testSsm(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        return "target";
    }

    //通过@RequestParam接收数组
    @ResponseBody
    @RequestMapping("send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer num : array) {
            System.out.println("num:" + num);
        }
        return "success";
    }

    //通过@RequestBody接收数组
    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(@RequestBody List<Integer> array){
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        for(Integer num : array){
            logger.info("num:"+num);
        }
        return "success";
    }
}
