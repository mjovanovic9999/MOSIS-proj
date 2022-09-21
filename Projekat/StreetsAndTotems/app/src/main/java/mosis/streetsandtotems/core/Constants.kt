package mosis.streetsandtotems.core

import mosis.streetsandtotems.feature_map.domain.model.SquadData
import kotlin.math.PI


object FireStoreConstants {
    const val PROFILE_DATA_COLLECTION = "profile_data"
    const val RESOURCES_COLLECTION = "resources"
    const val TOTEMS_COLLECTION = "totems"
    const val CUSTOM_PINS_COLLECTION = "custom_pins"
    const val HOMES_COLLECTION = "homes"
    const val USER_INVENTORY_COLLECTION = "user_inventory"
    const val MARKET_COLLECTION = "market"
    const val LEADERBOARD_COLLECTION = "leaderboard"
    const val MARKET_DOCUMENT_ID = "market_document_id"
    const val KICK_VOTE_COLLECTION = "kick_vote"
    const val SQUADS_COLLECTION = "squads"
    const val SQUAD_INVITES_COLLECTION = "squad_invites"

    const val ID_FIELD = "__name__"
    const val IMAGE_URI_FIELD = "image_uri"
    const val FIRST_NAME_FIELD = "first_name"
    const val LAST_NAME_FIELD = "last_name"
    const val USER_NAME_FIELD = "user_name"
    const val PHONE_NUMBER_FIELD = "phone_number"
    const val EMAIL_FIELD = "email"
    const val SQUAD_ID_FIELD = "squad_id"
    const val IS_ONLINE_FIELD = "is_online"

    const val EASY_RIDDLES_COLLECTION = "easy_riddles"
    const val MEDIUM_RIDDLES_COLLECTION = "medium_riddles"
    const val HARD_RIDDLES_COLLECTION = "hard_riddles"
    const val ITEM_COUNT = "item_count"
    const val RIDDLE_COUNT_VALUE = "value"
    const val ORDER_NUMBER = "order_number"

    const val FIELD_USERS = "users"
    const val FIELD_SQUAD_ID = "squad_id"
    const val FIELD_USER_ID = "user_id"
    const val FIELD_INVITER_ID = "inviter_id"
    const val FIELD_INVITEE_ID = "invitee_id"
    const val FIELD_INVENTORY = "inventory"
    const val L = "l"
}

object FirebaseErrorCodesConstants {
    const val INVALID_CUSTOM_TOKEN = "ERROR_INVALID_CUSTOM_TOKEN"
    const val CUSTOM_TOKEN_MISMATCH = "ERROR_CUSTOM_TOKEN_MISMATCH"
    const val INVALID_CREDENTIAL = "ERROR_INVALID_CREDENTIAL"
    const val INVALID_EMAIL = "ERROR_INVALID_EMAIL"
    const val WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
    const val USER_MISMATCH = "ERROR_USER_MISMATCH"
    const val REQUIRES_RECENT_LOGIN = "ERROR_REQUIRES_RECENT_LOGIN"
    const val ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL =
        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL"
    const val EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
    const val CREDENTIAL_ALREADY_IN_USE = "ERROR_CREDENTIAL_ALREADY_IN_USE"
    const val USER_DISABLED = "ERROR_USER_DISABLED"
    const val USER_TOKEN_EXPIRED = "ERROR_USER_TOKEN_EXPIRED"
    const val USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val INVALID_USER_TOKEN = "ERROR_INVALID_USER_TOKEN"
    const val OPERATION_NOT_ALLOWED = "ERROR_OPERATION_NOT_ALLOWED"
    const val WEAK_PASSWORD = "ERROR_WEAK_PASSWORD"
}


object FirebaseAuthConstants {
    val AUTH_ERRORS = mapOf(
        FirebaseErrorCodesConstants.INVALID_CUSTOM_TOKEN to "",
        FirebaseErrorCodesConstants.CUSTOM_TOKEN_MISMATCH to "",
        FirebaseErrorCodesConstants.INVALID_CREDENTIAL to "",
        FirebaseErrorCodesConstants.INVALID_EMAIL to "Invalid email address!",
        FirebaseErrorCodesConstants.WRONG_PASSWORD to "Wrong password!",
        FirebaseErrorCodesConstants.USER_MISMATCH to "",
        FirebaseErrorCodesConstants.REQUIRES_RECENT_LOGIN to "",
        FirebaseErrorCodesConstants.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL to "",
        FirebaseErrorCodesConstants.EMAIL_ALREADY_IN_USE to "Account with provided email already exists!",
        FirebaseErrorCodesConstants.CREDENTIAL_ALREADY_IN_USE to "",
        FirebaseErrorCodesConstants.USER_DISABLED to "Account banned!",
        FirebaseErrorCodesConstants.USER_TOKEN_EXPIRED to "",
        FirebaseErrorCodesConstants.USER_NOT_FOUND to "Wrong email!",
        FirebaseErrorCodesConstants.INVALID_USER_TOKEN to "",
        FirebaseErrorCodesConstants.OPERATION_NOT_ALLOWED to "",
        FirebaseErrorCodesConstants.WEAK_PASSWORD to "Provided password is weak!"
    )
}

object DINameConstants {
    const val SIGN_IN_REQUEST = "SignInRequest"
    const val SIGN_UP_REQUEST = "SignUpRequest"
}

object ConversionConstants {
    const val BASE64_IMAGE_PREFIX = "data:image/png;base64,"
}

object ButtonConstants {
    const val RECOVERY_EMAIL = "Send recovery email"
    const val RESEND_EMAIL = "Send new verification email"
    const val GO_TO_SIGN_IN = "Go to sign in screen"
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
    const val EDIT = "Edit"
    const val REMOVE = "Remove"
    const val INVEST = "Invest"
    const val HARVEST = "Harvest"
    const val BUY = "Buy"
    const val TAKE = "Take"
    const val PUT = "PUT"
    const val APPLY = "Apply"
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
    const val SUBMIT_ANSWER = "Submit answer"
    const val CLAIM_TOTEM = "Claim totem"
    const val PICK_UP_TOTEM = "Pick up"
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
    const val IMAGE_PATH = "imagePath"
    const val USER_NAME = "userName"
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
    const val MY_PIN_Z_INDEX = 0.5f

    const val MARKET = "MARKET"
    const val HOME = "HOME"
    const val HOME_DISCOVERY = "HOME_DISCOVERY"
    const val RESOURCES = "RESOURCES"
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
    const val MAXIMUM_IGNORE_ACCURACY_METERS = 2000f
    const val MAXIMUM_TRADE_DISTANCE_IN_METERS = 20f
    const val PREFERRED_INTERVAL = 4000L
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
    const val NO_USER_ACCOUNT_WITH_THIS_EMAIL = "Account with provided email doesn't exist!"
    const val RECOVERY_EMAIL_SENT = "Recovery email sent!"
    const val PASSWORD_RESET = "Enter email address for sending recovery email!"
    const val ALREADY_LOGGED_IN = "This account is already online on another device!"
    const val USERNAME_TAKEN = "Username is taken!"
    const val EMAIL_NOT_VERIFIED = "Unverified email!"
    const val EMAIL_TIMEOUT =
        "Email sent recently! You have to wait before you can send another one!"
    const val EMAIL_SENT = "Email sent!"
    const val EMAIL_VERIFICATION = "Verification email sent! Please verify your email to sign in!"
    const val USER_NAME_REQUIRED = "Username is required!"
    const val IMAGE_REQUIRED = "Image is required!"
    const val SIGN_OUT_ERROR = "An error occurred while signing you out!"
    const val GOOGLE_SIGN_IN_FAILED = "Google sign in failed! Please try again or contact support!"
    const val TOO_MANY_LOGIN_ATTEMPTS = "Too many login attempts! Account temporarily disabled!"
    const val DEFAULT_ERROR_MESSAGE = "Unknown error occurred! Please try again or contact support!"
    const val DEFAULT_SUCCESS_MESSAGE = "Success!"
    const val SIGN_UP_ERROR = "An error occurred while signing up! Please try again!"
    const val SIGNED_UP = "Sign up successful!"
    const val SIGNED_OUT = "Sign out successful!"
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
    const val CORRECT_ANSWER = "Your answer is correct!"
    const val INCORRECT_ANSWER = "Your answer is incorrect!"
    const val SQUAD_INVITE = "Squad invite"
    const val SQUAD_INVITE_QUESTION = " invited you to join squad."
}

object TitleConstants {
    const val PASSWORD_RESET = "Reset password"
    const val EMAIL_VERIFICATION = "Email verification"
    const val SIGN_UP = "Sign up"
    const val PROFILE = "My profile"
    const val EDIT_PROFILE = "Edit profile"
    const val EDIT_PASSWORD = "Edit password"
    const val BACKPACK = "Backpack"
    const val LEADERBOARD = "Leaderboard"
    const val MORE_INFORMATION = "More information"
    const val CUSTOM_NEW_PIN_DIALOG_SQUAD = "Place note pin for your squad"
    const val CUSTOM_NEW_PIN_DIALOG_SOLO = "Place note pin for yourself"
    const val CUSTOM_PIN_DIALOG_SQUAD_PLACED_BY = "Squad's pin placed by: "
    const val CUSTOM_PIN_DIALOG_SOLO_PLACED_BY = "My pin"
    const val LEAVE_SQUAD = "Are you sure you want to leave your squad?"
    const val ITEMS_LEFT = " left"
    const val BACKPACK_EMPTY_SPACES_LEFT = " backpack space"
    const val BACKPACK_EMPTY_SPACE_AREA = "Backpack space"
    const val PRICE = "Price"
    const val BACKPACK_INVENTORY = "Backpack inventory"
    const val HOME_INVENTORY = "Home inventory"
    const val VALUE_POINTS = "Value points: "
    const val SOLVE_ME = "Solve me"
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
    const val USER_NAME = "Username"
    const val ANSWER = "Answer"
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
}

object TotemItemsConstants {
    const val TOTEM_ID = "Totem id:"
    const val PLACED_BY = "Placed by:"
}

object TotemDialogConstants {
    const val PLACING_TIME = "Placing time:"
    const val LAST_VISIT = "Last visit:"
    const val DATE_TIME_FORMAT = "dd/MM/yy HH:mm"
    const val TOTEM_STRENGTH = "Totem strength: "
    const val POINTS_NEEDED_FOR_NEXT_LEVEL = "Next level for "
    const val POINTS = " points"
}

object LeaderboardItemsConstants {
    const val USERNAME = "Username:"
    const val POINTS = "Points:"
}

object CustomPinDialogConstants {
    const val PIN_TEXT = "Pin text"
    const val PIN_TEXT_LENGTH = 100
}

object ConfirmationDialogTextConstants {
    const val LEAVE_SQUAD =
        "If you leave squad you won't be able to rejoin unless someone invites you!"
}

object PreferencesDataStoreConstants {
    const val DATA_STORE_NAME = "USER_SETTINGS"

    const val RUN_IN_BACKGROUND_PREFERENCES = "RUN_IN_BACKGROUND"
    const val SHOW_NOTIFICATIONS_PREFERENCES = "SHOW_NOTIFICATIONS"
    const val CALL_PRIVACY_LEVEL_PREFERENCES = "CALL_PRIVACY_LEVEL"
    const val SMS_PRIVACY_LEVEL_PREFERENCES = "SMS_PRIVACY_LEVEL"
    const val AUTH_PROVIDER_PREFERENCES = "AUTH_PROVIDER"

    const val USER_ID_PREFERENCES = "USER_DATA"
    const val SQUAD_ID_PREFERENCES = "SQUAD_ID"
}

object BackpackConstants {
    const val DEFAULT_SIZE = 20
    const val DEFAULT_TOTEM_COUNT = 1
}

object PointsConversion {
    const val EMERALD = 40
    const val STONE = 30
    const val BRICK = 20
    const val WOOD = 10

    const val LOW = 50
    const val MEDIUM = 100
    const val HIGH = 200

    const val TOTEM_BONUS_POINTS_INCORRECT_ANSWER = 100

    const val HOURS_TO_POINTS_CONVERSION = 1

    const val SQUAD_MEMBERS_POINTS_COEFFICIENT = 0.5

    const val MAX_SQUAD_MEMBERS_COUNT = 5
}

object ProtectionLevelConstants {
    const val UNPROTECTED = "Unprotected"
    const val LOW = "Low"
    const val MEDIUM = "Medium"
    const val HIGH = "High"

}

object FirebaseStorageConstants {
    const val IMAGES = "profile_images"
}