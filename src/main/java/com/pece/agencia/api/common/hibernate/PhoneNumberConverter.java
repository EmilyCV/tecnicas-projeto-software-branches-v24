package com.pece.agencia.api.common.hibernate;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PhoneNumberConverter implements AttributeConverter<Phonenumber.PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(Phonenumber.PhoneNumber phonenumber) {
        if (phonenumber == null) {
            return null;
        } else {
            return PhoneNumberUtil.getInstance().format(phonenumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        }
    }

    @Override
    public Phonenumber.PhoneNumber convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        } else {
            try {
                return PhoneNumberUtil.getInstance().parse(value, "BR");
            } catch (Exception e) {
                throw new IllegalArgumentException("Número de telefone inválido: " + value, e);
            }
        }
    }
}
