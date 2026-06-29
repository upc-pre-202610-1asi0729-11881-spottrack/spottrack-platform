package com.spottrack.platform.membership.domain.model.valueobjects;

import java.util.UUID;

public record UserId(UUID uuid) {
  public UserId {

    if (uuid == null) {
      throw new IllegalArgumentException("uuid must not be null or blank");
    }

  }

}
