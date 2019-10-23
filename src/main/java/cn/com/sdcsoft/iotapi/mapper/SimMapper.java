package cn.com.sdcsoft.iotapi.mapper;

import cn.com.sdcsoft.iotapi.entity.Sim;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Component
public interface SimMapper {

    @Select("<script>" +
            "select * from Sim " +
            "<where>" +
            "1=1 " +
            "<if test='simNo!= null and simNo.length>0 '> " +
            "AND SimNo = #{simNo}"+
            "</if>" +
            "<if test='lifecycle!= null and lifecycle.length>0'> " +
            "AND Lifecycle = #{lifecycle}"+
            "</if>" +
            "<if test='state!= null and state.length>0'> " +
            "AND State = #{state}"+
            "</if>" +
            "</where>" +
            "</script>")
    List<Sim> findSim(Sim sim);


}
