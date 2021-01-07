package org.example.crowdfunding.service.api;

import com.github.pagehelper.PageInfo;
import org.example.crowdfunding.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByUsername(String username, String password);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
