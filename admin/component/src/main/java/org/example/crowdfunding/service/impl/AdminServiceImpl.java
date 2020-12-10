package org.example.crowdfunding.service.impl;

import org.example.crowdfunding.entity.Admin;
import org.example.crowdfunding.entity.AdminExample;
import org.example.crowdfunding.mapper.AdminMapper;
import org.example.crowdfunding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
