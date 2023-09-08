package com.aa.base.ui.compose.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aa.base.ui.configuration.AppTheme

@Composable
fun ErrorPopUp(
    errorText: String,
    onDismiss: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null
) {
    var openDialog by remember { mutableStateOf(true) }

    AppTheme {
        if (openDialog) {

            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(36.dp),
                onDismissRequest = {
                    openDialog = false
                    onDismiss?.let { it() }
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(errorText)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onConfirm?.let { it() }
                            openDialog = false
                        }) {
                        Text("Done")
                    }
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialog() {
    AppTheme {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.\
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text("This is an error description")
            },
            confirmButton = {
                Button(
                    onClick = {

                    }) {
                    Text("Done")
                }
            }
        )

    }
}
