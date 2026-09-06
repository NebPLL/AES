package Key;

import util.Byte4;
import util.Matrix4;

public record Key(Matrix4 key) {
    public Key[] KeyExpansion(int RoundKeys){
        Key[] keys = new Key[RoundKeys];
        keys[0] = this;

        for (int i = 1; i < RoundKeys; i++) {
            Matrix4 lastKeyMatrix = keys[i-1].key();
            Byte4 temp = KeyExpansion.GFunction(lastKeyMatrix.s4(), i);
            Byte4 newS1 = Byte4.XOR(temp, lastKeyMatrix.s1());
            Byte4 newS2 = Byte4.XOR(newS1, lastKeyMatrix.s2());
            Byte4 newS3 = Byte4.XOR(newS2, lastKeyMatrix.s3());
            Byte4 newS4 = Byte4.XOR(newS3, lastKeyMatrix.s4());

            keys[i] = new Key(new Matrix4(newS1, newS2, newS3, newS4));
        }

        return keys;
    }
}
