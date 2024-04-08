package com.vismiokt.benefit_calculator.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vismiokt.benefit_calculator.domain.BenefitCalculatorRepository
import com.vismiokt.benefit_calculator.domain.CalculatedData
import com.vismiokt.benefit_calculator.domain.Product

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

    override suspend fun deleteAllCalcData(productId: Int) {
        productDao.deleteAllCalcData(productId)
    }


    override suspend fun getProduct(productId: Int): Product {
        val dbModel = productDao.getProduct(productId)
        return mapper.mapDbModeTolEntity(dbModel)
    }

    override fun getProductList(): LiveData<List<Product>> = productDao.getProductList().map {
        mapper.mapListDbModelToListEntity(it)
    }

    override suspend fun getCalcData(productId: Int): List<CalculatedData> = productDao.getCalcData(productId).map {
        mapper.mapCalcDataDbToEntity(it)
    }

    override suspend fun deleteCalcData(calcDataId: Int, productId: Int) {
        productDao.deleteCalcData(calcDataId, productId)
    }
}