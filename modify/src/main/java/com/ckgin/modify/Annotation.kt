package com.ckgin.modify

import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.TYPEALIAS

@RequiresOptIn(
    message = "This API is experimental and is likely to change or removed in the future.",
    level = RequiresOptIn.Level.ERROR
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalModifyApi

@Target(
    CLASS,
    FUNCTION,
    PROPERTY,
    ANNOTATION_CLASS,
    CONSTRUCTOR,
    PROPERTY_SETTER,
    PROPERTY_GETTER,
    TYPEALIAS
)
annotation class DeprecatedIn(val value: String)