package com.example.efmobile2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.efmobile2.model.Formation
import com.example.efmobile2.viewmodel.FormationViewModel
import java.util.UUID

@Composable
fun MainScreen(viewModel: FormationViewModel) {
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedFormationId by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        //Dynamic Top Text
        Text(
            if (selectedFormationId == null) "Add Formation" else "Edit Formation",
            style = MaterialTheme.typography.titleLarge
        )
//first Input
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
//second input
        TextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
//third input
        TextField(
            value = priority,
            onValueChange = { priority = it },
            label = { Text("Priority") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
//Error message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
//Add / Update button
        Button(
            onClick = {
                if (name.isBlank() || status.isBlank() || priority.isBlank()) {
                    errorMessage = "Please fill all fields!"
                } else {
                    if (selectedFormationId == null) {
                        //Add new formation
                        viewModel.addFormation(
                            Formation(
                                id = UUID.randomUUID().toString(),
                                name = name,
                                status = status,
                                priority = priority
                            )
                        )
                    } else {
                        //Update existing formation
                        viewModel.updateFormation(
                            Formation(
                                id = selectedFormationId!!,
                                name = name,
                                status = status,
                                priority = priority
                            )
                        )
                    }
                    //reset all inputs
                    name = ""
                    status = ""
                    priority = ""
                    selectedFormationId = null
                    errorMessage = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(if (selectedFormationId == null) "Add Formation" else "Update Formation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Formations", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(viewModel.formations) { formation ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)) + expandVertically(),
                    exit = fadeOut(animationSpec = tween(durationMillis = 1000)) + shrinkVertically()
                ) {
                    FormationItem(
                        formation,
                        onEdit = {
                            selectedFormationId = formation.id
                            name = formation.name
                            status = formation.status
                            priority = formation.priority
                        },
                        onDelete = {
                            viewModel.deleteFormation(formation.id)
                            if (selectedFormationId == formation.id) {
                                name = ""
                                status = ""
                                priority = ""
                                selectedFormationId = null
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FormationItem(formation: Formation, onDelete: () -> Unit, onEdit: ()-> Unit) {
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)) + expandVertically(),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000)) + shrinkVertically()
    ) {
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).animateContentSize().clickable { onEdit() },
            elevation = CardDefaults.cardElevation(4.dp)
        ){
            Column(modifier = Modifier.padding(16.dp)){
                Text(formation.name)
                Text("Status: ${formation.status}")
                Text("Priority: ${formation.priority}")
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    TextButton(onClick = onEdit) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onDelete) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}