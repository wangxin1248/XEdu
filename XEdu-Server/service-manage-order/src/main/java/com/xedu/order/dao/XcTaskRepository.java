package com.xedu.order.dao;

import com.xedu.framework.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/17 15:29.
 * @Description: 任务表处理dao
 */

public interface XcTaskRepository extends JpaRepository<XcTask,String> {
    /**
     * 查询某个时间之前的前n条记录
     * @param pageable
     * @param updateTime
     * @return
     */
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    /**
     * 更新任务的updateTime
     * @param id
     * @param updateTime
     * @return
     */
    @Modifying
    @Query("update XcTask t set t.updateTime = :updateTime where t.id = :id")
    int updateTaskTime(@Param(value="id") String id,@Param(value="updateTime") Date updateTime);

    /**
     * 更新任务的version版本号
     * @param id
     * @param version
     * @return
     */
    @Modifying
    @Query("update XcTask t set t.version = :version+1 where t.id = :id and t.version = :version")
    int updateTaskVersion(@Param(value="id") String id,@Param(value="version") int version);
}
