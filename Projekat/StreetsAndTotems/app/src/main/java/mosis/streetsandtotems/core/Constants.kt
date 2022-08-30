package mosis.streetsandtotems.core

import kotlin.math.PI

object DatabaseConstants {
    const val DATABASE_NAME = "local_db"
}

object FirestoreConstants {
    const val USER_IN_GAME_DATA_COLLECTION = "user_in_game_data"
    const val RESOURCES_COLLECTION = "resources"

    const val ID_FIELD = "__name__"
}

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
    const val EDIT_PROFILE = "Edit profile"
    const val CHANGE_PASSWORD = "Change password"
    const val SAVE = "Save"
    const val CANCEL = "Cancel"
    const val DROP = "Drop"
    const val PLACE = "Place"
    const val DIALOG_BACKGROUND_PERMISSION_CONFIRM_BUTTON = "Grant"
    const val DIALOG_BACKGROUND_PERMISSION_DISMISS_BUTTON = "Don't grant"
    const val SHOW_ON_MAP = "Show on map"
    const val KICK = "Start kick voting"
    const val INVITE_TO_SQUAD = "Invite to squad"
    const val TRADE = "Invite to trade"
    const val YES = "Yes"
    const val NO = "No"
    const val ACCEPT = "Accept"
    const val DECLINE = "Decline"
    const val DISMISS = "Dismiss"
}

object FormFieldNamesConstants {
    const val EMAIL = "email"
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
    const val MY_PIN_COLOR_OPACITY = 0x551a88e9
    const val MY_PIN_COLOR = 0xFF1a88e9
    const val MY_PIN_RADIUS = 20f
    const val LAZY_LOADER_ID = "0"
    const val MY_PIN = "MY_PIN"

    const val MARKET = "MARKET"
    const val HOME = "HOME"
    const val HOME_DISCOVERY = "HOME_DISCOVERY"
    const val RESOURCES = "RESOURCES"
    const val RESOURCES_EMERALDS = RESOURCES + "_EMERALDS"
    const val RESOURCES_BRICKS = RESOURCES + "_BRICKS"
    const val RESOURCES_WOODS = RESOURCES + "_WOODS"
    const val RESOURCES_STONES = RESOURCES + "_STONES"
    const val FRIENDS = "FRIENDS"
    const val TOTEMS = "TOTEMS"
    const val CUSTOM = "CUSTOM"
    const val OTHER_PLAYER = "OTHER_PLAYER"
    const val OTHER_HOME = "OTHER_HOME"


}

object MapConstants {
    const val LEVEL_COUNT = 17
    const val TITLE_SIZE = 512
    const val WORKER_COUNT = 32
    const val INIT_SCROLL_X = 0.560821
    const val INIT_SCROLL_Y = 0.366226
    const val INIT_SCROLL_LAT = 43.321198
    const val INIT_SCROLL_LNG = 21.895673
    const val DEGREES_TO_RADIANS_COEFFICIENT = PI / 180
    const val RADIANS_TO_DEGREES_COEFFICIENT = 180 / PI
    const val MAX_SCALE = 2.5f
    const val MY_LOCATION_CIRCLE_SIZE = 60f
    const val TILE_URL_512 = "https://api.maptiler.com/maps/openstreetmap/"
    const val TILE_URL_256 = "https://api.maptiler.com/maps/openstreetmap/256/"
    const val TILE_KEY = "njA6yIfsMq23cZHLTop1"
    const val MAP_PRECISION_METERS = 2f
    const val MAXIMUM_IGNORE_ACCURACY_METERS = 100f
    const val MAXIMUM_TRADE_DISTANCE_IN_METERS = 20f
}

object VisualTransformationConstants {
    const val PASSWORD = "*"
}

object FormFieldLengthConstants {
    const val PASSWORD = 8
}

object HandleResponseConstants {
    const val DEFAULT_ID = 0
    const val ID_ADDITION_FACTOR = 1
}

object MessageConstants {
    const val SIGN_UP_ERROR = "An error occured while signing up! Please try again!"
    const val SIGNED_UP = "Sign up successful!"
    const val SIGNED_OUT = "Sign out successful!"
    const val INVALID_CREDENTIALS = "Wrong email and/or password!"
    const val SIGNED_IN = "Sign in successful!"
    const val INVALID_EMAIL = "Invalid email!"
    const val EMAIL_REQUIRED = "Email is required!"
    const val PASSWORD_REQUIRED = "Password is required!"
    const val PASSWORD_LENGTH = "Minimum 8 characters needed!"
    const val FIRST_NAME_REQUIRED = "First name is required!"
    const val LAST_NAME_REQUIRED = "Last name is required!"
    const val PHONE_NUMBER_REQUIRED = "Phone number is required!"
    const val INVALID_PHONE_NUMBER = "Invalid phone number!"
    const val REPEAT_PASSWORD_REQUIRED = "Repeating password is required!"
    const val NEW_PASSWORD_REQUIRED = "New password is required!"
    const val CURRENT_PASSWORD_REQUIRED = "Current password is required!"
    const val DIALOG_PERMISSION_TITLE = "Location permission is disabled"
    const val DIALOG_PERMISSION_TEXT =
        "In order to use Streets And Totems precise and background location permission has to be granted!"
    const val DIALOG_LOCATION_TITLE = "Location disabled"
    const val DIALOG_LOCATION_TEXT =
        "In order to use Streets And Totems Location has to be turned on!"
    const val DIALOG_NETWORK_TITLE = "Network is unavailable"
    const val DIALOG_NETWORK_TEXT =
        "Please check network connectivity in order to use Streets And Totems!"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords don't match!"
    const val DIALOG_BACKGROUND_PERMISSION_TITLE = "Background location permission is disabled"
    const val DIALOG_BACKGROUND_PERMISSION_TEXT =
        "In order to use Streets And Totems in background, background location permission has to be granted!"
}

object TitleConstants {
    const val SIGN_UP = "Sign up"
    const val PROFILE = "My profile"
    const val EDIT_PROFILE = "Edit profile"
    const val EDIT_PASSWORD = "Edit password"
    const val BACKPACK = "Backpack"
    const val LEADERBOARD = "Leaderboard"
    const val MORE_INFORMATION = "More information"
    const val CUSTOM_PIN_DIALOG_SQUAD = "Place note pin for your squad"
    const val CUSTOM_PIN_DIALOG_SOLO = "Place note pin for yourself"
    const val LEAVE_SQUAD = "Are you sure you want to leave your squad?"
}

object FormFieldConstants {
    const val EMAIL = "Email"
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
    const val NOTIFICATIONS = "Enable notifications"
    const val RUN_IN_BACKGROUND = "Run in background"
    const val SHOW_PHONE_NUMBER = "Show my phone number"
    const val CALL_SETTINGS = "Who is allowed to call me"
    const val SMS_SETTINGS = "Who is allowed to message me"
    const val LEADERBOARD = "Leaderboard"
    const val PROFILE = "Visit profile"
    const val LEAVE_SQUAD = "Leave squad"
    const val SIGN_OUT = "Sign out"
    val DROPDOWN_SELECT_LIST = listOf("No one", "Only squad members", "Everyone")
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
    const val CHANNEL_NAME2 = "Streets And Totems Nearby Notifications"
    const val NOTIFY_NEARBY_PASS_ID = 2
    const val NOTIFY_NEARBY_PASS_TITLE = "Something is near you!"
    const val NOTIFY_NEARBY_PASS_TEXT = "Open application to find out more!"

    val VIBRATION_PATTERN_TIMINGS = longArrayOf(0, 300, 50, 200)
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

object TotemDialogConstants {
    const val PLACING_TIME = "Placing time:"
    const val LAST_VISIT = "Last visit:"
    const val DATE_TIME_FORMAT = "dd/MM/yy HH:mm"
}

object LeaderboardItemsConstants {
    const val USERNAME = "Username:"
    const val POINTS = "Points:"
}

object CustomPinDialogConstants {
    const val NOTE_TEXT = "Note text"
    const val NOTE_TEXT_LENGTH = 100
}

object ConfirmationDialogTextConstants {
    const val LEAVE_SQUAD =
        "If you leave squad you won't be able to rejoin unless someone invites you!"
}

object UserSettingsConstants {
    const val DATA_STORE_NAME = "USER_SETTINGS"

    const val RUN_IN_BACKGROUND_PREFERENCES = "RUN_IN_BACKGROUND"
    const val SHOW_NOTIFICATIONS_PREFERENCES = "SHOW_NOTIFICATIONS"
    const val SHOW_MY_PHONE_NUMBER_PREFERENCES = "SHOW_MY_PHONE_NUMBER"
    const val CALL_PRIVACY_LEVEL_PREFERENCES = "CALL_PRIVACY_LEVEL"
    const val SMS_PRIVACY_LEVEL_PREFERENCES = "SMS_PRIVACY_LEVEL"
}