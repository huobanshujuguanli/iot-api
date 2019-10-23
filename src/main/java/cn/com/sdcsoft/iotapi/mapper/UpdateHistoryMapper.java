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
            "<if test='RawState!= null and rawState.length>0 '> " +
            "AND RawState = #{rawState}"+
            "</if>" +
            "<if test='NowState!= null and nowState.length>0 '> " +
            "AND NowState = #{nowState}"+
            "</if>" +
            "<if test='type!= 0  '> " +
            "AND Type = #{type}"+
            "</if>" +
            "</where>" +
            "</script>")
    List<UpdateHistory> findUpdateHistory(UpdateHistory updateHistory);


}
