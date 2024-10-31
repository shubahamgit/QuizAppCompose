package com.example.quizappcompose.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizappcompose.R
import com.example.quizappcompose.presentation.util.Dimens

@Preview
@Composable
fun Prev() {
    val list = listOf("Item 1", "Item 2")
    AppDropDownMenu(menuName = "Drop Down", menuList = list, text = "Item 1", onDropDownClick = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropDownMenu(
    menuName: String,
    menuList: List<String>,
    text: String,
    onDropDownClick: (String) -> Unit
){
    var isExpanded by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.MediumPadding)
    ) {
        Text(
            text = menuName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blue_grey)
        )

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true),
                value = text,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = colorResource(id = R.color.blue_grey),
                    unfocusedTextColor = colorResource(id = R.color.blue_grey),
                    unfocusedTrailingIconColor = colorResource(id = R.color.orange),
                    focusedIndicatorColor = colorResource(id = R.color.dark_slate_blue),
                    unfocusedIndicatorColor = colorResource(id = R.color.dark_slate_blue),
                    focusedContainerColor = colorResource(id = R.color.dark_slate_blue),
                    unfocusedContainerColor = colorResource(id = R.color.dark_slate_blue)
                    ),
                shape = RoundedCornerShape(15.dp)
             )

            DropdownMenu(
                modifier = Modifier
                    .background(colorResource(id = R.color.mid_night_blue)),
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                menuList.forEachIndexed { index: Int, text: String ->
                    DropdownMenuItem(
                        text = { Text(text = text, color = colorResource(id = R.color.blue_grey)) },
                        onClick = {
                            onDropDownClick(menuList[index])
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}
