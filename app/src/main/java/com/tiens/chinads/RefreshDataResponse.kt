package com.tiens.chinads

data class RefreshDataResponse (
    val total: Int,
    val hasNext: Boolean,
    val items: List<ConferenceBean>
)

data class ConferenceBean (
    val conferenceId: Long,
    val coName: String
)