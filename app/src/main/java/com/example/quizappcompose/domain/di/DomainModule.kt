package com.example.quizappcompose.domain.di

import com.example.quizappcompose.domain.repository.QuizRepository
import com.example.quizappcompose.domain.usecases.GetQuizzesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetQuizzesUseCase(quizRepository: QuizRepository): GetQuizzesUseCases {
        return GetQuizzesUseCases(quizRepository)
    }
}