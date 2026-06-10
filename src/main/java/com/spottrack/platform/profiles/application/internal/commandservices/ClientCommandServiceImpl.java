package com.spottrack.platform.profiles.application.internal.commandservices;

import com.spottrack.platform.profiles.application.commandservices.ClientCommandService;
import com.spottrack.platform.profiles.domain.model.aggregates.Client;
import com.spottrack.platform.profiles.domain.model.commands.CreateClientCommand;
import com.spottrack.platform.profiles.domain.model.commands.UpdateClientProfileCommand;
import com.spottrack.platform.profiles.domain.repositories.ClientRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ClientCommandServiceImpl implements ClientCommandService {
    private final ClientRepository clientRepository;

    public ClientCommandServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Result<Client, ApplicationError> handle(CreateClientCommand command){
        try {
            if (clientRepository.existsByEmailAddress(command.emailAddress())){
                return Result.failure(ApplicationError.conflict(
                        "Client",
                        "A client with email address '%s' already exists".formatted(command.emailAddress())
                ));
            }

            var client = new Client(command);
            var savedClient = clientRepository.save(client);
            return Result.success(savedClient);
        } catch (IllegalArgumentException e){
            return Result.failure(ApplicationError.validationError("Client", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "Client creation",
                    e.getMessage()
            ));
        }
    }

    @Override
    public Result<Client, ApplicationError> handle(UpdateClientProfileCommand command){
        try {
            var client = clientRepository.findById(command.clientId());

            if (client.isEmpty()){
                return Result.failure(ApplicationError.notFound(
                        "Client",
                        command.clientId().clientId().toString()
                ));
            }

            client.get().updateProfile(command);
            var savedClient = clientRepository.save(client.get());

            return Result.success(savedClient);
        } catch (IllegalArgumentException e){
            return Result.failure(ApplicationError.validationError("Client", e.getMessage()));
        } catch (Exception e){
            return Result.failure(ApplicationError.unexpected("Client update", e.getMessage()));
        }
    }
}
