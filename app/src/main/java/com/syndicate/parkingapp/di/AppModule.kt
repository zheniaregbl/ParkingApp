package com.syndicate.parkingapp.di

import android.content.Context
import android.content.SharedPreferences
import com.syndicate.parkingapp.core.AppConstants
import com.syndicate.parkingapp.data.remote.ParkingApi
import com.syndicate.parkingapp.data.repository.ParkingRepositoryImpl
import com.syndicate.parkingapp.domain.repository.ParkingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideParkingApi(): ParkingApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ParkingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideParkingRepository(parkingApi: ParkingApi): ParkingRepository {
        return ParkingRepositoryImpl(parkingApi)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_configuration", Context.MODE_PRIVATE)
    }
}