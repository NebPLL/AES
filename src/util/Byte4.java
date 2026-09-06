package util;

public record Byte4(byte a, byte b, byte c, byte d) {
    public static Byte4 XOR(Byte4 a, Byte4 b){
        byte newA = (byte) (a.a() ^ b.a());
        byte newB = (byte) (a.b() ^ b.b());
        byte newC = (byte) (a.c() ^ b.c());
        byte newD = (byte) (a.d() ^ b.d());

        return new Byte4(newA, newB, newC, newD);
    }
}
