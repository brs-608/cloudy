package com.burmic.cloudy.Controllers;

import com.burmic.cloudy.Entities.Message;
import com.burmic.cloudy.Entities.User;
import com.burmic.cloudy.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class MessageController {
    @Autowired
    MessageService messageService;

//    @PostMapping("/send-message")
//    public void sendMessage(@RequestBody Map<String,String> message){
//        messageService.sendMessage(message.get("text"),message.get("sender_id"),message.get("receiver_id"));
//    }
    @GetMapping("/history/{user1}/{user2}")
    public List<Message> getConversation(@PathVariable("user1") long user1,@PathVariable("user2") long user2){
        return messageService.getConversation(user1,user2);
    }

    @GetMapping("/history/{email}")
    public List<Map<String,Object>> chatHistory(@PathVariable("email") String email){
        return messageService.chatHistory(email);
    }
}
