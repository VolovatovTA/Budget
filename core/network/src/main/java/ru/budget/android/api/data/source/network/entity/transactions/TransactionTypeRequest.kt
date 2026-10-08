package ru.budget.android.api.data.source.network.entity.transactions

enum class TransferTypeEnum(val nameToBack: String?) {
    WITH_TRANSFER(null),
    WITHOUT_TRANSFER("WITHOUT"),
    ONLY_TRANSFER("ONLY")
}