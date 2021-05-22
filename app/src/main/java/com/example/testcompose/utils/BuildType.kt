package com.example.testcompose.utils

import com.example.testcompose.BuildConfig


object BuildType {

    fun isRelease() : Boolean {
        return BuildConfig.BUILD_TYPE == "release"
    }

    fun isDebug() : Boolean {
        return BuildConfig.BUILD_TYPE == "debug"
    }

}