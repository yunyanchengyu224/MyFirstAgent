package com.sqnu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class ChatController {
    @Autowired
    private AgentService agentService;

    //获取初始问题
    @GetMapping("/init")
    public String init(){
        return agentService.initChat();
    }
    //接受候选人回答，并返回ai点评
    @PostMapping("/chat")
    public AgentResponse chat(@RequestBody Map<String,String> request){
        String userAnswer = request.get("message");
        return agentService.processUserMessage(userAnswer);
    }
}
