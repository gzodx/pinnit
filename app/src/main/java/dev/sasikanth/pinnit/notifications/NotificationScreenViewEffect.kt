package dev.sasikanth.pinnit.notifications

import java.util.UUID

sealed class NotificationScreenViewEffect

data class OpenNotificationEditorViewEffect(val notificationUuid: UUID) : NotificationScreenViewEffect()

data class UndoNotificationDeleteViewEffect(val notificationUuid: UUID) : NotificationScreenViewEffect()
