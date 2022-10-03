package com.spider_net.spider;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;

import java.util.List;

@Component
public class WebSpiderMonitor extends SpiderMonitor {
    public List<SpiderStatusMXBean> getSpiderMonitorStatus(){
        return this.getSpiderStatuses();
    }
}
