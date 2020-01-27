package com.pts.mds;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@OpenAPIDefinition(info = @Info(title = "Template API for MDS"
		, version = "v1"
		, description = "Template API discription for MDS"
		, license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
		, contact = @Contact(url = "https://github.com/chenjun420/", name = "Harry.Chan", email = "chenpi_cn@hotmail.com")))
public class MdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdsApplication.class, args);
	}

}
