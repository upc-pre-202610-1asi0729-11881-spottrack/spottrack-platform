package com.spottrack.platform.iam.domain.model.commands;

public record ResetPasswordCommand(String username, String newPassword) {
}
