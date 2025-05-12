package com.example.efmobile3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.efmobile3.model.Cours
import com.example.efmobile3.viewmodel.CoursViewModel

@Composable
fun CoursScreen(viewModel: CoursViewModel) {
    var nom by remember { mutableStateOf("") }
    var statut by remember { mutableStateOf("In Progress") }
    var priorite by remember { mutableStateOf("Medium") }
    var isEditing by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf<Cours?>(null) }
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        if (error.isNotBlank()) {
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            value = nom,
            onValueChange = { nom = it },
            label = { Text("Course name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        DropdownSelector(
            label = "Status",
            options = listOf("In Progress", "Completed", "Cancelled"),
            selectedOption = statut
        ) { newStatus ->
            statut = newStatus
        }

        Spacer(Modifier.height(8.dp))

        DropdownSelector(
            label = "Priority",
            options = listOf("High", "Medium", "Low"),
            selectedOption = priorite
        ) { newPriority ->
            priorite = newPriority
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (nom.isBlank()) {
                    error = "Naame is empy"
                } else {
                    error = ""
                    if (isEditing && selected != null) {
                        viewModel.updateCours(
                            selected!!.copy(nom = nom, statut = statut, priorite = priorite)
                        )
                        isEditing = false
                        selected = null
                    } else {
                        viewModel.addCours(nom, statut, priorite)
                    }
                    nom = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update Course" else "Add Course")
        }

        Spacer(Modifier.height(16.dp))

        Text("Courses List", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(viewModel.coursList) { cours ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Course: ${cours.nom}", style = MaterialTheme.typography.bodyLarge)
                        Text("Status: ${cours.statut}", style = MaterialTheme.typography.bodySmall)
                        Text("Priority: ${cours.priorite}", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                        Row {
                            TextButton(onClick = {
                                nom = cours.nom
                                statut = cours.statut
                                priorite = cours.priorite
                                selected = cours
                                isEditing = true
                            }) {
                                Text("Edit")
                            }
                            TextButton(onClick = { viewModel.deleteCours(cours) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}