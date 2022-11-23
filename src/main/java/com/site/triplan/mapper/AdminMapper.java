package com.site.triplan.mapper;

import ch.qos.logback.core.pattern.PostCompileProcessor;
import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserVo> findAll();
}
