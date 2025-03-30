package com.burmic.cloudy.Repositories;


import com.burmic.cloudy.Entities.Message;
import com.burmic.cloudy.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findBySenderAndReceiverOrSenderAndReceiver(User sender1,User receiver1,User sender2,User receiver2);
    List<Message> findBySenderOrReceiver(User sender,User receiver);
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
}
