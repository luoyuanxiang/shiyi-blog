package com.mojian.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AI配置
 *
 * @author luoyuanxiang
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AiConfig {

    /**
     * API 密钥
     */
    private String apiKey;

    /**
     * 基本网址
     */
    private String baseUrl;

    /**
     * 型
     */
    private String model;
}
