package com.brank.dao.bo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PurchasedGoodsBo {
    BigInteger id;
    BigInteger num;
    BigInteger price;
    BigInteger evaluation;
}
