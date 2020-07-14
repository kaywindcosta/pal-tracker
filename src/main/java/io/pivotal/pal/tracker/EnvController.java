package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {


    private final String port;
    private final String memoryLimit;
    private final String cfInstanceIndex;
    private final String cfInstanceAddr;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memoryLimit:NOT SET}") String memoryLimit,
                         @Value("${cfInstanceIndex:NOT SET}") String cfInstanceIndex,
                         @Value("${cfInstanceAddr:NOT SET}") String cfInstanceAddr){

        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddr = cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> result=new HashMap<>();
        result.put("PORT",port);
        result.put("MEMORY_LIMIT",memoryLimit);
        result.put("CF_INSTANCE_INDEX",cfInstanceIndex);
        result.put("CF_INSTANCE_ADDR",cfInstanceAddr);
        return result;
    }
}
