package com.virtualgame.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mysql")
@Getter
@Setter
public class MySqlProperties {
    private boolean sshEnable;
    private String driver;
    private String url;
    private String host;
    private int localPort;
    private int remotePort;
    private String user;
    private String password;
    private String schema;
}
