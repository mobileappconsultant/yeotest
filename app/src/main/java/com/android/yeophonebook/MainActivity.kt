package com.android.yeophonebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.yeophonebook.ui.ContactViewModel
import com.android.yeophonebook.ui.model.Contact
import com.android.yeophonebook.ui.theme.YeoPhonebookTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YeoPhonebookTheme {
                val viewModel by viewModels<ContactViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val contacts = viewModel.contactsFlow.collectAsState()
                    Column(modifier = Modifier.fillMaxSize()) {
                        CustomTopAppBar()
                        ContactList(contacts = contacts.value)
                    }

                }
            }
        }
    }
}

@Composable
fun CustomTopAppBar() {
    TopAppBar(
        title = { Text(text = "Contacts") },
        actions = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Download"
                )
            }
        }
    )
}

@Composable
fun ContactList(contacts: List<Contact>) {
    if (contacts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Contacts Found")
        }
    } else {
        LazyColumn {
            items(contacts) { contact ->
                ContactItem(contact)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = contact.name)
        Spacer(Modifier.height(10.dp))
        Text(text = contact.phoneNumber)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YeoPhonebookTheme {
        Greeting("Android")
    }
}