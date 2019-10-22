package cn.com.sdcsoft.iotapi.controller;


import cn.com.sdcsoft.iotapi.entity.UpdateHistory;
import cn.com.sdcsoft.iotapi.mapper.UpdateHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/updateHistory")
public class UpdateHistoryController {

    @Autowired
    private UpdateHistoryMapper updateHistoryMapper;

    @PostMapping("/find")
    public List<UpdateHistory> find(UpdateHistory updateHistory) {
        return updateHistoryMapper.findUpdateHistory(updateHistory);
    }
}
