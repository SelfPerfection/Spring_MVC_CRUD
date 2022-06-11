package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@PropertySource("classpath:site.properties")
public class SiteService {

    @Autowired
    private Environment env;

    @Bean(name = "homeUrl")
    public Supplier<String> homeUrl() {
        return () -> env.getProperty("site.url");
    }

    @Bean(name = "siteTitle")
    public Supplier<String> siteTitle() {
        return () -> env.getProperty("site.name");
    }

}
