package com.example.pelor.PartHome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.gemifikasi.catagory

@Composable
fun ComponentCatagory(
    setValue: (String) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(0) }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 20.dp),
        content = {
            items(
                count = catagory.size,
                itemContent = {
                    CardCatagory(
                        title = catagory[it],
                        isSelected = selectedIndex.value == it,
                        onClick = {
                            selectedIndex.value = it
                            setValue(catagory[it])
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            )
        }
    )
}

@Preview
@Composable
private fun CatagoryPrev() {
    ComponentCatagory({})
}
