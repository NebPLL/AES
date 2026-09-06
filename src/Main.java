import Encrypt.Encrypt;
import Key.Key;
import util.Byte4;
import util.Matrix4;

import Key.KeyExpansion;
import util.SBox;

public class Main {
    public static void main(String[] args) {
        Byte4 test = new Byte4((byte) 0, (byte) 0, (byte) 0, (byte) 0);

        Key key = new Key(new Matrix4(test, test, test, test));
        Matrix4 block = new Matrix4(test, test, test, test);

        Matrix4 encryptedBlock = Encrypt.EncryptBlock(block, key.KeyExpansion(11));

        System.out.println(encryptedBlock);
    }
}