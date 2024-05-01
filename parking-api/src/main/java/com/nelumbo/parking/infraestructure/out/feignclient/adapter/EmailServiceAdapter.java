package com.nelumbo.parking.infraestructure.out.feignclient.adapter;

import com.nelumbo.parking.domain.model.MessageModel;
import com.nelumbo.parking.domain.ports.out.IMessageServicePort;
import com.nelumbo.parking.infraestructure.out.feignclient.EmailFeignClient;
import com.nelumbo.parking.infraestructure.out.feignclient.mapper.MessageRequestMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailServiceAdapter implements IMessageServicePort {

    private final EmailFeignClient emailFeignClient;
    private final MessageRequestMapper messageRequestMapper;
    @Override
    public void sendMessage(MessageModel messageModel) {
        emailFeignClient.sendEmail(messageRequestMapper.toMessageRequest(messageModel));
    }
}
