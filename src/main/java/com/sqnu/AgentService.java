package com.sqnu;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
@Service
public class AgentService {
    private StringBuilder conversationMemory;
    private ObjectMapper mapper;
    private String currentQuestion;
    private final HttpClient httpClient;

    public AgentService() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        initChat();//初始化状态
    }
    public String initChat() {
        conversationMemory = new StringBuilder();
        currentQuestion = "你好，我是你的专属AI大厂面试官。请介绍一下你最熟悉的计算机网络协议（如TCP/UDP/HTTP),并聊一聊它在项目实际中是如何工作的？";
        return currentQuestion;
    }
    //处理用户发来的消息
    public AgentResponse processUserMessage(String userAnswer) {
        //1.更新项目
        conversationMemory.append("面试官的问题：").append(currentQuestion).append("\n");
        conversationMemory.append("候选人回答：").append(userAnswer).append("\n");

        try{
            //2.使用jackson构造JSON,彻底解决手动拼接带来的格式错误
            ObjectNode requestBody = mapper.createObjectNode();
            requestBody.put("model", "deepseek-r1:7b");
            requestBody.put("prompt","你现在是大厂高级技术面试官。请根据以下对话历史，对候选人的最新回答进行深度评估。\n\n【对话历史】:\n"
                    +conversationMemory.toString()
                    +"\n\n请严格返回如下格式的JSON，不要输出任何Markdown格式标记或思维链。格式：{\"field\":\"考察的技术领域\",\"evaluation\":\"对本次回答的深度点评\",\"nextQuestion\":\"下一个底层原理追问\"}");
            requestBody.put("format","json");
            requestBody.put("stream",false);

            String jsonInputString = mapper.writeValueAsString(requestBody);
            //构建现代的httprquest,锁住http协议
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type","application/json;utf-8")
                    .timeout(Duration.ofSeconds(60))//给模型思考时间
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString,StandardCharsets.UTF_8))
                    .build();
            //发送请求并获取响应
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if(statusCode >=200 && statusCode < 300){

                JsonNode rootNode = mapper.readTree(response.body());
                String jsonContent = rootNode.get("response").asText();
                AgentResponse result = mapper.readValue(jsonContent,AgentResponse.class);

                //防御性兜底
                result.setField(result.getField()!=null?result.getField():"通用技术");
                result.setEvaluation(result.getEvaluation()!=null?result.getEvaluation():"回答有待深入，情节和底层原理说明。");
                result.setNextQuestion(result.getNextQuestion()!=null?result.getNextQuestion():"请结合实际生产环境（如高并发、网络抖动）聊聊你的排查思路？");

                //更新当前问题，进入下一轮
                currentQuestion = result.getNextQuestion();
                return result;
            }else{
                throw new RuntimeException("Ollama返回错误代码："+statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AgentResponse errorResponse = new AgentResponse();
            errorResponse.setField("系统错误");
            errorResponse.setEvaluation("本地 AI 模型请求失败，请检查 Ollama 后台是否启动。");
            errorResponse.setNextQuestion("是否要重新开始？");
            return errorResponse;
        }
    }
}
