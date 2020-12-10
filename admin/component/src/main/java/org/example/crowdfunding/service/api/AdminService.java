package org.example.crowdfunding.service.api;

import org.example.crowdfunding.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();
}
