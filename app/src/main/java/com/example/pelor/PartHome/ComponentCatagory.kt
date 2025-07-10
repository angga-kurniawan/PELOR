package com.example.pelor.PartHome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pelor.gemifikasi.KategoriType
import com.example.pelor.gemifikasi.catagory

@Composable
fun ComponentCatagory(
    setValue: (KategoriType) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(0) }
    val kategoriList = KategoriType.values()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        itemsIndexed(kategoriList) { index, kategoriType ->
            val label = stringResource(kategoriType.labelResId)
            CardCatagory(
                title = label,
                isSelected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index
                    setValue(kategoriType)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview
@Composable
private fun CatagoryPrev() {
    ComponentCatagory({})
}
