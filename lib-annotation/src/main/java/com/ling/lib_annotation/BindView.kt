package com.ling.lib_annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class BindView(val value: Int)
