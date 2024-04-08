package com.vismiokt.benefit_calculator.data

import com.vismiokt.benefit_calculator.domain.CalculatedData
import com.vismiokt.benefit_calculator.domain.Product

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
            isBestPrice = calcData.isBestPrice,
            errorInputWeight = calcData.errorInputWeight,
            errorInputPrice = calcData.errorInputPrice
        )
    }

    fun mapCalcDataDbToEntity(calcDataDb: CalculatedDataDbModel): CalculatedData {
        return CalculatedData(
            id = calcDataDb.id,
            productId = calcDataDb.productId,
            price = calcDataDb.price,
            weight = calcDataDb.weight,
            resultPrice = calcDataDb.resultPrice,
            isBestPrice = calcDataDb.isBestPrice,
            errorInputWeight = calcDataDb.errorInputWeight,
            errorInputPrice = calcDataDb.errorInputPrice
        )

    }

    fun mapListDbModelToListEntity(list: List<ProductDbModel>) = list.map {
        mapDbModeTolEntity(it)
    }


    fun mapListCalcDataEntityToListDb(list: List<CalculatedData>, productId: Int) = list.map {
        mapCalcDataEntityToDbModel(it, productId)
    }


}