package com.example.benefitalculator.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.benefitalculator.domain.BenefitCalculatorRepository
import com.example.benefitalculator.domain.Product

class BenefitCalculatorRepositoryImpl(
    application: Application
): BenefitCalculatorRepository {

    private val productDao = BenefitCalculatorDatabase.getInstance(application).productDao()
    private val mapper = ProductMapper()

    override fun addProduct(product: Product) {
        productDao.addProduct(product = mapper.mapEntityToDbModel(product))
    }

    override fun deleteProduct(product: Product) {
        productDao.deleteProduct(product = mapper.mapEntityToDbModel(product))
    }

    override fun editProduct(product: Product) {
        productDao.updateProduct(product = mapper.mapEntityToDbModel(product))
    }

    override fun getProduct(productId: Int): Product {
        val dbModel = productDao.getProduct(productId)
        return mapper.mapDbModeTolEntity(dbModel)
    }

    override fun getProductList(): LiveData<List<Product>> = productDao.getProductList().map { mapper.mapListDbModelToListEntity(it) }

}