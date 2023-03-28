package com.saint.struct.di

import android.content.Context
import com.saint.struct.StructApp
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Driver @Inject constructor(val applicaiton: StructApp) {
}

