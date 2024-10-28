package com.brank.util;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SdkForCommodity {
    private final Client client;

    public SdkForCommodity() {
        BcosSDK bcosSDK = BcosSDK.build("./conf/config.toml");
        client = bcosSDK.getClient(1);
        client.getCryptoSuite().loadAccount(
                "pem",
                "./conf/0x72a3c2a56feaafe19893edb936349139c5f10fc3.pem",""
        );
    }

    @Bean
    public Commodity loadCommodity() {
        String contractAddress = "0x46e22773e9962b6dd9aa592e38f72659a8c11237";
        return new Commodity(contractAddress, client, client.getCryptoSuite().getCryptoKeyPair());
    }
}
