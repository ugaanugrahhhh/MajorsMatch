package capstone.product.tim.majorsmatch.di

import android.content.Context
import capstone.product.tim.majorsmatch.data.pref.UserPreference
import capstone.product.tim.majorsmatch.data.pref.dataStore
import capstone.product.tim.majorsmatch.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): capstone.product.tim.majorsmatch.data.UserRepository<Any?> {
        val pref = UserPreference.getInstance(context.dataStore)
        runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService()
        return capstone.product.tim.majorsmatch.data.UserRepository.getInstance(apiService, pref)
    }
}