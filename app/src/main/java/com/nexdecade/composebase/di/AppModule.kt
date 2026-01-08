package com.nexdecade.composebase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.nexdecade.composebase.network.services.GetPokemonService
import com.nexdecade.composebase.roomDb.AppDatabase
import com.nexdecade.composebase.roomDb.PokemonDao


import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideUserInfoDao(localDb: LocalDb): UserInfoDao {
//        return localDb.dao
//    }

//    @Provides
//    @Singleton
//    fun provideLocalDb(@ApplicationContext context: Context): LocalDb {
//        return LocalDb.getDatabase(context)
//    }
    
    
    @Provides
    @Singleton
    fun providePokemonService(): GetPokemonService {
        return GetPokemonService()
    }
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pokemon_db"
        ).build()
    
    @Provides
    fun providePokemonDao(db: AppDatabase): PokemonDao =
        db.pokemonDao()
}
