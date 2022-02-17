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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.yeophonebook.ui.ContactViewModel
import com.android.yeophonebook.ui.model.Contact
import com.android.yeophonebook.ui.theme.YeoPhonebookTheme
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@AndroidEntryPoint
@kotlin.OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YeoPhonebookTheme {
                val viewModel by viewModels<ContactViewModel>()
                val contactsPermissionState =
                    rememberPermissionState(android.Manifest.permission.READ_CONTACTS)
                val context = LocalContext.current

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val contacts = viewModel.contactsFlow.collectAsState()
                    Column(modifier = Modifier.fillMaxSize()) {
                        CustomTopAppBar {
                            contactsPermissionState.launchPermissionRequest()
                            if (contactsPermissionState.hasPermission) {
                                viewModel.getContactsFromDevice(context)
                                Toast.makeText(context, "Fetching contacts", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (contactsPermissionState.shouldShowRationale.not()) {
                                Toast.makeText(context, "Permission needed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        ContactList(contacts = contacts.value)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTopAppBar(onDownloadClick: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Contacts") },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = {
                    onDownloadClick()
                    showMenu = false
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Download,
                            contentDescription = "Download"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Sync Contacts")
                    }

                }
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
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = "Name : ${contact.name}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(10.dp))
        Text(text = "Phone Number: ${contact.phoneNumber}")
        Spacer(Modifier.height(4.dp))
        Text(text = "Date Updated: ${contact.dateUpdated}", color = Color.Green)
    }
}