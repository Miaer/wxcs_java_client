package com.springboot.test;

import com.springboot.constant.TransferAgreement;

/**
 * @author mrliz
 */
public class EnumTest {

    public static void main(String[] args) {
        short value = TransferAgreement.PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG.getValue();
        short value1 = TransferAgreement.PACKAGE_TYPE_CLIENT_CONFIG_SYNC.getValue();
        System.out.println(value1);

    }
}
