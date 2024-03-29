package cn.com.sdcsoft.iotapi.controller;


import cn.com.sdcsoft.iotapi.entity.Sim;
import cn.com.sdcsoft.iotapi.mapper.SimMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/sim")
public class SimController {

    @Autowired
    private SimMapper simMapper;

    @PostMapping("/find")
    public List<Sim> find(@RequestBody Sim sim) {
        return simMapper.findSim(sim);
    }

}
