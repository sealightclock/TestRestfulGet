package com.example.jonathan.testrestfulget.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jonathan.testrestfulget.model.User
import com.example.jonathan.testrestfulget.viewmodel.UserViewModel

/**
 * This file contains the View component of MVVM.
 */

// This displays each user.
@Composable
fun UserItemView(user: User) {
    Column (modifier = Modifier.padding(10.dp)) {
        Text(
            text = user.name
        )

        // TODO: Consider displaying an image of the file.
        Text(
            text = user.photo
        )
    }
}

// This displays a list of users:
@Composable
fun UserListView(viewModel: UserViewModel) {
    val users = viewModel.users.collectAsState()

    Column {
        users.value.forEach { user ->
            UserItemView(user)
        }
    }
}
