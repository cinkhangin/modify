package com.ckgin.modify

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

val LocalBackStack = compositionLocalOf<NavBackStack<NavKey>> {
    error("Navigator not present")
}

fun <T : NavKey> NavBackStack<NavKey>.navigate(route: T) {
    add(route)
}

fun NavBackStack<NavKey>.navigateBack() {
    removeLastOrNull()
}

fun <T : NavKey> NavBackStack<NavKey>.clearNavigate(screen: T) {
    clear(); navigate(screen)
}
