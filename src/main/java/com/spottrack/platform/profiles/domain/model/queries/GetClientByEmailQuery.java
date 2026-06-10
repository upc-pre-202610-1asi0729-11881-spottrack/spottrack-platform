package com.spottrack.platform.profiles.domain.model.queries;

import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;

public record GetClientByEmailQuery(EmailAddress emailAddress) {
}
