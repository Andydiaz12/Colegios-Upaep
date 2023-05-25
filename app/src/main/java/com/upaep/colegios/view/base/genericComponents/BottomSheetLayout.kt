package com.upaep.colegios.view.base.genericComponents

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(SheetContent: @Composable () -> Unit, BaseScreenContent: @Composable () -> Unit) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            SheetContent()
        }
    ) {
        BaseScreenContent()
    }
}