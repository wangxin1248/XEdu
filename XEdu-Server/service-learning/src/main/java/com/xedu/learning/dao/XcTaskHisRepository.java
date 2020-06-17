package com.xedu.learning.dao;

import com.xedu.framework.domain.task.XcTaskHis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 19:25.
 * @Description: 选课历史信息Dao
 */
public interface XcTaskHisRepository extends JpaRepository<XcTaskHis,String> {
}
