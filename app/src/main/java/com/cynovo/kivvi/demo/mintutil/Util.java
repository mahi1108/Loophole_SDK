package com.cynovo.kivvi.demo.mintutil;

/**
 * @author yinheli
 */
public class Util {

    private static final String[] hexStrings;
    /**
     * A String for a space character.
     *
     * @since 3.2
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     * @since 2.0
     */
    public static final String EMPTY = "";


    /**
     * <p>The maximum size to which the padding constant(s) can expand.</p>
     */
    private static final int PAD_LIMIT = 8192;


    static {
        hexStrings = new String[256];
        for (int i = 0; i < 256; i++ ) {
            StringBuilder d = new StringBuilder(2);
            char ch = Character.forDigit(((byte)i >> 4) & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            ch = Character.forDigit((byte)i & 0x0F, 16);
            d.append(Character.toUpperCase(ch));
            hexStrings[i] = d.toString();
        }

    }

    private Util() {
    }

    public static byte[] hex2byte (byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i=0; i<len*2; i++) {
            int shift = i%2 == 1 ? 0 : 4;
            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
        }
        return d;
    }

    public static byte[] hex2byte (String s) {
        if (s.length() % 2 == 0) {
            return hex2byte (s.getBytes(), 0, s.length() >> 1);
        } else {
            return hex2byte("0"+s);
        }
    }

    public static String hexString(byte[] b) {
        StringBuilder d = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            d.append(hexStrings[(int) aB & 0xFF]);
        }
        return d.toString();
    }

    public static byte[] trim (byte[] array, int length) {
        byte[] trimmedArray = new byte[length];
        System.arraycopy(array, 0, trimmedArray, 0, length);
        return  trimmedArray;
    }

    public static byte[] concat (byte[] array1, int beginIndex1, int length1, byte[] array2,
                                 int beginIndex2, int length2) {
        byte[] concatArray = new byte[length1 + length2];
        System.arraycopy(array1, beginIndex1, concatArray, 0, length1);
        System.arraycopy(array2, beginIndex2, concatArray, length1, length2);
        return  concatArray;
    }

    public static byte[] concat (byte[] array1, byte[] array2) {
        byte[] concatArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, concatArray, 0, array1.length);
        System.arraycopy(array2, 0, concatArray, array1.length, array2.length);
        return  concatArray;
    }

    /**
     * <p>Left pad a String with spaces (' ').</p>
     *
     * <p>The String is padded to the size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     */
    public static String leftPad(final String str, final int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * <p>Left pad a String with a specified character.</p>
     *
     * <p>Pad to a size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     * @since 2.0
     */
    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }

    /**
     * <p>Left pad a String with a specified String.</p>
     *
     * <p>Pad to a size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     */
    public static String leftPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }


    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }


    // Padding
    //-----------------------------------------------------------------------
    /**
     * <p>Repeat a String {@code repeat} times to form a
     * new String.</p>
     *
     * <pre>
     * StringUtils.repeat(null, 2) = null
     * StringUtils.repeat("", 0)   = ""
     * StringUtils.repeat("", 2)   = ""
     * StringUtils.repeat("a", 3)  = "aaa"
     * StringUtils.repeat("ab", 2) = "abab"
     * StringUtils.repeat("a", -2) = ""
     * </pre>
     *
     * @param str  the String to repeat, may be null
     * @param repeat  number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     *  {@code null} if null String input
     */
    public static String repeat(final String str, final int repeat) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return EMPTY;
        }
        final int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return repeat(str.charAt(0), repeat);
        }

        final int outputLength = inputLength * repeat;
        switch (inputLength) {
            case 1 :
                return repeat(str.charAt(0), repeat);
            case 2 :
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char[] output2 = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default :
                final StringBuilder buf = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    /**
     * <p>Repeat a String {@code repeat} times to form a
     * new String, with a String separator injected each time. </p>
     *
     * <pre>
     * StringUtils.repeat(null, null, 2) = null
     * StringUtils.repeat(null, "x", 2)  = null
     * StringUtils.repeat("", null, 0)   = ""
     * StringUtils.repeat("", "", 2)     = ""
     * StringUtils.repeat("", "x", 3)    = "xxx"
     * StringUtils.repeat("?", ", ", 3)  = "?, ?, ?"
     * </pre>
     *
     * @param str        the String to repeat, may be null
     * @param separator  the String to inject, may be null
     * @param repeat     number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     *  {@code null} if null String input
     * @since 2.5
     */
    public static String repeat(final String str, final String separator, final int repeat) {
        if(str == null || separator == null) {
            return repeat(str, repeat);
        }
        // given that repeat(String, int) is quite optimized, better to rely on it than try and splice this into it
        final String result = repeat(str + separator, repeat);
        return removeEnd(result, separator);
    }

    /**
     * <p>Returns padding using the specified delimiter repeated
     * to a given length.</p>
     *
     * <pre>
     * StringUtils.repeat('e', 0)  = ""
     * StringUtils.repeat('e', 3)  = "eee"
     * StringUtils.repeat('e', -2) = ""
     * </pre>
     *
     * <p>Note: this method does not support padding with
     * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a>
     * as they require a pair of {@code char}s to be represented.
     * If you are needing to support full I18N of your applications
     * consider using {@link #repeat(String, int)} instead.
     * </p>
     *
     * @param ch  character to repeat
     * @param repeat  number of times to repeat char, negative treated as zero
     * @return String with repeated character
     * @see #repeat(String, int)
     */
    public static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return EMPTY;
        }
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    public static String removeEnd(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }


    /**
     * <p>Right pad a String with spaces (' ').</p>
     *
     * <p>The String is padded to the size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.rightPad(null, *)   = null
     * StringUtils.rightPad("", 3)     = "   "
     * StringUtils.rightPad("bat", 3)  = "bat"
     * StringUtils.rightPad("bat", 5)  = "bat  "
     * StringUtils.rightPad("bat", 1)  = "bat"
     * StringUtils.rightPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return right padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     */
    public static String rightPad(final String str, final int size) {
        return rightPad(str, size, ' ');
    }

    /**
     * <p>Right pad a String with a specified character.</p>
     *
     * <p>The String is padded to the size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.rightPad(null, *, *)     = null
     * StringUtils.rightPad("", 3, 'z')     = "zzz"
     * StringUtils.rightPad("bat", 3, 'z')  = "bat"
     * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
     * StringUtils.rightPad("bat", 1, 'z')  = "bat"
     * StringUtils.rightPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return right padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     * @since 2.0
     */
    public static String rightPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(repeat(padChar, pads));
    }

    public static String rightPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }




}
