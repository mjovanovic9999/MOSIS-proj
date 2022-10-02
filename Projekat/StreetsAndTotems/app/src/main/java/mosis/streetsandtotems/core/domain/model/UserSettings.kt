package mosis.streetsandtotems.core.domain.model

data class UserSettings(
    val runInBackground: Boolean,
    val showNotifications: Boolean,
    val callPrivacyLevel: PrivacySettings,
    val smsPrivacyLevel: PrivacySettings,
)

enum class PrivacySettings {
    NoOne,
    OnlySquadMembers,
    Everyone,
}

