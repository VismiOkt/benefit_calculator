package com.example.benefitalculator.data

import com.example.benefitalculator.domain.Product

class ProductMapper {

    fun mapEntityToDbModel(product: Product): ProductDbModel {
        return ProductDbModel(
            id = product.id,
            name = product.name,
            calcData = product.calcData,
            note = product.note
        )
    }

    fun mapDbModeTolEntity(productDbModel: ProductDbModel): Product {
        return Product(
            id = productDbModel.id,
            name = productDbModel.name,
            calcData = productDbModel.calcData,
            note = productDbModel.note
        )
    }

    fun mapListDbModelToListEntity(list: List<ProductDbModel>) = list.map {
        mapDbModeTolEntity(it)
    }
}