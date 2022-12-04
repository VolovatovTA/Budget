package ru.bysoft.budget.features.bottom_navigation.home.data.me.mapper

import ru.bysoft.budget.features.bottom_navigation.home.data.me.entity.MeData
import ru.bysoft.budget.home.data.me.network.entity.MeResponse

fun MeResponse.mapToData() = MeData(
    this.name
)