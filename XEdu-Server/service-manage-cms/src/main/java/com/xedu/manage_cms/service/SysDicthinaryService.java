package com.xedu.manage_cms.service;

import com.xedu.framework.domain.system.SysDictionary;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.manage_cms.dao.SysDicthinaryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/28 15:57.
 * @Description:
 */
@Service
public class SysDicthinaryService {
    @Autowired
    SysDicthinaryRepository sysDicthinaryRepository;

    /**
     * 根据传入的 d_type 返回对应的 SysDictionary 字典对象
     * @param type 数组字典的 type 类型
     * @return SysDictionary 字典对象
     */
    public SysDictionary getByType(String type){
        // 判断参数是否合法
        if(StringUtils.isEmpty(type)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 调用dao对应的查询方法
        return sysDicthinaryRepository.findByDType(type);
    }
}
