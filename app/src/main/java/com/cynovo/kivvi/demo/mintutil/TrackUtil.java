package com.cynovo.kivvi.demo.mintutil;

import com.cynovo.kivvidevicessdk.Utils.F;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by carmelita.dimaandal on 26/10/17.
 */

public class TrackUtil {
    private static String   cTBCDSymbolString = "0123456789*#abc";
    private static char[]   cTBCDSymbols = cTBCDSymbolString.toCharArray();


    public static String formattedTrack2(String track2){
        if(track2!=null && track2.length()>0)
        {
            String formatted = track2.replace("=", "D");
            int length = track2.length() & 01;
            if(length ==1){
                formatted += "F";
            }

            return formatted;
        }

        return null;

    }


    public static String addFPadding(String data, int lengthToBeAdd){

        StringBuilder buffer = new StringBuilder();
        buffer.append(data);
        for(int i=0; i< lengthToBeAdd; i++){
            buffer.append("F");
        }
        return buffer.toString();

    }



    public static String addZeroPadding(String data, int lengthToBeAdd){
        StringBuilder buffer = new StringBuilder();
        buffer.append(data);
        for(int i=0; i< lengthToBeAdd; i++){
            buffer.append("0");
        }
        return buffer.toString();

    }


    public static int getIntFromHex(String hexString) throws NumberFormatException {
        return Integer.parseInt(hexString, 16);
    }


    /**
     * Method to check if data is null or empty. A byte array containing all zeros is considered empty.
     *
     * @param data
     *            data to be checked
     * @return boolean flag
     */
    public static boolean isNullOrEmpty(final byte[] data) {
        return (data == null || data.length == 0 || containsNothing(data));
    }

    private static boolean containsNothing(final byte[] data) {
        boolean flag = true;
        for (byte b : data) {
            if (b != 0x00) {
                flag = false;
            }
        }
        return flag;
    }



    /**
     * Execute an xor operation on 2 arrays of bytes.
     *
     * @param array1
     *            First array of bytes.
     * @param array2
     *            Second array of bytes.
     * @return The result of the xor
     * @throws IllegalArgumentException
     *             if the two arrays are not the same length.
     */
    public static byte[] xorArray(final byte[] array1, final byte[] array2) throws IllegalArgumentException {
        if (isNullOrEmpty(array1) || isNullOrEmpty(array2)) {
            throw new IllegalArgumentException("Array can't be empty or null");
        }

        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Trying to XOR two arrays of different length, operation is illegal.");
        }

        byte[] xoredArray = new byte[array1.length];
        for (int i = 0; i < xoredArray.length; i++) {
            xoredArray[i] = (byte) (array1[i] ^ array2[i]);
        }
        return xoredArray;
    }

    public static byte[] calculateDataEncryptionKey(byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        byte[] derived_key = key;

         final String DATA_ENCRYPTION_VARIANT_CONSTANT_BOTH_WAYS = "0000000000FF0000";
        byte[] derived_key_L = subArray(derived_key, 0, 7);
        byte[] derived_key_R = subArray(derived_key, 8, 15);
        byte[] data_encryption_variant_constant_both_ways = F.HexStringToByteArray(DATA_ENCRYPTION_VARIANT_CONSTANT_BOTH_WAYS );

        // 1 - derived_key_L XOR'ed with DATA_ENCRYPTION_VARIANT_CONSTANT_BOTH_WAYS = variant_key_L
        byte[] variant_key_L = xorArray(derived_key_L, data_encryption_variant_constant_both_ways);

        // 2 - derived_key_R XOR'ed with DATA_ENCRYPTION_VARIANT_CONSTANT_BOTH_WAYS = variant_key_R
        byte[] variant_key_R = xorArray(derived_key_R, data_encryption_variant_constant_both_ways);

        // 3 - variant_key_L << 64 & variant_key_R = variant_key_L_R
        byte[] variant_key_L_R = join(variant_key_L, variant_key_R);

        // 4 - TDEA variantkley_L with variant_key_L_R = encryption_key_L
        byte[] encryption_key_L = DESCryptoUtil.tdesEncrypt(variant_key_L, variant_key_L_R);

        // 5 - TDEA variant_key_R with variant_key_L_R = encryption_key_R
        byte[] encryption_key_R = DESCryptoUtil.tdesEncrypt(variant_key_R, variant_key_L_R);

        // 6 - variant_key_L << 64 & variant_key_R = new_derived_data_key
        byte[] new_derived_data_key = join(encryption_key_L, encryption_key_R);

        return new_derived_data_key;
    }

    public static byte[] join(byte[] left, byte[] right) {
        byte[] result = new byte[left.length + right.length];

        for(int i = 0; i < left.length; i ++) {
            result[i] = left[i];
        }

        for(int i = 0; i < right.length; i ++) {
            result[i + left.length] = right[i];
        }

        return result;
    }


    public static byte[] subArray(byte[] array, int from, int to) {
        int length = (to - from) + 1;
        byte[] subArray = new byte[length];

        for(int i = 0, j = from; j < to + 1; i++, j++) {
            subArray[i] = array[j];
        }

        return subArray;
    }



}
