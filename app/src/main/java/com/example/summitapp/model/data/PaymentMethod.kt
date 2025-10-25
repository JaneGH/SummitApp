package com.example.summitapp.model.data

sealed class PaymentMethod {
    abstract val displayName: String
    object COD : PaymentMethod() {
        override val displayName: String = "Cash on Delivery"
    }

    object CreditCard : PaymentMethod() {
        override val displayName: String = "Credit Card"
    }

    object PayPal : PaymentMethod() {
        override val displayName: String = "PayPal"
    }

    object InternetBanking : PaymentMethod() {
        override val displayName: String = "Internet Banking"
    }

    companion object {
        fun getAllPaymentMethods(): List<PaymentMethod> = listOf(COD, CreditCard, PayPal, InternetBanking)
    }
}
