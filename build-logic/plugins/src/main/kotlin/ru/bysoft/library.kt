package ru.bysoft

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

fun Project.library(): LibraryExtension = extensions.getByType(LibraryExtension::class)
