package com.example.jonathan.testrestfulget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * View of MVVM
 */

@Composable
fun UserItemView(user: User) {
    Column (modifier = Modifier.padding(10.dp)) {
        Text(
            text = user.name
        )

        Text(
            text = user.photo
        )
    }
}

@Composable
fun UserListView(viewModel: UserViewModel) {
    Column {
        viewModel.users.forEach { user ->
            UserItemView(user)
        }
    }
}
