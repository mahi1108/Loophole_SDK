package com.cynovo.kivvi.demo.mintutil;


import com.cynovo.kivvidevicessdk.Utils.F;


/**
 * Tools for encoding a decoding pinblock
 *
 * @author Tomas Jacko <tomas.jacko [at] monetplus.cz>
 */
public class PinblockTool {

    /**
     * Decode pinblock format 0 (ISO 9564)
     *
     * @param pin pin
     * @param pan primary account number (PAN/CLN/CardNumber)
     * @return pinblock in HEX format
     */
    public static String format0Encode(String pin, String pan) {
       try {
            final String pinLenHead = Util.leftPad(Integer.toString(pin.length()), 2, '0') + pin;
            final String pinData = Util.rightPad(pinLenHead, 16, 'F');
            final byte[] bPin = F.HexStringToByteArray(pinData);

            final String panPart = extractPanAccountNumberPart(pan);
            final String panData = Util.leftPad(panPart, 16, '0');
            final byte[] bPan =F.HexStringToByteArray(panData);

            final byte[] pinblock = new byte[8];
            for (int i = 0; i < 8; i++)
                pinblock[i] = (byte) (bPin[i] ^ bPan[i]);

            return F.ByteArrayToHexString(pinblock);

        } catch (Exception e) {
            throw new RuntimeException("Hex decoder failed!", e);
        }

    }

    /**
     * @param accountNumber PAN - primary account number
     * @return extract right-most 12 digits of the primary account number (PAN)
     */
    public static String extractPanAccountNumberPart(String accountNumber) {
        String accountNumberPart = null;
        if (accountNumber.length() > 12)
            accountNumberPart = accountNumber.substring(accountNumber.length() - 13, accountNumber.length() - 1);
        else
            accountNumberPart = accountNumber;
        return accountNumberPart;
    }


}

