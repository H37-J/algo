package hjk.algo.config;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MybatisMssqlConfig {

    public SqlSessionTemplate sessionTemplate;
}
