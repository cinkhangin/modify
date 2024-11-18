package com.naulian.modify


data class SelectableItem<T>(
    val item: T,
    val selected: Boolean = false
)

fun <T> List<SelectableItem<T>>.selectBy(
    selector: (T) -> Boolean,
) = map { it.copy(selected = selector(it.item)) }

fun <T> List<SelectableItem<T>>.selectAll(value: Boolean) = map { it.copy(selected = value) }

fun <T> List<SelectableItem<T>>.multiSelectBy(
    selector: (T) -> Boolean,
) = map {
    val selected = selector(it.item)
    it.copy(selected = it.selected xor selected)
}