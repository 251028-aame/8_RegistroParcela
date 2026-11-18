package com.utxicotepec.registroparcela

data class Parcela(
    val nombreProductor: String,
    val localidad: String,
    val superficieHectareas: Int, // Usa Double si permitirás decimales (ej. 1.5 ha)
    val fechaSiembra: Long,
    val cultivo: String,
    val observaciones: String
)