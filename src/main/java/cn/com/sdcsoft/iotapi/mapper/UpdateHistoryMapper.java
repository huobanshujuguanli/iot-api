package cn.com.sdcsoft.iotapi.mapper;

import cn.com.sdcsoft.iotapi.entity.UpdateHistory;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UpdateHistoryMapper {

    @Select("<script>" +
            "select * from UpdateHistory " +
            "<where>" +
            "1=1 " +
            "<if test='simNo!= null and simNo.length>0 '> " +
            "AND SimNo = #{simNo}"+
            "</if>" +
            "<if test='type!= null  '> " +
            "AND Type = #{type}"+
            "</if>" +
            "</where>" +
            "</script>")
    List<UpdateHistory> findUpdateHistory(UpdateHistory updateHistory);


}
