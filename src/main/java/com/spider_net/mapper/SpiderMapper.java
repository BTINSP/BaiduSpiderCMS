package com.spider_net.mapper;

import com.spider_net.entity.SpiderKeyword;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpiderMapper {

    @Insert("insert into spider_keyword(keyword, status, date) VALUES (#{keyword},#{status},now())")
    boolean addSpiderKeyword(@Param("keyword")String keyword,@Param("status")int status);


    @Insert("insert into spider_article(title, content, path, source, date) VALUES (#{title},#{content},#{path},#{source},now())")
    boolean addSpiderArticle(
            @Param("title")String title,
            @Param("content")String content,
            @Param("path")String path,
            @Param("source")int source
    );

    @Select("SELECT DISTINCT keyword FROM spider_keyword WHERE id >= ( SELECT floor( RAND() * ( SELECT MAX( id ) FROM spider_keyword) ) ) LIMIT #{size}")
    List<String> getRandomKeyword(@Param("size")int size);

}
