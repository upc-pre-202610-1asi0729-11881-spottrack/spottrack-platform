package com.spottrack.platform.iam.application.commandservices;

import com.spottrack.platform.iam.domain.model.aggregates.User;
import com.spottrack.platform.iam.domain.model.commands.SignUpCommand;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface UserCommandService {
    Result<User, ApplicationError> handle(SignUpCommand command);
}
