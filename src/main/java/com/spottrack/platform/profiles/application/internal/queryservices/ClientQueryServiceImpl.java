package com.spottrack.platform.profiles.application.internal.queryservices;

import com.spottrack.platform.profiles.application.queryservices.ClientQueryService;
import com.spottrack.platform.profiles.domain.model.aggregates.Client;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByEmailQuery;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByIdQuery;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByUserIdQuery;
import com.spottrack.platform.profiles.domain.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientQueryServiceImpl implements ClientQueryService {
    private final ClientRepository clientRepository;

    public ClientQueryServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> handle(GetClientByIdQuery query) {
        return clientRepository.findById(query.clientId());
    }

    @Override
    public Optional<Client> handle(GetClientByUserIdQuery query) {
        return clientRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Client> handle(GetClientByEmailQuery query) {
        return clientRepository.findByEmailAddress(query.emailAddress());
    }
}
