package com.botica.alquimista.models;

/**
 * Datos de envío que el cliente diligencia en el formulario de pago.
 */
public record ShippingDetails(String fullName, String email, String address, String city, String phone) {

    /** Datos válidos según HU-01: correo con formato válido y teléfono de 10 dígitos. */
    public static ShippingDetails valid() {
        return new ShippingDetails("Nicolás Flamel", "nicolas.flamel@botica.test",
                "Calle 12 # 34-56", "Cali", "3001234567");
    }

    public ShippingDetails withEmail(String newEmail) {
        return new ShippingDetails(fullName, newEmail, address, city, phone);
    }

    public ShippingDetails withPhone(String newPhone) {
        return new ShippingDetails(fullName, email, address, city, newPhone);
    }
}
