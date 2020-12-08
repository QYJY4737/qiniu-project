package cn.congee.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: yang
 * @Date: 2020-12-08 10:06
 */
@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String prefix;

}
