package com.naulian.modify

@Deprecated(
    message = "Replace with com.naulian.anhance.paragraph",
    replaceWith = ReplaceWith(
        expression = "paragraph",
        imports = ["com.naulian.anhance.paragraph"]
    )
)
fun String.asParagraph(): String {
    return trimIndent().replace("\n", " ")
}