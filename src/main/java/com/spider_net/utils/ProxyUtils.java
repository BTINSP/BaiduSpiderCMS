package com.spider_net.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spider_net.entity.SpiderProxy;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class ProxyUtils {

    private boolean checkHostStatus(SpiderProxy spiderProxy) throws IOException {
        //  IP 验证网址
        String target = "http://myip.ipip.net";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(target);
        //  设置代理
        HttpHost proxy = new HttpHost(spiderProxy.getIp(), spiderProxy.getPort());
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
        request.setConfig(requestConfig);
        //  response
        HttpResponse response = httpClient.execute(request);
        //  判断是否访问成功
        if (response.getStatusLine().getStatusCode() == 200){
            return true;
        }
        return false;
    }

    public SpiderProxy getProxyHost() throws IOException {
        String target = "https://proxy.qg.net/allocate?Key=50C8FC62&Num=1";

        HttpClient httpClient = HttpClientBuilder.create().build();

        while (true){
            HttpGet request = new HttpGet(target);

            HttpResponse response = httpClient.execute(request);
            //  判断访问是否成功
            if (response.getStatusLine().getStatusCode() == 200){
                ObjectMapper objectMapper = new ObjectMapper();
                //  获取String格式结果
                String entity = EntityUtils.toString(response.getEntity());
                //  将Sting 转换成JsonNode 先将String转换成Object才可以转JsonNode否则乱码
                JsonNode result = objectMapper.valueToTree(objectMapper.readValue(entity,Object.class));

                //  判断是否访问正确
                if (result.get("Code").asInt() == 0){
                    //  获取data
                    JsonNode data = result.get("Data").get(0);

                    SpiderProxy spiderProxy = objectMapper.readValue(data.toString(),SpiderProxy.class);
                    //  验证成功返回对象
                    if (checkHostStatus(spiderProxy)){
                        return spiderProxy;
                    }
                }
            }
        }
    }

}
