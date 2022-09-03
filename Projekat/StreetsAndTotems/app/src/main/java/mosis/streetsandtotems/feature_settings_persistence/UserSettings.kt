package mosis.streetsandtotems.feature_settings_persistence

data class UserSettings(
    val runInBackground: Boolean,
    val showNotifications: Boolean,
    val showMyPhoneNumber: Boolean,
    val callPrivacyLevel: PrivacySettings,
    val smsPrivacyLevel: PrivacySettings,
)

enum class PrivacySettings {
    NO_ONE,
    ONLY_SQUAD_MEMBERS,
    EVERYONE,
}