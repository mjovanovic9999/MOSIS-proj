package mosis.streetsandtotems.core

object ButtonConstants {
    const val SIGN_IN = "Sign In"
    const val SIGN_IN_GOOGLE = "Sign in with Google"
    const val SIGN_IN_FACEBOOK = "Sign in with Facebook"
    const val FORGOT_PASSWORD = "Forgot password?"
    const val NO_ACCOUNT = "Don't have an account?"
    const val SIGN_UP_GOOGLE = "Sign up with Google"
    const val SIGN_UP_FACEBOOK = "Sign up with Facebook"
    const val SIGN_UP = "Sign Up"
    const val EDIT_PROFILE = "Edit profile"
    const val CHANGE_PASSWORD = "Change password"
    const val SAVE = "Save"
    const val CANCEL = "Cancel"
    const val DROP = "Drop"
    const val PLACE = "Place"
}

object FormFieldNamesConstants {
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val PHONE_NUMBER = "phoneNumber"
    const val REPEAT_PASSWORD = "repeatPassword"
    const val CURRENT_PASSWORD = "currentPassword"
    const val NEW_PASSWORD = "newPassword"
}

object ImageContentDescriptionConstants {
    const val LOGO_TEXT = "Streets and totems logo"
    const val SETTINGS = "Settings icon"
    const val BULLET = "Numbering dot"
    const val LEADERBOARD = "Leaderboard icon"
    const val EDIT_PASSWORD = "Edit password icon"
    const val TOTEM = "Totem logo"
    const val EMERALD = "Emerald icon"
    const val BRICKS = "Bricks icon"
    const val WOOD = "Wood icon"
    const val STONE = "Stone icon"
}

object PinConstants {
    const val MY_PIN = "0"
}

object LocationConstants {
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"
}

object NotificationConstants {
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1
}

object VisualTransformationConstants {
    const val PASSWORD = "*"
}

object FormFieldLengthConstants {
    const val PASSWORD = 8
}

object MessageConstants {
    const val USERNAME_REQUIRED = "Username is required!"
    const val PASSWORD_REQUIRED = "Password is required!"
    const val PASSWORD_LENGTH = "Minimum 8 characters needed!"
    const val FIRST_NAME_REQUIRED = "First name is required!"
    const val LAST_NAME_REQUIRED = "Last name is required!"
    const val PHONE_NUMBER_REQUIRED = "Phone number is required!"
    const val INVALID_PHONE_NUMBER = "Invalid phone number!"
    const val REPEAT_PASSWORD_REQUIRED = "Repeating password is required!"
    const val NEW_PASSWORD_REQUIRD = "New password is required!"
    const val CURRENT_PASSWORD_REQUIRED = "Current password is required!"
    const val DIALOG_PERMISSION_TITLE = "Location permission is disabled"
    const val DIALOG_PERMISSION_TEXT =
        "In order to use Streets And Totems location permission has to be granted!"
    const val DIALOG_PERMISSION_CONFIRM_BUTTON = "Turn on permissions"
    const val DIALOG_PERMISSION_DISMISS_BUTTON = "Close app"
    const val DIALOG_LOCATION_TITLE = "Location disabled"
    const val DIALOG_LOCATION_TEXT =
        "In order to use Streets And Totems Location has to be turned on!"
    const val DIALOG_LOCATION_CONFIRM_BUTTON = "Turn on location"
    const val DIALOG_LOCATION_DISMISS_BUTTON = "Close app"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords don't match!"

}

object TitleConstants {
    const val SIGN_UP = "Sign up"
    const val PROFILE = "My profile"
    const val EDIT_PROFILE = "Edit profile"
    const val EDIT_PASSWORD = "Edit password"
    const val BACKPACK = "Backpack"
}

object FormFieldConstants {
    const val USERNAME = "Username"
    const val PASSWORD = "Password"
    const val FIRST_NAME = "First name"
    const val LAST_NAME = "Last name"
    const val PHONE_NUMBER = "Phone number"
    const val REPEAT_PASSWORD = "Repeat password"
    const val PROFILE_PHOTO = "Add a profile photo"
    const val CHANGE_PHOTO = "Click again to change the photo"
    const val NEW_PASSWORD = "New password"
    const val CURRENT_PASSWORD = "Current password"
    const val AMOUNT = "Amount"
    const val DEFAULT_AMOUNT = "1"
}

object NavBarConstants {
    const val BACKPACK = "Backpack"
    const val MAP = "Map"
    const val TOTEMS = "Totems"
}

object RegexConstants {
    const val PHONE_NUMBER = "^\\+?[0-9]+\$"
}

object DrawerConstants {
    const val SETTINGS = "Settings"
    const val NOTIFIACTIONS = "Enable notifications"
    const val RUN_IN_BACKGROUND = "Run in background"
    const val SHOW_PHONE_NUMBER = "Show my phone number"
    const val CALL_SETTINGS = "Who is allowed to call me"
    const val SMS_SETTINGS = "Who is allowed to message me"
    const val LEADERBOARD = "Leaderboard"
    const val PROFILE = "Visit profile"
    const val LEAVE_SQUAD = "Leave squad"
    const val SIGN_OUT = "Sign out"
    val DROPDOWN_SELECT_LIST = listOf("Everyone", "Only squad members", "No one")
}

object ItemsConstants {
    const val STONE = "Stone"
    const val WOOD = "Wood"
    const val BRICK = "Brick"
    const val EMERALD = "Emerald"
    const val TOTEM = "Totem"
    const val ITEMS_LEFT = " left"
}

object TotemItemsConstants {
    const val TOTEM_ID = "Totem id:"
    const val PLACED_BY = "Placed by:"
}