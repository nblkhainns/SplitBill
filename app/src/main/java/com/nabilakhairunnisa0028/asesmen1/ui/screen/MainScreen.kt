package com.nabilakhairunnisa0028.asesmen1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabilakhairunnisa0028.asesmen1.R
import com.nabilakhairunnisa0028.asesmen1.ui.theme.Asesmen1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){
    var total by rememberSaveable { mutableStateOf("") }
    var totalError by rememberSaveable { mutableStateOf(false)  }

    var jumlah by rememberSaveable { mutableStateOf("") }
    var jumlahError by rememberSaveable { mutableStateOf(false)  }

    val radioOptions = listOf(
        stringResource(R.string.tip_0),
        stringResource(R.string.tip_5),
        stringResource(R.string.tip_10)
    )
    var tip by rememberSaveable { mutableStateOf(radioOptions[0]) }

    var totalTip by rememberSaveable { mutableDoubleStateOf(0.0)}
    var perorang by rememberSaveable { mutableDoubleStateOf(0.0) }
    var totalPerorang by rememberSaveable { mutableDoubleStateOf(0.0) }

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_intro),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            value = total,
            onValueChange = { total = it },
            label = {Text(text = stringResource(R.string.total_tagihan))},
            leadingIcon = { IconPicker(totalError, "Rp") },
            supportingText = { ErrorHint(totalError) },
            isError = totalError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jumlah,
            onValueChange = { jumlah = it },
            label = {Text(text = stringResource(R.string.jumlah_orang))},
            supportingText = { ErrorHint(jumlahError) },
            isError = jumlahError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp
        )
        Text(
            text = stringResource(id = R.string.tip_title),
            style = MaterialTheme.typography.bodyLarge,
        )
        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                TipOption(
                    label = text,
                    isSelected = tip == text,
                    modifier = Modifier
                        .selectable(
                            selected = tip == text,
                            onClick = { tip = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Button(
            onClick = {
                totalError = (total == "" || total == "0")
                jumlahError = (jumlah == "" || jumlah == "0")
                if (totalError || jumlahError) return@Button

                totalTip = hitungTip(total.toDouble(), tip.replace("%","").toDouble()/100)
                perorang = hitungPerorang(total.toDouble(), jumlah.toInt())
                totalPerorang = hitungTotalPerorang(total.toDouble(), jumlah.toInt(), totalTip)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }
        if (totalPerorang != 0.0) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.tip_label) ,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Rp %.0f".format(totalTip),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.perorang_label),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Rp %.0f".format(perorang),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.hasil_label),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Rp %.0f".format(totalPerorang),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun TipOption(label: String, isSelected: Boolean, modifier: Modifier){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError){
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean){
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}
private fun hitungTip(total: Double, tip: Double): Double {
    return total * tip
}
private fun hitungPerorang(total: Double, jumlah: Int): Double {
    return total / jumlah
}
private fun hitungTotalPerorang(total: Double, jumlah: Int, totalTip: Double): Double {
    return (total + totalTip)/jumlah
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Asesmen1Theme {
        MainScreen()
    }
}
