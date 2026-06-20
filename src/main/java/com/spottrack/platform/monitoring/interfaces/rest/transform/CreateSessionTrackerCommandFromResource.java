package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.commands.CreateSessionTrackerCommand;
import com.spottrack.platform.monitoring.domain.model.valueObjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.UsageActivity;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CreateSessionTrackerResource;

public class CreateSessionTrackerCommandFromResource {
    public static CreateSessionTrackerCommand toCommandFromResource(CreateSessionTrackerResource resource){
        return new CreateSessionTrackerCommand(new SessionTrackerId(resource.sessionTrackerId()), new ReservationId(resource.reservationId()), resource.sessionIsActive(), resource.sessionIsInactive(), new UsageActivity(resource.continuousActivity(), resource.seconds()));
    }


}
