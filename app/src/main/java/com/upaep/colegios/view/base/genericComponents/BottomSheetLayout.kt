package com.upaep.colegios.view.base.genericComponents

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    BaseScreenContent: @Composable () -> Unit,
    childDataViewModel: ChildDataViewModel = hiltViewModel(),
    SheetContent: @Composable () -> Unit = { },
    customContent: Boolean = false
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            if (customContent) SheetContent()
            else DefaultSheetContent(childDataViewModel = childDataViewModel, scope = scope, state = state)
        }
    ) {
        BaseScreenContent()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DefaultSheetContent(childDataViewModel: ChildDataViewModel, scope: CoroutineScope, state: ModalBottomSheetState) {
    ChildSelectorModal(onClick = { student, color ->
        childDataViewModel.changeSelectedStudent(student, color)
        scope.launch { state.hide() }
    })
}