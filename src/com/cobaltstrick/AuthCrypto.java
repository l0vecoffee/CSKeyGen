package com.cobaltstrick;

import javax.crypto.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.*;

public class AuthCrypto {
    public Cipher cipher;
    public Key pubkey;
    public Key prikey;
    protected String error;

    public AuthCrypto(byte[] btKey, int nCipherMode) {
        this.pubkey = null;
        this.error = null;
        try {
            this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            switch (nCipherMode){
                case Cipher.ENCRYPT_MODE:
                    this.prikey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(btKey));
                    break;
                case Cipher.DECRYPT_MODE:
                    this.pubkey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(btKey));;
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex) {
            this.error = "Could not initialize crypto";
        }
    }

    public byte[] decrypt(final byte[] array) {
        final byte[] decrypt = this._decrypt(array);
        try {
            if (decrypt.length == 0) {
                return decrypt;
            }
            final DataParser dataParser = new DataParser(decrypt);
            dataParser.big();
            if (dataParser.readInt() != 0xcafec0bb) {
                this.error = "bad header";
                return new byte[0];
            }
            return CommonUtils.gunzip(dataParser.readBytes(dataParser.readShort()));
        }
        catch (Exception ex) {
            this.error = ex.getMessage();
            return new byte[0];
        }
    }
    /**
     * int转byte数组
     * @param num
     * @return bytes
     */
    private static byte[]_IntToByte(int num){
        byte[]bytes=new byte[4];
        bytes[0]=(byte) ((num>>24)&0xff);
        bytes[1]=(byte) ((num>>16)&0xff);
        bytes[2]=(byte) ((num>>8)&0xff);
        bytes[3]=(byte) (num&0xff);
        return bytes;
    }

    /**
     * word 转byte数组
     * @param num
     * @return
     */
    private static byte[]_WordToByte(int num){
        byte[]bytes=new byte[2];
        bytes[0]=(byte) ((num>>8)&0xff);
        bytes[1]=(byte) (num&0xff);
        return bytes;
    }

    public byte[] encrypt(final byte[] array){
        byte[] btZip = CommonUtils.gzip(array);
        byte[] tmp = new byte[6 + btZip.length];
        System.arraycopy(_IntToByte(0xcafec0bb), 0, tmp, 0, 4);
        System.arraycopy(_WordToByte(btZip.length), 0, tmp, 4, 2);
        System.arraycopy(btZip, 0, tmp, 6, btZip.length);;

        final byte[] encrypt = this._encrypt(tmp);
        try {
            if (encrypt.length == 0) {
                return encrypt;
            }

            return encrypt;
        } catch (Exception ex) {
            this.error = ex.getMessage();
            return new byte[0];
        }
    }

    protected byte[] _encrypt(final byte[] array){
        byte[] doFinal = new byte[0];
        try {
            if (this.prikey == null) {
                return new byte[0];
            }
            synchronized (this.cipher) {
                this.cipher.init(Cipher.ENCRYPT_MODE, this.prikey);
                doFinal = this.cipher.doFinal(array);
            }
            return doFinal;
        }
        catch (Exception ex) {
            this.error = ex.getMessage();
            return new byte[0];
        }
    }

    protected byte[] _decrypt(final byte[] array) {
        byte[] doFinal = new byte[0];
        try {
            if (this.pubkey == null) {
                return new byte[0];
            }
            synchronized (this.cipher) {
                this.cipher.init(Cipher.DECRYPT_MODE, this.pubkey);
                doFinal = this.cipher.doFinal(array);
            }
            return doFinal;
        }
        catch (Exception ex) {
            this.error = ex.getMessage();
            return new byte[0];
        }
    }
}
