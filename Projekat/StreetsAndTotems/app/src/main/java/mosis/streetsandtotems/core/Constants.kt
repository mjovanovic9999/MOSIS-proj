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
    const val DIALOG_PERMISSION_CONFIRM_BUTTON = "Turn on permissions"
    const val DIALOG_PERMISSION_DISMISS_BUTTON = "Close app"
    const val DIALOG_LOCATION_CONFIRM_BUTTON = "Turn on location"
    const val DIALOG_LOCATION_DISMISS_BUTTON = "Close app"
    const val TURN_OFF_BACKGROUND_SERVICE_BUTTON = "TURN OFF"
    const val DIALOG_NETWORK_DISMISS_BUTTON = "Close app"

}

object ImageContentDescriptionConstants {
    const val LOGO_TEXT = "Streets and totems logo"
    const val SETTINGS = "Settings icon"
    const val BULLET = "Numbering dot"
    const val ACCOUNT = "Account icon"
    const val LEADERBOARD = "Leaderboard icon"
}

object PinConstants {
    const val MY_PIN = "0"
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
    const val DIALOG_PERMISSION_TITLE = "Location permission is disabled"
    const val DIALOG_PERMISSION_TEXT =
        "In order to use Streets And Totems location permission has to be granted!"
    const val DIALOG_LOCATION_TITLE = "Location disabled"
    const val DIALOG_LOCATION_TEXT =
        "In order to use Streets And Totems Location has to be turned on!"
    const val DIALOG_NETWORK_TITLE = "Network is unavailable"
    const val DIALOG_NETWORK_TEXT =
        "Please check network connectivity in order to use Streets And Totems!"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords don't match!"
}

object TitleConstants {
    const val SIGN_UP = "Sign up"
}

object FormFieldConstants {
    const val USERNAME = "Username"
    const val PASSWORD = "Password"
    const val FIRST_NAME = "First name"
    const val LAST_NAME = "Last name"
    const val PHONE_NUMBER = "Phone number"
    const val REPEAT_PASSWORD = "Repeat password"
    const val PROFILE_PHOTO = "Add a profile photo"
    const val CHANGE_PHOTO = "Click again to change photo"
}

object NavBarConstants {
    const val BACKPACK = "Backpack"
    const val MAP = "Map"
    const val TOTEMS = "Totems"
}

object RegexConstants {
    const val PHONE_NUMBER = "^\\+?[0-9]+\$"
}

object DrawerConstants{
    const val SETTINGS = "Settings"
    const val NOTIFIACTIONS = "Enable notifications"
    const val RUN_IN_BACKGROUND = "Run in background"
    const val SHOW_PHONE_NUMBER = "Show my phone number"
    const val CALL_SETTINGS = "Who can call me"
    const val SMS_SETTINGS = "Who can message me"
    const val LEADERBOARD = "Leaderboard"
    const val PROFILE = "Visit profile"
    const val LEAVE_SQUAD = "Leave squad"
    const val SIGN_OUT = "Sign out"
    val DROPDOWN_SELECT_LIST = listOf("Everyone", "Only squad members", "No one")
}

object WorkerKeys {
    const val NEW_LOCATION = "newLocation"
    const val ERROR = "error"
    const val LOCATION_DISABLED = "locationDisabled"
}

object NotificationConstants {
    const val CHANNEL_ID = "StreetsAndTotems_channel"
    const val CHANNEL_NAME = "Streets And Totems Notifications"
    const val DISABLE_BACKGROUND_SERVICE_TITLE = "Application is running in the background"
    const val DISABLE_BACKGROUND_SERVICE_TEXT =
        "If you disable this you won't be able to get push notifications"
    const val DISABLE_BACKGROUND_SERVICE_ID = 1


    const val CHANNEL_ID2 = "StreetsAndTotems_channel2"
    const val CHANNEL_NAME2 = "Streets And Totems Notifications2"
}