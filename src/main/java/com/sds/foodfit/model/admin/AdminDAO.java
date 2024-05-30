package com.sds.foodfit.model.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sds.foodfit.domain.Member;
import com.sds.foodfit.domain.Role;

@Mapper
public interface AdminDAO {
    @Select("SELECT id, pwd FROM member WHERE id = #{id} AND roleIdx = 1")
    @Results({
    	@Result(property="role", column="roleIdx", javaType=Role.class, one=@One(select="com.sds.foodfit.model.member.RoleDAO.selectByName"))
    })
    Member findAdmin(@Param("id") String id);
}
