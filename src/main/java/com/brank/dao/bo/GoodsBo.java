package com.brank.dao.bo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class GoodsBo {
    BigInteger roleId;
    BigInteger id;
    String name;
    BigInteger purchasePrice;
    BigInteger sellingPrice;
    BigInteger stock;
}
