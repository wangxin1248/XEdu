package com.xedu.order.dao;

import com.xedu.framework.domain.task.XcTaskHis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 20:50.
 * @Description: 历史任务Dao
 */
public interface XcTaskHisRepository extends JpaRepository<XcTaskHis,String> {
}
