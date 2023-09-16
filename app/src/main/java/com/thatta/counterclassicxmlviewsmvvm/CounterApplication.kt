package com.thatta.counterclassicxmlviewsmvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Application class to initialize the database, repository, use cases and be able to inject them

// Hilt annotation to inject dependencies
@HiltAndroidApp
class CounterApplication: Application(){}