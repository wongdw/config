package priv.wdw.config.apollo.service1;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>@author wdw</p>
 * <p>@date 2020/6/21 22:58</p>
 * <p>@description </p>
 */
@EnableApolloConfig  //开启Apollo
@SpringBootApplication
public class ApolloService1App {

    public static void main(String[] args) {
        SpringApplication.run(ApolloService1App.class, args);
    }
}
