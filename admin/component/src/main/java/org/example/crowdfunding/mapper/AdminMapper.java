package org.example.crowdfunding.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.example.crowdfunding.entity.Admin;
import org.example.crowdfunding.entity.AdminExample;

public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    //根据关键字查找用户的方法
    List<Admin> selectAdminByKeyword(String keyword);

    void saveAdminRoleRelationship(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);

    void clearOldRelationship(Integer adminId);
}