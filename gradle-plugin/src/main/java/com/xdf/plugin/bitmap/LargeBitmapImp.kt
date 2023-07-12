package com.xdf.plugin.bitmap

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


class LargeBitmapImp : Plugin<Project> {

    override fun apply(project: Project) {
        val appExtension: AppExtension = project.extensions.getByType(AppExtension::class.java)
        appExtension.registerTransform(LargeBitmapTransform())
    }


}