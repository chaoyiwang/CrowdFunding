package org.example.crowdfunding;

import org.example.crowdfunding.util.CrowdFundingUtil;
import org.junit.Test;

public class StringTest {

    //测试MD5加密
    @Test
    public void testMd5(){
        String source = "chaoyi";
        String md5 = CrowdFundingUtil.md5(source);
        System.out.println(md5);//40945ED14C7F0B55399589E9D8338672
    }
}
