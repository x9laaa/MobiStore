package cl.bootcamp.mobistore.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.model.ProductDao

@Database(entities = [Product::class], version = 1)
abstract class DbDataSource: RoomDatabase() {
    abstract fun productDao(): ProductDao

}