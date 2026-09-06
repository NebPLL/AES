package util;

public class GaloisField{
    public static byte xtime(byte a) {
        boolean highBitSet = (a & 0x80) != 0;
        byte shifted = (byte) (a << 1);
        if (highBitSet) {
            shifted = (byte) (shifted ^ 0x1B);
        }
        return shifted;
    }

    public static byte multiply(byte a, byte factor) {
        if (factor == 0x01) return a;
        if (factor == 0x02) return xtime(a);
        if (factor == 0x03) return (byte) (xtime(a) ^ a);
        throw new IllegalArgumentException("Unsupported factor: " + factor);
    }
}
