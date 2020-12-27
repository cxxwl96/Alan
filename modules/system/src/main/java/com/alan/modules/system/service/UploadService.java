package com.alan.modules.system.service;

import com.alan.modules.system.domain.Upload;

/**
 * @author cxxwl96@sina.com
 * @date 2020/11/02
 */
public interface UploadService {

    /**
     * 获取文件sha1值的记录
     * @param sha1 文件sha1值
     * @return 文件信息
     */
    Upload getBySha1(String sha1);

    /**
     * 保存文件上传
     * @param upload 文件上传实体类
     * @return 文件信息
     */
    Upload save(Upload upload);
}

