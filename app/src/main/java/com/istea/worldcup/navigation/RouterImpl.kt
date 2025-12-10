package com.istea.worldcup.navigation

import androidx.navigation.NavController

class RouterImpl(
    private val navController: NavController
) : Router {

    override fun openGroupDetail(groupId: String) {
        // ✅ Usar el ID real del grupo
        navController.navigate("detalle/$groupId")
    }

    override fun back() {
        navController.popBackStack()
    }
}