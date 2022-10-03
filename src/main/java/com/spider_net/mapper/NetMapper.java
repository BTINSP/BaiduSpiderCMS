package com.spider_net.mapper;

import com.spider_net.entity.SpiderArticle;
import com.spider_net.entity.SpiderKeyword;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NetMapper {

    @Select("SELECT * FROM spider_keyword WHERE id >= ( SELECT floor( RAND() * ( SELECT MAX( id ) FROM spider_keyword ) ) ) ORDER BY id LIMIT 10")
    List<SpiderKeyword> getRandomKeyword();

    @Select("SELECT * FROM spider_article WHERE id >= ( SELECT floor( RAND() * ( SELECT MAX( id ) FROM spider_article ) ) ) ORDER BY id LIMIT 10")
    List<SpiderArticle> getRandomArticle();

    @Select("select *,MATCH(title,content) AGAINST(#{keyword}) as score from spider_article where MATCH(title,content) AGAINST (#{keyword} IN NATURAL LANGUAGE MODE) GROUP BY title limit 20")
    List<SpiderArticle> getArticleByKeyword(@Param("keyword")String keyword);

    @Insert("insert into net_comment(name, email, comment,date) values (#{name},#{email},#{comment},now())")
    boolean addComment(@Param("name")String name,@Param("email")String email,@Param("comment")String comment);
}
