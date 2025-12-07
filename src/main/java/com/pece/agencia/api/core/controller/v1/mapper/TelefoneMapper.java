package com.pece.agencia.api.core.controller.v1.mapper;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring"
)
public abstract class TelefoneMapper {

    public String toDTO(Phonenumber.PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            return null;
        } else {
            return PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        }
    }
}
