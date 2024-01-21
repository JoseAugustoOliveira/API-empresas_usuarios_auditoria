package com.api.business.utils;


public interface Regex {

    String CEP_REGEX = "[\\d]{8}";
    String CEP_MASK =  "[\\d]{5}-[\\d]{3}";
    String CPF = "[\\d]{11}";
    String EMAIL_REGEX = "^[\\w-_.+]*@([\\w-+]+\\.)+[\\w]+[\\w]$";
    String CPF_REGEX_WITH_MASK = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
    String PHONE_REGEX_WITH_MASK = "\\(\\d{2}\\) \\d{4,5}-\\d{4}";
    String CRM_BRAZIL_REGEX = "^[A-Z]{2}\\d{4,5}$";
    String CNPJ_REGEX_WITH_MASK = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";
    String CNPJ = "\\d{14}";
    String ONLY_NUMBER = "\\d+";
    String NUMBER_DAY = "\\d{1,48}";
}
