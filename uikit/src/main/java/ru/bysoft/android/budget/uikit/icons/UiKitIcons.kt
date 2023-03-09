package ru.bysoft.android.budget.uikit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import ru.bysoft.android.budget.uikit.icons.another.Qrcode
import ru.bysoft.android.budget.uikit.icons.another.Wallet
import ru.bysoft.android.budget.uikit.icons.another.logo
import ru.bysoft.android.budget.uikit.icons.pack.*

object UiKitIcons {
    private val icons = mapOf(
        ArrowDown.name to ArrowDown,
        ArrowLeft.name to ArrowLeft,
        ArrowRight.name to ArrowRight,
        ArrowUp.name to ArrowUp,
        Beach.name to Beach,
        Calendar.name to Calendar,
        Card.name to Card,
        CheckMark.name to CheckMark,
        Clear.name to Clear,
        Copy.name to Copy,
        Delete.name to Delete,
        Edit.name to Edit,
        Education.name to Education,
        Filters.name to Filters,
        Food.name to Food,
        Health.name to Health,
        Heart.name to Heart,
        House.name to House,
        Internet.name to Internet,
        Menu.name to Menu,
        Minus.name to Minus,
        Multiply.name to Multiply,
        Oil.name to Oil,
        Person.name to Person,
        Photo.name to Photo,
        Plus.name to Plus,
        Recycle.name to Recycle,
        Rest.name to Rest,
        Search.name to Search,
        Shield.name to Shield,
        Sport.name to Sport,
        Statistic.name to Statistic,
        Tele.name to Tele,
        Qrcode.name to Qrcode,
        Wallet.name to Wallet,
        logo.name to logo,
    )
    fun getByName(name: String?): ImageVector? = icons[name]

    fun getCategories() = categories.toList()

    private val categories = mapOf(
        Beach.name to Beach,
        Calendar.name to Calendar,
        Card.name to Card,
        Education.name to Education,
        Food.name to Food,
        Health.name to Health,
        Heart.name to Heart,
        House.name to House,
        Internet.name to Internet,
        Oil.name to Oil,
        Person.name to Person,
        Photo.name to Photo,
        Recycle.name to Recycle,
        Rest.name to Rest,
        Search.name to Search,
        Shield.name to Shield,
        Sport.name to Sport,
        Statistic.name to Statistic,
        Tele.name to Tele,
    )


}