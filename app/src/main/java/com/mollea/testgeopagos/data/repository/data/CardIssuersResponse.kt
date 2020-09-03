package com.mollea.testgeopagos.data.repository.data

import com.mollea.testgeopagos.domain.CardIssuer

class CardIssuersResponse : ArrayList<IssuerResponseItem>() {
    fun toCardIssuerList() = this.map { item ->
        CardIssuer(
            id = item.id,
            name = item.name,
            thumbnail = item.secureThumbnail
        )
    }
}