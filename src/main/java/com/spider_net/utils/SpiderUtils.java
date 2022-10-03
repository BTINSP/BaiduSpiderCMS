package com.spider_net.utils;

import com.spider_net.entity.SpiderKeyword;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;

import java.util.ArrayList;
import java.util.List;

public class SpiderUtils {


    public static List<String> getSpiderKeywordUrls(List<SpiderKeyword> spiderKeywords){
        List<String> strings = new ArrayList<>();
        for (SpiderKeyword spiderKeyword : spiderKeywords) {
            strings.add(getSpliceUrlByKeyword(spiderKeyword.getKeyword()));
        }
        return strings;
    }
    public static List<String> getSpiderKeywordUrlsByString(List<String> spiderKeywords){
        List<String> strings = new ArrayList<>();
        for (String spiderKeyword : spiderKeywords) {
            strings.add(getSpliceUrlByKeyword(spiderKeyword));
        }
        return strings;
    }

    public static String getCanonicalizeContent(String string){
        string = string.replace("<em>","");
        string = string.replace("</em>","");
        string = string.replace("<b>","");
        string = string.replace("</b>","");
        string = string.replace("&lt;","<");
        string = string.replace("&gt;",">");
        string = string.replace("&quot;","=");
        return string;
    }

    public static String getBaiduCanonicalizeUrl(String url){
        return url.replace("http://www.baidu.com/link?url=","");
    }

    //  根据关键词拼接 baidu 搜索链接
    public static String getSpliceUrlByKeyword(String keyword){
        return "http://www.baidu.com/s?ie=UTF-8&rn=50&wd=" + keyword;
    }

    public static SpiderStatusMXBean getSpiderStatusMXBeanByUUID(List<SpiderStatusMXBean> spiderStatusMXBeans,String uuid){
        for (SpiderStatusMXBean spiderStatusMXBean : spiderStatusMXBeans) {
            if (spiderStatusMXBean.getName().equals(uuid)){
                return spiderStatusMXBean;
            }
        }
        return null;
    }

}
