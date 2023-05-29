package com.saint.inject

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * author: DragonForest
 * time: 2019/12/24
 */
public class AsmPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        println "==================="
        println "I am com.saint.inject.AsmPlugin"
        println "==================="

        // 注册transform
        registerTransform(project);
    }

    private void registerTransform(Project project) {
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new AsmTransform(project));
    }
}