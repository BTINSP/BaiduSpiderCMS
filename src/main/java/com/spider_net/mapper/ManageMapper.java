package com.spider_net.mapper;

import com.spider_net.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManageMapper {

    @Select("select * from net_user where username = #{username}")
    User getUserByUsername(@Param("username")String username);

    @Insert("insert into spider_article(title, content, path, source, date) VALUES (#{title},#{content},#{path},#{source},now())")
    boolean addSpiderArticle(
            @Param("title")String title,
            @Param("content")String content,
            @Param("path")String path,
            @Param("source")int source
    );

}
