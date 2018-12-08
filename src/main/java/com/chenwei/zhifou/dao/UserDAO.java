package com.chenwei.zhifou.dao;

import com.chenwei.zhifou.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 19:10
 * @Description:
 */
@Mapper
public interface UserDAO {
    // 注意空格
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name},#{password},#{salt}," +
            "#{headUrl})"})
    int addUser(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id = #{id}"})
    User selectById(int id);

    @Update({"update", TABLE_NAME, "set password = #{password}"})
    void updatePassword(User user);

    @Delete({"delete from", TABLE_NAME, "where id = #{id}"})
    void deleteById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name = #{username}"})
    User selectByName(String username);
}
