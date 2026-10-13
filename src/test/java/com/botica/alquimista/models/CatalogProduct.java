package com.botica.alquimista.models;

/**
 * Producto tal como lo muestra una tarjeta del catálogo.
 * Precio y stock se guardan con el formato que ve el usuario ("$18.500", "10").
 */
public record CatalogProduct(String name, String category, String price, String stock) {
}
