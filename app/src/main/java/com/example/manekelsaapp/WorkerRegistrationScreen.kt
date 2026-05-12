package com.example.manekelsaapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- ಹೈ ಕಾಂಟ್ರಾಸ್ಟ್ ಕಲರ್ ಪ್ಯಾಲೆಟ್ ---
val RegistrationPrimary = Color(0xFF0D1B2A)
val RegistrationAccent = Color(0xFFFFC107)
val RegistrationLabel = Color(0xFF1B263B)
val RegistrationBg = Color(0xFFE0E1DD)
val CardBg = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerRegistrationScreen(
    viewModel: ManeKelsaViewModel,
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var selectedSkill by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf("") }
    var isLocExpanded by remember { mutableStateOf(false) }
    var selectedAvailability by remember { mutableStateOf("") }
    var isAvailExpanded by remember { mutableStateOf(false) }

    // --- ಕನ್ನಡದಲ್ಲಿ ವೃತ್ತಿಗಳ ಪಟ್ಟಿ ---
    val skillMap = mapOf(
        "ಅಡುಗೆ ಕೆಲಸ" to "Cook",
        "ವಾಹನ ಚಾಲಕ" to "Driver",
        "ವಿದ್ಯುತ್ ಕೆಲಸ (ಎಲೆಕ್ಟ್ರಿಷಿಯನ್)" to "Electrician",
        "ನಳದ ಕೆಲಸ (ಪ್ಲಂಬರ್)" to "Plumber",
        "ಬಣ್ಣದ ಕೆಲಸ" to "Painter",
        "ತೋಟಗಾರಿಕೆ" to "Gardener",
        "ಮನೆಗೆಲಸ" to "Maid",
        "ಬಡಗಿ" to "Carpenter",
        "ಕಟ್ಟಡ ಕಾರ್ಮಿಕ" to "Mason",
        "ವೆಲ್ಡಿಂಗ್ ಕೆಲಸ" to "Welder",
        "ಟೈಲರಿಂಗ್" to "Tailor",
        "ಸೆಕ್ಯೂರಿಟಿ ಗಾರ್ಡ್" to "Security Guard",
        "ಡೆಲಿವರಿ ಬಾಯ್" to "Delivery Boy",
        "ಕ್ಲೀನಿಂಗ್ ಸೇವೆ" to "Cleaner",
        "ಎಸಿ ಮೆಕ್ಯಾನಿಕ್" to "AC Mechanic",
        "ಟೈಲ್ಸ್ ಕೆಲಸ" to "Tiles Worker",
        "ಫೋಟೋಗ್ರಾಫರ್" to "Photographer",
        "ಬ್ಯೂಟಿಷಿಯನ್" to "Beautician",
        "ಕಂಪ್ಯೂಟರ್ ರಿಪೇರಿ" to "Computer Repair",
        "ಪ್ಯಾಕರ್ಸ್ ಮತ್ತು ಮೂವರ್ಸ್" to "Packers and Movers",
        "ಪೆಸ್ಟ್ ಕಂಟ್ರೋಲ್" to "Pest Control"
    )

    // --- ಕನ್ನಡದಲ್ಲಿ ಪ್ರದೇಶಗಳ ಪಟ್ಟಿ ---
    val locationList = listOf(
        "ಪೀಣ್ಯ", "ದಾಸರಹಳ್ಳಿ", "ಹೊಸಕೋಟೆ", "ಕೋರಮಂಗಲ", "ವೈಟ್‌ಫೀಲ್ಡ್", "ಜಯನಗರ",
        "ಯಲಹಂಕ", "ಎಲೆಕ್ಟ್ರಾನಿಕ್ ಸಿಟಿ", "ರಾಜಾಜಿನಗರ", "ಇಂದಿರಾನಗರ", "ಬಿಟಿಎಂ ಲೇಔಟ್",
        "ಬನಶಂಕರಿ", "ಹೆಚ್‌ಎಸ್ಆರ್ ಲೇಔಟ್", "ಮಲ್ಲೇಶ್ವರಂ", "ಯಶವಂತಪುರ", "ಹೆಬ್ಬಾಳ",
        "ವಿಜಯನಗರ", "ಕೆಂಗೇರಿ", "ಬನ್ನೇರುಘಟ್ಟ ರಸ್ತೆ", "ಆರ್.ಟಿ ನಗರ", "ಕಲ್ಯಾಣ ನಗರ"
    )

    // --- ಕನ್ನಡದಲ್ಲಿ ಸಮಯದ ಲಭ್ಯತೆ ---
    val availabilityOptions = listOf(
        "ಪೂರ್ಣ ಸಮಯ", "ಭಾಗಶಃ ಸಮಯ", "ವಾರಾಂತ್ಯ ಸೇವೆ", "ಬೆಳಿಗ್ಗೆ 9 ರಿಂದ ಸಂಜೆ 6", "ಬೆಳಿಗ್ಗೆ 10 ರಿಂದ ಸಂಜೆ 5",
        "ಬೆಳಿಗ್ಗೆ ಶಿಫ್ಟ್ (6 - 2)", "ಮಧ್ಯಾಹ್ನದ ಶಿಫ್ಟ್ (2 - 10)", "ರಾತ್ರಿ ಶಿಫ್ಟ್ (10 - 6)",
        "24 ಗಂಟೆಗಳ ಸೇವೆ", "ಭಾನುವಾರ ಮಾತ್ರ", "ವಾರದ ದಿನಗಳಲ್ಲಿ ಮಾತ್ರ", "ಕರೆ ಮಾಡಿದಾಗ ಲಭ್ಯ",
        "ಯಾವಾಗಲೂ ಲಭ್ಯ", "ಗಂಟೆಯ ಆಧಾರದ ಮೇಲೆ", "ತುರ್ತು ಸೇವೆ ಮಾತ್ರ"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ನೋಂದಣಿ ಫಾರ್ಮ್", color = Color.White, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = RegistrationPrimary)
            )
        },
        containerColor = RegistrationBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    ProfessionalTextField(value = name, onValueChange = { name = it }, label = "ಪೂರ್ಣ ಹೆಸರು")
                    ProfessionalTextField(value = phone, onValueChange = { phone = it }, label = "ಫೋನ್ ಸಂಖ್ಯೆ")

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(Modifier.weight(1f)) { ProfessionalTextField(value = age, onValueChange = { age = it }, label = "ವಯಸ್ಸು") }
                        Box(Modifier.weight(1f)) { ProfessionalTextField(value = experience, onValueChange = { experience = it }, label = "ಅನುಭವ (ವರ್ಷಗಳು)") }
                    }

                    ProfessionalTextField(value = rate, onValueChange = { rate = it }, label = "ದಿನದ ಕೂಲಿ (ರೂಪಾಯಿಗಳಲ್ಲಿ)")
                    ProfessionalTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = "ಭಾವಚಿತ್ರದ ಲಿಂಕ್ (ಲಿಂಕ್ ಮಾತ್ರ)")

                    // ಪ್ರದೇಶದ ಆಯ್ಕೆ
                    Text("ನಿಮ್ಮ ಪ್ರದೇಶವನ್ನು ಆರಿಸಿ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RegistrationLabel, modifier = Modifier.padding(top = 8.dp))
                    ExposedDropdownMenuBox(expanded = isLocExpanded, onExpandedChange = { isLocExpanded = !isLocExpanded }) {
                        OutlinedTextField(
                            value = selectedLocation,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("ಪ್ರದೇಶವನ್ನು ಆಯ್ಕೆ ಮಾಡಿ", color = Color.Gray) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = RegistrationPrimary, unfocusedBorderColor = Color.LightGray)
                        )
                        ExposedDropdownMenu(expanded = isLocExpanded, onDismissRequest = { isLocExpanded = false }) {
                            locationList.forEach { loc ->
                                DropdownMenuItem(text = { Text(loc, fontWeight = FontWeight.Medium) }, onClick = { selectedLocation = loc; isLocExpanded = false })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ಲಭ್ಯತೆಯ ಆಯ್ಕೆ
                    Text("ಕೆಲಸದ ಲಭ್ಯತೆ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RegistrationLabel)
                    ExposedDropdownMenuBox(expanded = isAvailExpanded, onExpandedChange = { isAvailExpanded = !isAvailExpanded }) {
                        OutlinedTextField(
                            value = selectedAvailability,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("ಸಮಯವನ್ನು ಆಯ್ಕೆ ಮಾಡಿ", color = Color.Gray) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isAvailExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = RegistrationPrimary, unfocusedBorderColor = Color.LightGray)
                        )
                        ExposedDropdownMenu(expanded = isAvailExpanded, onDismissRequest = { isAvailExpanded = false }) {
                            availabilityOptions.forEach { option ->
                                DropdownMenuItem(text = { Text(option, fontWeight = FontWeight.Medium) }, onClick = { selectedAvailability = option; isAvailExpanded = false })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("ನಿಮ್ಮ ಬಗ್ಗೆ ವಿವರಣೆ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RegistrationLabel)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth().height(110.dp).padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("ನಿಮ್ಮ ಕೆಲಸದ ಅನುಭವದ ಬಗ್ಗೆ ಇಲ್ಲಿ ಬರೆಯಿರಿ...", color = Color.Gray) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = RegistrationPrimary, unfocusedBorderColor = Color.LightGray)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // ವೃತ್ತಿಯ ಆಯ್ಕೆ
                    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = !isExpanded }) {
                        OutlinedTextField(
                            value = selectedSkill,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("ನಿಮ್ಮ ಕೆಲಸದ ವಿಧವನ್ನು ಆರಿಸಿ", fontWeight = FontWeight.Bold) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = RegistrationPrimary, unfocusedBorderColor = Color.LightGray)
                        )
                        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                            skillMap.keys.forEach { skill ->
                                DropdownMenuItem(text = { Text(skill, fontWeight = FontWeight.Medium) }, onClick = { selectedSkill = skill; isExpanded = false })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() && phone.isNotBlank() && selectedSkill.isNotBlank() && selectedAvailability.isNotBlank()) {
                                viewModel.registerWorker(
                                    name = name, phone = phone, age = age, experience = experience,
                                    rate = rate, description = description, imageUrl = imageUrl,
                                    skill = skillMap[selectedSkill] ?: "Worker",
                                    location = selectedLocation,
                                    availability = selectedAvailability,
                                    onSuccess = { onRegisterSuccess() }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(58.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RegistrationPrimary),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text("ನೋಂದಾಯಿಸಿ", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = RegistrationAccent)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfessionalTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RegistrationLabel)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFFAFAFA),
                focusedBorderColor = RegistrationPrimary,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}