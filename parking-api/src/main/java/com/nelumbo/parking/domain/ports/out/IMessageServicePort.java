package com.nelumbo.parking.domain.ports.out;

import com.nelumbo.parking.domain.model.MessageModel;

public interface IMessageServicePort {
    void sendMessage(MessageModel messageModel);
}
