package com.example.benefitalculator.data

import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

class ProductMapper {

    fun mapEntityToDbModel(product: Product): ProductDbModel {
        return ProductDbModel(
            id = 0,
            name = product.name,
            note = product.note
        )
    }

    fun mapEntityToDbModelEdit(product: Product): ProductDbModel {
        return ProductDbModel(
            id = product.id,
            name = product.name,
            note = product.note
        )
    }


    fun mapDbModeTolEntity(productDbModel: ProductDbModel): Product {
        return Product(
            id = productDbModel.id,
            name = productDbModel.name,
            note = productDbModel.note
        )
    }

    private fun mapCalcDataEntityToDbModel(calcData: CalculatedData, productId: Int): CalculatedDataDbModel {
        return CalculatedDataDbModel(
            productId = productId,
            id = 0,
            price = calcData.price,
            weight = calcData.weight,
            resultPrice = calcData.resultPrice,
            isBestPrice = calcData.isBestPrice
        )
    }

    fun mapCalcDataDbToEntity(calcDataDb: CalculatedDataDbModel): CalculatedData {
        return CalculatedData(
            id = calcDataDb.id,
            productId = calcDataDb.productId,
            price = calcDataDb.price,
            weight = calcDataDb.weight,
            resultPrice = calcDataDb.resultPrice,
            isBestPrice = calcDataDb.isBestPrice
        )

    }

    fun mapListDbModelToListEntity(list: List<ProductDbModel>) = list.map {
        mapDbModeTolEntity(it)
    }


    fun mapListCalcDataEntityToListDb(list: List<CalculatedData>, productId: Int) = list.map {
        mapCalcDataEntityToDbModel(it, productId)
    }


}