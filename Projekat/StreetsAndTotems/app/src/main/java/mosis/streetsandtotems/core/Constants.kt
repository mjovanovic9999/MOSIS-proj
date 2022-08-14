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
    const val DISABLE_BACKGROUND_SERVICE_BUTTON = "DISABLE"

}

object ImageContentDescriptionConstants {
    const val LOGO_TEXT = "Streets and totems logo"
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
    const val DIALOG_PERMISSION_TITLE = "Location permission is disabled"
    const val DIALOG_PERMISSION_TEXT =
        "In order to use Streets And Totems location permission has to be granted!"
    const val DIALOG_LOCATION_TITLE = "Location disabled"
    const val DIALOG_LOCATION_TEXT =
        "In order to use Streets And Totems Location has to be turned on!"
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

object PathConstants {
    const val IMAGES = "image/*"
}

object WorkerKeys {
    const val NEW_LOCATION = "newLocation"
    const val ERROR = "error"
    const val LOCATION_DISABLED = "locationDisabled"
}

object NotificationsConstants {
    const val CHANNEL_ID = "StreetsAndTotems_channel"
    const val CHANNEL_NAME = "Streets And Totems Notifications"
    const val DISABLE_BACKGROUND_SERVICE_TITLE = "Application is running in the background"
    const val DISABLE_BACKGROUND_SERVICE_TEXT =
        "If you disable this you won't be able to get push notifications"
    const val DISABLE_BACKGROUND_SERVICE_ID = 1
}

