package com.virtualgame.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ssh")
@Getter
@Setter
public class SSHProperties {
    private boolean enable;
    private String host;
    private int hostPort;
    private String user;
    private String password;
    private String privateKeyPath;
    private String privateKeyPassword;
}