package Encrypt;

import Key.Key;
import util.Byte4;
import util.GaloisField;
import util.Matrix4;
import util.SBox;

public class Encrypt {

    public static Matrix4 EncryptBlock(Matrix4 Block, Key[] RoundKeys){
        Matrix4 EncryptedBlock = AddRoundKey(Block, RoundKeys[0].key());

        for (int i = 1; i < RoundKeys.length -1; i++) {
            Matrix4 SubBlock = SubBytes(EncryptedBlock);
            Matrix4 ShiftedBlock = ShiftRows(SubBlock);
            Matrix4 MixedBlock = MixColumns(ShiftedBlock);
            EncryptedBlock = AddRoundKey(MixedBlock, RoundKeys[i].key());
        }

        Matrix4 SubBlock = SubBytes(EncryptedBlock);
        Matrix4 ShiftedBlock = ShiftRows(SubBlock);

        EncryptedBlock = AddRoundKey(ShiftedBlock, RoundKeys[RoundKeys.length-1].key());

        return EncryptedBlock;
    }

    public static Matrix4 AddRoundKey(Matrix4 block, Matrix4 key){
        Matrix4 newBlock = new Matrix4(Byte4.XOR(block.s1(), key.s1()),
                                       Byte4.XOR(block.s2(), key.s2()),
                                       Byte4.XOR(block.s3(), key.s3()),
                                       Byte4.XOR(block.s4(), key.s4()));

        return newBlock;
    }

    public static Matrix4 SubBytes(Matrix4 block){
        Byte4 newA = SBox.substitute(block.s1());
        Byte4 newB = SBox.substitute(block.s2());
        Byte4 newC = SBox.substitute(block.s3());
        Byte4 newD = SBox.substitute(block.s4());

        return new Matrix4(newA, newB, newC, newD);
    }


    public static Matrix4 ShiftRows(Matrix4 block){
        Byte4 line1 = new Byte4(block.s1().a(), block.s2().a(), block.s3().a(), block.s4().a());
        Byte4 line2 = ShiftLeft(new Byte4(block.s1().b(), block.s2().b(), block.s3().b(), block.s4().b()), 1);
        Byte4 line3 = ShiftLeft(new Byte4(block.s1().c(), block.s2().c(), block.s3().c(), block.s4().c()), 2);
        Byte4 line4 = ShiftLeft(new Byte4(block.s1().d(), block.s2().d(), block.s3().d(), block.s4().d()), 3);

        Byte4 s1 = new Byte4(line1.a(), line2.a(), line3.a(), line4.a());
        Byte4 s2 = new Byte4(line1.b(), line2.b(), line3.b(), line4.b());
        Byte4 s3 = new Byte4(line1.c(), line2.c(), line3.c(), line4.c());
        Byte4 s4 = new Byte4(line1.d(), line2.d(), line3.d(), line4.d());

        return new Matrix4(s1, s2, s3, s4);
    }

    public static Byte4 ShiftLeft(Byte4 input, int amount){
        Byte4 rotatedByte4 = input;
        for (int i = 0; i < amount ; i++) {
            rotatedByte4 = new Byte4(rotatedByte4.b(), rotatedByte4.c(), rotatedByte4.d(), rotatedByte4.a());
        }

        return rotatedByte4;
    }

    public static Matrix4 MixColumns(Matrix4 block){
        Byte4 newA = MixColumn(block.s1());
        Byte4 newB = MixColumn(block.s2());
        Byte4 newC = MixColumn(block.s3());
        Byte4 newD = MixColumn(block.s4());

        return new Matrix4(newA, newB, newC, newD);
    }

    public static Byte4 MixColumn(Byte4 column) {
        byte s0 = column.a(), s1 = column.b(), s2 = column.c(), s3 = column.d();

        byte r0 = (byte) (GaloisField.multiply(s0, (byte)0x02) ^ GaloisField.multiply(s1, (byte)0x03) ^ s2 ^ s3);
        byte r1 = (byte) (s0 ^ GaloisField.multiply(s1, (byte)0x02) ^ GaloisField.multiply(s2, (byte)0x03) ^ s3);
        byte r2 = (byte) (s0 ^ s1 ^ GaloisField.multiply(s2, (byte)0x02) ^ GaloisField.multiply(s3, (byte)0x03));
        byte r3 = (byte) (GaloisField.multiply(s0, (byte)0x03) ^ s1 ^ s2 ^ GaloisField.multiply(s3, (byte)0x02));

        return new Byte4(r0, r1, r2, r3);
    }
}
