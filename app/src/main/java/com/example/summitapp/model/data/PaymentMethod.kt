package com.example.summitapp.model.data

sealed class PaymentMethod {
    abstract val displayName: String
    abstract val code: String
    object COD : PaymentMethod() {
        override val displayName: String = "Cash on Delivery"
        override val code: String = "COD"
    }

    object CreditCard : PaymentMethod() {
        override val displayName: String = "Credit Card"
        override val code: String = "CreditCard"
    }

    object PayPal : PaymentMethod() {
        override val displayName: String = "PayPal"
        override val code: String = "PayPal"
    }

    object InternetBanking : PaymentMethod() {
        override val displayName: String = "Internet Banking"
        override val code: String = "InternetBanking"
    }

    companion object {
        fun getAllPaymentMethods(): List<PaymentMethod> = listOf(COD, CreditCard, PayPal, InternetBanking)
    }
}
