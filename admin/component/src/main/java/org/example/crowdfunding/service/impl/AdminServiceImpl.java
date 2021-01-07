package org.example.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.crowdfunding.constant.CrowdFundingConstant;
import org.example.crowdfunding.entity.Admin;
import org.example.crowdfunding.entity.AdminExample;
import org.example.crowdfunding.exception.LoginFailedException;
import org.example.crowdfunding.mapper.AdminMapper;
import org.example.crowdfunding.service.api.AdminService;
import org.example.crowdfunding.util.CrowdFundingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByUsername(String username, String password) {

        // 1、根据登陆账号查询Admin对象
        // 创建AdminExample对象
        AdminExample adminExample = new AdminExample();
        // 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 在Criteria对象中封装查询的条件
        criteria.andLoginAcctEqualTo(username);
        // 调用AdminMapper的方法来查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 2、判断Admin对象是否为null或数据库数据不正常
        if (admins == null || admins.size() == 0){
            throw new LoginFailedException(CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1){
            // 数据库的数据存在重复
            throw new LoginFailedException(CrowdFundingConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 前面判断完后无异常，取出admin对象
        Admin admin = admins.get(0);

        // 3、如果Admin对象为null 则抛出异常
        if (admin == null){
            throw new LoginFailedException(CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4、如果Admin对象不为null，则取出Admin对象的密码
        String userPswdDB = admin.getUserPswd();

        // 5、对表单提交的密码进行md5加密
        String userPswdForm = CrowdFundingUtil.md5(password);

        // 6、对比两个密码
        // 因为两个密码都是对象，使用字符串的equals方法，如果存在空的密码则会触发空指针异常
        // 因此选用Objects.equals方法
        if (!Objects.equals(userPswdDB,userPswdForm)){
            // 密码不匹配
            throw new LoginFailedException(CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
        }

        // 7、比对结果一致，返回admin对象
        return admin;
    }

    /**
     * @param keyword 关键字
     * @param pageNum 当前页码
     * @param pageSize 每一页显示的信息数量
     * @return 最后的pageInfo对象
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 利用PageHelper的静态方法开启分页
        PageHelper.startPage(pageNum,pageSize);

        // 调用Mapper接口的对应方法
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);

        // 为了方便页面的使用，把Admin的List封装成PageInfo（放别得到页码等数据）
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);

        // 返回得到的pageInfo对象
        return pageInfo;
    }
}
