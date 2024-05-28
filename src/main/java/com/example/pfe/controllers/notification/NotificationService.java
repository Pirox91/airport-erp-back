package com.example.pfe.controllers.notification;

import com.example.pfe.assigned.AssignedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(AssignedDTO message) {
        messagingTemplate.convertAndSend("/notifications", message);
    }
}