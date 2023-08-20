package com.aeon.exchangeratesapp.data

data class ExchangeRatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
) {

    companion object {
        fun stub() =
            ExchangeRatesResponse(
                true,
                System.currentTimeMillis(),
                "EUR",
                "2023-08-18",
                mapOf(
                    "RUB" to 100.0,
                    "USD" to 1.15,
                    "AMD" to 0.05,
                    "TRY" to 0.2,
                )
            )
    }
}