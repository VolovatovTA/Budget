package ru.budget.android.api

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.budget.android.api.data.mapper.CategoryDataMapper
import ru.budget.android.api.data.mapper.TransactionsDataMapper
import ru.budget.android.api.data.mapper.WalletsDataMapper
import ru.budget.android.api.data.source.network.entity.category.CategoryResponse
import ru.budget.android.api.data.source.network.entity.me.MeResponse
import ru.budget.android.api.data.source.network.entity.transactions.TransactionResponse
import ru.budget.android.api.data.source.network.entity.wallet.WalletItemResponse
import ru.budget.android.api.data.source.network.entity.wallet.WalletListResponse
import ru.budget.android.api.data.source.network.pathMe
import ru.budget.android.api.data.source.network.pathToTransactions
import ru.budget.android.api.data.source.network.pathToWallet
import ru.budget.android.api.data.source.network.pathWallet
import ru.budget.android.api.data.source.network.postSignInRoute
import ru.budget.android.api.data.source.network.postSignUpRoute
import ru.bysoft.android.budget.common.token.entity.AuthSuccessResponse
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.common.util.pointJson
import ru.bysoft.android.budget.common.util.restore
import java.io.File
import java.util.Locale

/**
 * Моки читают ответы из assets по тем же путям, что и настоящий бэкенд.
 * Тест проверяет, что каждый файл существует и разбирается в свой класс ответа.
 */
class MockAssetsTest {

    private fun asset(path: String) = File("src/main/assets/$path").readText()

    @Test
    fun authResponsesParse() {
        listOf(postSignInRoute, postSignUpRoute).forEach { route ->
            val response = asset(route + pointJson).restore<AuthSuccessResponse>()
            assertTrue(!response.accessToken.isNullOrEmpty())
        }
    }

    @Test
    fun meResponseParses() {
        asset(pathMe + pointJson).restore<MeResponse>()
    }

    @Test
    fun walletsParseAndMap() {
        val wallets = asset(pathWallet + pointJson).restore<WalletListResponse>()
        assertTrue(WalletsDataMapper().mapToData(wallets).isNotEmpty())
        asset("$pathToWallet/wallet$pointJson").restore<WalletItemResponse>()
    }

    @Test
    fun categoriesParseAndMap() {
        CategoryTypeEnum.entries.forEach { type ->
            val categories = asset("$pathToWallet/${type.pathToBack}$pointJson").restore<CategoryResponse>()
            assertTrue(CategoryDataMapper().mapToData(categories).listCategoryData.isNotEmpty())
        }
    }

    @Test
    fun transactionsParseAndMap() {
        val transactions = asset(pathToTransactions + pointJson).restore<TransactionResponse>()
        val mapped = TransactionsDataMapper(Locale.US).mapToData(transactions)
        assertEquals(transactions.data?.size, mapped.listTransactions.size)
    }
}
