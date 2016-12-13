package webui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by admin on 01.11.2016.
 */
@Configuration
@ComponentScan({"infrastructure.service.*"})
public class ServiceConfig {
}
