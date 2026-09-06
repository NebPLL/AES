package Key;

import util.Byte4;
import util.GaloisField;
import util.SBox;

public class KeyExpansion {
    public static Byte4 GFunction(Byte4 temp, int round){
        Byte4 rotTemp = RotWord(temp);
        Byte4 subTemp = SubWord(rotTemp);
        return applyRCon(subTemp, round);
    }

    public static Byte4 RotWord(Byte4 temp){
        return new Byte4(temp.b(), temp.c(), temp.d(), temp.a());
    }

    public static Byte4 SubWord(Byte4 temp){
        return new Byte4(SBox.substitute(temp.a()), SBox.substitute(temp.b()), SBox.substitute(temp.c()), SBox.substitute(temp.d()));
    }


    public static Byte4 applyRCon(Byte4 temp, int round){
        byte rconValue = RConValue(round);
        byte newA = (byte) (temp.a() ^ rconValue);

        return new Byte4(newA, temp.b(), temp.c(), temp.d());
    }


    public static byte RConValue(int round){
        byte newAByte = 0x01;
        for (int i = 0; i < round - 1; i++) {
            newAByte = GaloisField.xtime(newAByte);
        }
        return newAByte;
    }
}
