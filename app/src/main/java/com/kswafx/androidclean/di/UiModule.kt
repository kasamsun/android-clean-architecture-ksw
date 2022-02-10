package com.kswafx.androidclean.di

import android.media.AudioManager
import android.media.ToneGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UiModule {

    @Provides
    fun provideToneGenerator() : ToneGenerator {
        return ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
    }

}