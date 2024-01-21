package com.api.business.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatUtils {

    private static final String FORMAT_STATUS_ACTIVE = "Ativa";
    private static final String FORMAT_STATUS_DISABLED = "Desativada";
    private static final String FORMAT_STATUS_REALIZED = "Realizado";
    private static final String FORMAT_STATUS_PENDING = "Pendente";

    public static String convertObjToJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    public static String formatCnpj(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    public static String formatCurrency(BigDecimal value) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormatter.format(value);
    }

    public static String formatActiveStatus(boolean isActive) {
        return isActive ? FORMAT_STATUS_ACTIVE : FORMAT_STATUS_DISABLED;
    }

    public static String formatPaymentDoneStatus(boolean isPaymentDone) {
        return isPaymentDone ? FORMAT_STATUS_REALIZED : FORMAT_STATUS_PENDING;
    }
}
