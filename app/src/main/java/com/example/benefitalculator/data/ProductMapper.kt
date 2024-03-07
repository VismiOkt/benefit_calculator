package com.example.benefitalculator.data

import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

class ProductMapper {

    fun mapEntityToDbModel(product: Product): ProductDbModel {
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

    fun mapCalcDataEntityToDbModel(calcData: CalculatedData): CalculatedDataDbModel {
        return CalculatedDataDbModel(
            productId = 0,
            id = calcData.id,
            price = calcData.price,
            weight = calcData.weight,
            resultPrice = calcData.resultPrice,
            isBestPrice = calcData.isBestPrice
        )
    }

    fun mapCalcDataDbToEntity(calcDataDb: CalculatedDataDbModel): CalculatedData {
        return CalculatedData(
            id = calcDataDb.id,
            price = calcDataDb.price,
            weight = calcDataDb.weight,
            resultPrice = calcDataDb.resultPrice,
            isBestPrice = calcDataDb.isBestPrice
        )

    }

    fun mapListDbModelToListEntity(list: List<ProductDbModel>) = list.map {
        mapDbModeTolEntity(it)
    }

    fun mapListCalcDataDbToListEntity(list: List<CalculatedDataDbModel>) = list.map {
        mapCalcDataDbToEntity(it)
    }
}