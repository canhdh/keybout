package com.adeliosys.keybout.model

/**
 * Base class for the notifications.
 */
open class BaseNotification(val type:String)

/**
 * Sent when the user name is incorrect (empty for example).
 */
class IncorrectNameNotification: BaseNotification(Constants.NOTIFICATION_INCORRECT_NAME)

/**
 * Sent when the user name is already used.
 */
class UsedNameNotification: BaseNotification(Constants.NOTIFICATION_USED_NAME)

/**
 * Sent when the games list changed.
 */
class GamesListNotification(val games:Collection<GameDescriptor>): BaseNotification(Constants.NOTIFICATION_GAMES_LIST)