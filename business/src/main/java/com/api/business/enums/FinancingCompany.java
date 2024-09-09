package com.api.business.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancingCompany {

    BRADESCO("60.746.948/0001-12", "BANCO BRADESCO S.A."),
    SANTANDER("90.400.888/0001-42", "BANCO SANTANDER S.A."),
    BANCO_DO_BRASIL("00.000.000/0001-91", "BANCO DO BRASIL S.A."),
    BTG("30.306.294/0001-45", "BANCO BTG PACTUAL SA"),
    ITAU("60.872.504/0001-23", "ITAU UNIBANCO S.A."),
    CAIXA("00.360.305/0001-04", "CAIXA ECONÔMICA FEDERAL"),
    BMG("61.186.680/0001-74", "BANCO BMG S.A."),
    BANRISUL("92.702.067/0001-96", "BANCO DO ESTADO DO RIO GRANDE DO SUL"),
    ABC("28.195.667/0001-06", "BANCO SAFRA S.A."),
    SAFRA("58.160.789/0001-28", "BANCO SAFRA S.A."),
    SICOOB("02.038.232/0003-26", "BANCO COOPERATIVO SICOOB S.A."),
    SICREDI("01.181.521/0006-60", "BANCO COOPERATIVO SICREDI S.A."),
    CITIBANK("33.479.023/0001-80", "BANCO CITIBANK S.A.");

    private final String contractDocumentNumber;
    private final String companyName;
}
