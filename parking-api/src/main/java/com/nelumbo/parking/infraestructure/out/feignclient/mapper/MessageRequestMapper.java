package com.nelumbo.parking.infraestructure.out.feignclient.mapper;

import com.nelumbo.parking.domain.model.MessageModel;
import com.nelumbo.parking.infraestructure.out.feignclient.dto.MessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MessageRequestMapper {
    MessageRequest toMessageRequest(MessageModel messageModel);
}
