package ru.bysoft.budget.features.create_update_wallet.data.entity

data class CreateWalletData(
    val errorType: CreateWalletErrorData? = null
)

enum class CreateWalletErrorData(val errorText: String, val slug: String) {

    INVALID_CURRENCY("Данная валюта не поддерживается нашим бэкендом. Странно что она вообще сюда попала...","invalid-currency"),
    INVALID_BALANCE("Неверный баланс. Попробуйте заменить его.","invalid-balance"),
    INVALID_NAME("Имя не должно быть пустым","invalid-name"),
    NO_UNIQ_NAME("Имя кошелька должно отличаться от существующих","wallet-name-musq-be-unique"),
}