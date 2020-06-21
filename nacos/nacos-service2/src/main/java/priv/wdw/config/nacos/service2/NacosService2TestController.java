package priv.wdw.config.nacos.service2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@author wdw</p>
 * <p>@date 2020/6/21 10:00</p>
 * <p>@description 测试获取变量值 </p>
 */
@RestController
public class NacosService2TestController {


    @Value("${service2.key:null}")
    private String val;


    @GetMapping("/getVal")
    public String getVal() {
        return System.currentTimeMillis() + "-->" + this.val;
    }

}
