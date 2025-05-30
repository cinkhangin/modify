package com.naulian.modify.field

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink

fun AnnotatedString.Builder.appendClickable(
    text: String,
    color: Color = Color.Blue,
    fontWeight: FontWeight = FontWeight.Bold,
    spanStyle: SpanStyle = SpanStyle(
        color = color,
        fontWeight = fontWeight
    ),
    onClick: (String) -> Unit
) {
    withLink(
        link = LinkAnnotation.Clickable(
            tag = text,
            styles = TextLinkStyles(style = spanStyle),
            linkInteractionListener = { onClick(text) }
        ),
        block = { append(text) }
    )
}

fun AnnotatedString.Builder.appendLink(
    text: String,
    url : String,
    color: Color = Color.Blue,
    fontWeight: FontWeight = FontWeight.Bold,
    spanStyle: SpanStyle = SpanStyle(
        color = color,
        fontWeight = fontWeight
    ),
    onClick: (String) -> Unit
) {
    withLink(
        link = LinkAnnotation.Url(
            url = url,
            styles = TextLinkStyles(style = spanStyle),
            linkInteractionListener = { onClick(text) }
        ),
        block = { append(text) }
    )
}