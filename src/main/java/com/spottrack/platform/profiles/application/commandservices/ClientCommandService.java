package com.spottrack.platform.profiles.application.commandservices;

import com.spottrack.platform.profiles.domain.model.aggregates.Client;
import com.spottrack.platform.profiles.domain.model.commands.CreateClientCommand;
import com.spottrack.platform.profiles.domain.model.commands.UpdateClientProfileCommand;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface ClientCommandService {
    Result<Client, ApplicationError> handle(CreateClientCommand command);
    Result<Client, ApplicationError> handle(UpdateClientProfileCommand command);
}
