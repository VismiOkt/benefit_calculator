package com.example.benefitalculator.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.benefitalculator.domain.BenefitCalculatorRepository
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

class BenefitCalculatorRepositoryImpl(
    application: Application
): BenefitCalculatorRepository {

    private val productDao = BenefitCalculatorDatabase.getInstance(application).productDao()
    private val mapper = ProductMapper()

    override suspend fun addProduct(product: Product): Long {
        return productDao.addProduct(product = mapper.mapEntityToDbModel(product))
    }

    override suspend fun addCalculatedData(calcData: List<CalculatedData>, productId: Int) {
        productDao.addCalculatedData(calcData = mapper.mapListCalcDataEntityToListDb(calcData, productId))
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product = mapper.mapEntityToDbModelEdit(product))
    }

    override suspend fun editProduct(product: Product) {
        productDao.updateProduct(product = mapper.mapEntityToDbModelEdit(product))
    }

    override suspend fun getProduct(productId: Int): Product {
        val dbModel = productDao.getProduct(productId)
        return mapper.mapDbModeTolEntity(dbModel)
    }

    override fun getProductList(): LiveData<List<Product>> = productDao.getProductList().map {
        mapper.mapListDbModelToListEntity(it)
    }

    override fun getCalcData(productId: Int): LiveData<List<CalculatedData>> = productDao.getCalcData(productId).map {
        mapper.mapListCalcDataDbToListEntity(it)
    }

    override suspend fun getBestPrice(productId: Int): Double {
        return productDao.getBestPrice(productId)
    }

    //    override suspend fun getCalculatedList(product: Product): LiveData<List<CalculatedData>> = productDao.getCalcData() {
//        TODO("Not yet implemented")
//    }
}