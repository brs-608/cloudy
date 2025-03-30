package com.burmic.cloudy.Services;

import com.burmic.cloudy.Entities.Message;
import com.burmic.cloudy.Entities.User;
import com.burmic.cloudy.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserService userService;

    public Message sendMessage(String text, long sender_id,long receiver_id){
        User sender = userService.getUser(sender_id);
        User receiver = userService.getUser(receiver_id);
        if(sender != null && receiver != null){
            Message message = new Message();
            message.setText(text);
            message.setReceiver(receiver);
            message.setSender(sender);
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getConversation(long user1Id,long user2Id){
        User user1 = userService.getUser(user1Id);
        User user2 = userService.getUser(user2Id);
        if(user1 != null && user2!= null){
            return messageRepository.findBySenderAndReceiverOrSenderAndReceiver(user1,user2,user2,user1);
        }
        return null;
    }
    public List<Map<String,Object>> chatHistory(String email){
        User user = userService.getUserByEmail(email);
        List<User> history = new ArrayList<>();
        List<Map<String,Object>> chatHistory = new ArrayList<>();
        if(user != null){
            List<Message> sentByUsers = messageRepository.findBySender(user);
            List<Message> receivedByUsers = messageRepository.findByReceiver(user);

            for(Message message : sentByUsers){
                List<Message> oldMessages = messageRepository.findBySenderAndReceiverOrSenderAndReceiver(message.getSender(),message.getReceiver(),message.getReceiver(),message.getSender());
                if(!history.contains(message.getReceiver())){
                    history.add(message.getReceiver());
                    Map<String,Object> oldMessage = new HashMap<>();
                    oldMessage.put("partner",message.getReceiver());
                    oldMessage.put("lastMessage",oldMessages.get(oldMessages.size()-1));
                    oldMessage.put("user",user);
                    chatHistory.add(oldMessage);
                }
            }
            for (Message message : receivedByUsers){
                if(!history.contains(message.getSender())){
                    List<Message> oldMessages = messageRepository.findBySenderAndReceiverOrSenderAndReceiver(message.getSender(),message.getReceiver(),message.getReceiver(),message.getSender());
                    history.add(message.getSender());
                    Map<String,Object> oldMessage = new HashMap<>();
                    oldMessage.put("partner",message.getSender());
                    oldMessage.put("lastMessage",oldMessages.get(oldMessages.size() -1));
                    oldMessage.put("user",user);
                    chatHistory.add(oldMessage);
                }
            }
        }
        return chatHistory;
    }
}
