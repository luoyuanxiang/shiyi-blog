package com.mojian.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mojian.entity.SysFileOss;
import com.mojian.enums.FileOssEnum;
import com.mojian.mapper.SysFileOssMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.FileStorageServiceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author: quequnlong
 * @date: 2025/2/14
 * @description:
 */
@Component
@RequiredArgsConstructor
public class FileStorageInit {

    private final FileStorageService service;

    private final SysFileOssMapper sysFileOssMapper;


    @PostConstruct
    public void init() {
        List<SysFileOss> sysFileOssList = sysFileOssMapper.selectList(null);
        for (SysFileOss sysFileOss : sysFileOssList) {
            switch (sysFileOss.getPlatform()) {
                case "ali":
                    FileStorageProperties.AliyunOssConfig aliyunOssConfig = getAliyunOssConfig(sysFileOss);
                    service.getFileStorageList().addAll(FileStorageServiceBuilder
                            .buildAliyunOssFileStorage(Collections.singletonList(aliyunOssConfig), null));
                    break;
                case "qiniu":
                    FileStorageProperties.QiniuKodoConfig qiniuKodoConfig = getQiniuKodoConfig(sysFileOss);
                    service.getFileStorageList().addAll(FileStorageServiceBuilder
                            .buildQiniuKodoFileStorage(Collections.singletonList(qiniuKodoConfig), null));
                    break;
                case "tencent":
                    FileStorageProperties.TencentCosConfig tencentCosConfig = getTencentCosConfig(sysFileOss);
                    service.getFileStorageList().addAll(FileStorageServiceBuilder
                            .buildTencentCosFileStorage(Collections.singletonList(tencentCosConfig), null));
                    break;
                case "minio":
                    FileStorageProperties.MinioConfig minioConfig = getMinioConfig(sysFileOss);
                    service.getFileStorageList().addAll(FileStorageServiceBuilder
                            .buildMinioFileStorage(Collections.singletonList(minioConfig), null));
                    break;
                default:
                    FileStorageProperties.LocalPlusConfig config = new FileStorageProperties.LocalPlusConfig();
                    config.setPlatform(sysFileOss.getPlatform());
                    config.setBasePath(sysFileOss.getBasePath());
                    config.setStoragePath(sysFileOss.getStoragePath());
                    config.setDomain(sysFileOss.getDomain());
                    service.getFileStorageList().addAll(FileStorageServiceBuilder
                            .buildLocalPlusFileStorage(Collections.singletonList(config)));
                    break;
            }
            if (sysFileOss.getIsEnable() == 1) {
                service.getProperties().setDefaultPlatform(sysFileOss.getPlatform());
            }
        }
    }

    /**
     * 获取 minio 配置
     *
     * @param sysFileOss 系统文件作系统
     * @return {@link FileStorageProperties.MinioConfig }
     */
    private FileStorageProperties.MinioConfig getMinioConfig(SysFileOss sysFileOss) {
        FileStorageProperties.MinioConfig minioConfig = new FileStorageProperties.MinioConfig();
        minioConfig.setPlatform(sysFileOss.getPlatform());
        minioConfig.setAccessKey(sysFileOss.getAccessKey());
        minioConfig.setSecretKey(sysFileOss.getSecretKey());
        minioConfig.setDomain(sysFileOss.getDomain());
        minioConfig.setBucketName(sysFileOss.getBucket());
        minioConfig.setBasePath(sysFileOss.getBasePath());
        minioConfig.setEndPoint(sysFileOss.getDomain());
        return minioConfig;
    }

    /**
     * 获取腾讯 COS 配置
     *
     * @param sysFileOss 系统文件作系统
     * @return {@link FileStorageProperties.TencentCosConfig }
     */
    private FileStorageProperties.TencentCosConfig getTencentCosConfig(SysFileOss sysFileOss) {
        FileStorageProperties.TencentCosConfig tencentCosConfig = new FileStorageProperties.TencentCosConfig();
        tencentCosConfig.setPlatform(sysFileOss.getPlatform());
        tencentCosConfig.setSecretId(sysFileOss.getAccessKey());
        tencentCosConfig.setSecretKey(sysFileOss.getSecretKey());
        tencentCosConfig.setDomain(sysFileOss.getDomain());
        tencentCosConfig.setBucketName(sysFileOss.getBucket());
        tencentCosConfig.setBasePath(sysFileOss.getBasePath());
        tencentCosConfig.setRegion(sysFileOss.getRegion());
        return tencentCosConfig;
    }

    /**
     * 获取 七牛云 配置
     *
     * @param sysFileOss 系统文件作系统
     * @return {@link FileStorageProperties.QiniuKodoConfig }
     */
    private FileStorageProperties.QiniuKodoConfig getQiniuKodoConfig(SysFileOss sysFileOss) {
        FileStorageProperties.QiniuKodoConfig qiniuKodoConfig = new FileStorageProperties.QiniuKodoConfig();
        qiniuKodoConfig.setPlatform(sysFileOss.getPlatform());
        qiniuKodoConfig.setAccessKey(sysFileOss.getAccessKey());
        qiniuKodoConfig.setSecretKey(sysFileOss.getSecretKey());
        qiniuKodoConfig.setDomain(sysFileOss.getDomain());
        qiniuKodoConfig.setBucketName(sysFileOss.getBucket());
        qiniuKodoConfig.setBasePath(sysFileOss.getBasePath());
        return qiniuKodoConfig;
    }

    /**
     * 获取阿里云 OSS 配置
     *
     * @param sysFileOss 系统文件作系统
     * @return {@link FileStorageProperties.AliyunOssConfig }
     */
    private FileStorageProperties.AliyunOssConfig getAliyunOssConfig(SysFileOss sysFileOss) {
        FileStorageProperties.AliyunOssConfig config = new FileStorageProperties.AliyunOssConfig();
        config.setPlatform(sysFileOss.getPlatform());
        config.setAccessKey(sysFileOss.getAccessKey());
        config.setSecretKey(sysFileOss.getSecretKey());
        config.setDomain(sysFileOss.getDomain());
        config.setBucketName(sysFileOss.getBucket());
        config.setBasePath(sysFileOss.getBasePath());
        config.setEndPoint(sysFileOss.getEndpoint());
        return config;
    }

    @Bean
    public WebMvcConfigurer myFileStorageWebMvcConfigurer() {
        SysFileOss sysFileOss = sysFileOssMapper.selectOne(new LambdaQueryWrapper<SysFileOss>()
                .eq(SysFileOss::getPlatform, FileOssEnum.LOCAL.getValue()));

        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                if (sysFileOss != null) {
                    //本地存储升级版
                    registry.addResourceHandler(sysFileOss.getPathPatterns())
                            .addResourceLocations("file:" + sysFileOss.getStoragePath());
                }

            }
        };
    }

}
