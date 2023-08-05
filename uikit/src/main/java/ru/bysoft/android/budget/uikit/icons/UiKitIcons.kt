package ru.bysoft.android.budget.uikit.icons

import androidx.annotation.DrawableRes
import ru.bysoft.android.budget.common.util.CategoryTypeEnum
import ru.bysoft.android.budget.uikit.R

object UiKitIcons {
    private val icons =
        (ExpensesIcons.values().toList() +
                IncomesIcons.values().toList() +
                WalletIcons.values().toList())
            .associate { it.nameForBack to it.id }

    fun getByName(name: String?): Int? = icons[name]

    fun getCategoriesIcons(type: CategoryTypeEnum): List<IIcons> =
        when (type) {
            CategoryTypeEnum.EXPENSE -> ExpensesIcons.values().toList()
            CategoryTypeEnum.INCOME -> IncomesIcons.values().toList()
        }

    fun getWalletIcons(): List<IIcons> = WalletIcons.values().toList()


    interface IIcons {
        val nameForBack: String
        val id: Int
    }

    enum class ExpensesIcons(override val nameForBack: String, @DrawableRes override val id: Int) :
        IIcons {
        Beach("activity", R.drawable.activity),
        AlertTriangle("alert_triangle", R.drawable.alert_triangle),
        Bag("bag", R.drawable.bag),
        Calendar("calendar", R.drawable.calendar),
        Cart("cart", R.drawable.cart),
        Compass("compass", R.drawable.compass),
        Computer("computer", R.drawable.computer),
        Delivery("delivery", R.drawable.delivery),
        Diamond("diamond", R.drawable.diamond),
        Drop("drop", R.drawable.drop),
        Flame("flame", R.drawable.flame),
        Gear("gear", R.drawable.gear),
        Gift("gift", R.drawable.gift),
        Globe("globe", R.drawable.globe),
        Heart("heart", R.drawable.heart),
        Iphone("iphone", R.drawable.iphone),
        Lightning("lightning", R.drawable.lightning),
        Mail("mail", R.drawable.mail),
        MediaStrip("media_strip", R.drawable.media_strip),
        PhoneCall("phone_call", R.drawable.phone_call),
        Plane("plane", R.drawable.plane),
        ReceiptLines("receipt_lines", R.drawable.receipt_lines),
        Shield("shield", R.drawable.shield),
        Ticket("ticket", R.drawable.ticket),
        Train("train", R.drawable.train),
        Umbrella("umbrella", R.drawable.umbrella),
        Smoke("smoke", R.drawable.smoke),
        Food("food", R.drawable.food),
        Home("home", R.drawable.home_05),
        Cafe("cafe", R.drawable.cafe),
        Clothes("clothes", R.drawable.clothes),
    }

    enum class IncomesIcons(override val nameForBack: String, @DrawableRes override val id: Int) :
        IIcons {
        Banknote02("banknote_02", R.drawable.bank_note_02),
        Banknote05("banknote_05", R.drawable.bank_note_05),
        Banknote06("banknote_06", R.drawable.bank_note_06),
        Card02("card_02", R.drawable.card_02),
        CoinHand("coin_hand", R.drawable.coin_hand),
        Gift("gift", R.drawable.gift),
        Wallet("wallet", R.drawable.wallet),
        SmileyHappy("smiley_happy", R.drawable.smiley_happy),
        Discount("discount", R.drawable.discount_square),
        DiscountCircle("discount_circle", R.drawable.discount_circle),
        CurrencyCoinDollar("currency_coin_dollar", R.drawable.currency_coin_dollar),
        CurrencyCoinEuro("currency_coin_euro", R.drawable.currency_coin_euro),
        CurrencyCoinPound("currency_coin_pound", R.drawable.currency_coin_pound),
        CurrencyCoinRubel("currency_coin_rubel", R.drawable.currency_coin_rubel),
        CurrencyCoinYen("currency_coin_yen", R.drawable.currency_coin_yen),
        CurrencyCoinRupee("currency_coin_rupee", R.drawable.currency_coin_rupee),
        CurrencyCoinBitcoin("currency_coin_bitcoin", R.drawable.currency_coin_bitcoin),
    }

    enum class WalletIcons(override val nameForBack: String, @DrawableRes override val id: Int) :
        IIcons {
        Banknote05("banknote_05", R.drawable.bank_note_05),
        Wallet("wallet", R.drawable.wallet),
        Card02("card_02", R.drawable.card_02),
        DiscountCircle("discount_circle", R.drawable.discount_circle),

    }


}