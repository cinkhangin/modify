package com.naulian.modify

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun String.asParagraph(): String {
    return trimIndent().replace("\n", " ")
}

@Preview
@Composable
private fun AsParagraphPreview() {
    PreviewBox {
        Text(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            text = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Suspendisse aliquam est quis laoreet tincidunt.
                Quisque tempus sapien ante, sed aliquam ligula tempor sed.
                Etiam ac sem nec diam elementum dapibus.
                Cras tincidunt ipsum non ipsum condimentum vestibulum a nec lectus.
                Vestibulum vestibulum at ipsum id sagittis.
                Aliquam tristique dui leo, sit amet tristique nibh tempor sed.
                Mauris pretium, tellus id condimentum cursus, ex turpis vehicula neque,
                volutpat placerat tellus augue et augue.
                Maecenas imperdiet mi vitae tellus vulputate laoreet.
                Mauris eget justo eu nisl vestibulum congue.
            """.asParagraph()
        )
    }
}