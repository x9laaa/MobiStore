package cl.bootcamp.mobistore.di

import cl.bootcamp.mobistore.repository.ProductRepository
import cl.bootcamp.mobistore.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun productRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository


}