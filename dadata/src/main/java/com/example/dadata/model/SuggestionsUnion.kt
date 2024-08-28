package com.example.dadata.model

import kotlinx.serialization.Serializable

@Serializable
data class SuggestionsUnion(
    val suggestions: List<Suggestion>
)