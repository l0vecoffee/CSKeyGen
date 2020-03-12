package com.cobaltstrick;

import javax.crypto.*;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.*;

public class KeyGen {

    public static void readLicense(String strlicenseFile, String strPubKeyFile){
        final byte[] file = CommonUtils.readFile(strlicenseFile);
        final AuthCrypto authCrypto = new AuthCrypto(CommonUtils.readFile(strPubKeyFile), Cipher.DECRYPT_MODE);
        final byte[] decrypt = authCrypto.decrypt(file);
        if (decrypt.length == 0) {
            return;
        }
        final String[] array = CommonUtils.toArray(CommonUtils.bString(decrypt));
        if (array.length < 4) {
            return;
        }
        System.out.println("licensekey:\t" + array[0]);
        String validto = "";
        if ("forever".equals(array[1])) {
            validto = array[1];
            System.out.println("valid to:\t" + "perpetual");
        }
        else {
            validto = "20" + array[1];
            System.out.println("valid to:\t" + CommonUtils.formatDateAny("MMMMM d, YYYY", CommonUtils.parseDate(validto, "yyyyMMdd")));
        }
        System.out.println("watermark:\t" + CommonUtils.toNumber(array[2], 0));
        System.out.println("Issued at:\t" + CommonUtils.formatDate(Long.parseLong(array[3])));
    }

    public static void generateLicense(String[] array, String priKeyPath, String authFilePath){
        if (array.length < 4){
            return;
        }
        final byte[] file = CommonUtils.readFile(priKeyPath);

        final AuthCrypto authCrypto = new AuthCrypto(file, Cipher.ENCRYPT_MODE);
        final byte[] encryptbt = authCrypto.encrypt(CommonUtils.toBytes(CommonUtils.toString(array)));
        if (encryptbt.length == 0) {
            return;
        }
        File f = new File(authFilePath);
        CommonUtils.writeToFile(f, encryptbt);
    }

    public static void help(){
        System.out.println(KeyGen.class.getPackage().getName() + " -r cobaltstrike.auth authkey.pub\tRead license file info");
        System.out.println(KeyGen.class.getPackage().getName() + " -g PrivateKeyFile authkey.pub\tGenerate authkey.pub file, require feed privatekey");
        System.out.println(KeyGen.class.getPackage().getName() + " -k PrivateKeyFile PublicKeyFile\tGenerate private key file and public key");
    }

    public static void main(String[] args) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (args[0].equals("-r"))
        {
            readLicense(args[1], args[2]);
        }
        else if (args[0].equals("-k"))
        {
            RSAUtil.generateKeyPair();
            RSAUtil.writeKeyToFIle(RSAUtil.pri.getEncoded(), args[1], Cipher.PRIVATE_KEY);
            RSAUtil.writeKeyToFIle(RSAUtil.pub.getEncoded(), args[2], Cipher.PUBLIC_KEY);
        }
        else if (args[0].equals("-g"))
        {
            String[] s = {"9ab7-fbe1-d915-b858", "forever", "1873433027", "1546198065476"};
            generateLicense(s, args[1], args[2]);
        }
        else
        {
            help();
        }
    }
}
