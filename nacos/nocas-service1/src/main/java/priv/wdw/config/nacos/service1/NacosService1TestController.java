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
     * 主配置文件 （microservice1.properties）
     */
    @Value("${service1.key:null}")
    private String val;


    /**
     * 拓展配置properties格式文件（microservice1-extend.properties）
     */
    @Value("${service1.extendKey:null}")
    private String extendKey;


    /**
     * 拓展配置json格式文件（microservice1-extend.json）
     */
    @Value("${service1.extendJsonKey:null}")
    private String extendJsonKey;


    @GetMapping("/key")
    public String getKey() {
        return System.currentTimeMillis() + "-->" + this.val;
    }


    @GetMapping("/extendKey")
    public String getExtendKey() {
        return System.currentTimeMillis() + "-->" + this.extendKey;
    }


    @GetMapping("/extendJsonKey")
    public String getExtendJsonKey(){
        return System.currentTimeMillis() + "-->" + this.extendJsonKey;
    }
}
