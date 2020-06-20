package priv.wdw.nacos;

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
@RefreshScope  //加上此注解才可以自动更新
public class NacosDemo {


    @Value(value = "${username}")
    private volatile String username;


    @Value(value = "${jdbc.url}")
    private volatile String jdbcUrl;


    @GetMapping("/printVal")
    public String printVal() {
        return "username = " + username + '\n' + "jdbc.url = " + jdbcUrl;
    }


}
