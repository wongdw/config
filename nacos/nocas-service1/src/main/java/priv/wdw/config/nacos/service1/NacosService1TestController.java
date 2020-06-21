package priv.wdw.config.nacos.service1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@author wdw</p>
 * <p>@date 2020/6/20 20:58</p>
 * <p>@description </p>
 */

@RestController
@RefreshScope  //加上此注解 @Value才可以自动更新（系统的配置文件不需要此注解）
public class NacosService1TestController {

    /**
     * 主配置文件 （microService1.properties）
     */
    @Value("${service1.key:null}")
    private String val;


    /**
     * 定义在拓展配置文件（microService1-db.properties）
     */
    @Value("${jdbc.url:null}")
    private String jdbcUrl;


    @GetMapping("/getVal")
    public String getVal() {
        return System.currentTimeMillis() + "-->" + this.val;
    }


    @GetMapping("/jdbcUrl")
    public String getJdbcUrl() {
        return System.currentTimeMillis() + "-->" + this.jdbcUrl;

    }


}
