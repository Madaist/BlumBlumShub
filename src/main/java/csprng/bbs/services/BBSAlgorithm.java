package csprng.bbs.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

@Service
public class BBSAlgorithm {

    private static final BigInteger one = BigInteger.valueOf(1L);
    private static final BigInteger two = BigInteger.valueOf(2L);
    private static final BigInteger three = BigInteger.valueOf(3L);
    private static final BigInteger four = BigInteger.valueOf(4L);

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger x0; // seed


    private static BigInteger getPrime(int bits, Random rand) { //functie folosita pentru generarea lui p si q
        BigInteger p;
        do {
            p = new BigInteger(bits, 100, rand);
        } while (!p.mod(four).equals(three)); // numarul generat trebuie sa aiba proprietatea ca restul impartirii la 4 este 3
        return p;
    }

    public void generateN(int bits, Random rand) { // generam n = p * q
        this.p = getPrime(bits/2, rand);
        this.q = getPrime(bits/2, rand);

        while (p.equals(q)) {
            q = getPrime(bits, rand);
        }
        n = p.multiply(q);
}

    private BigInteger getSeed(int bits, Random rand) {
        BigInteger s = getPrime(bits, rand);
        while(s.compareTo(one) < 0 || s.compareTo(n) > 0) // s trebuie sa fie cuprins intre 1 si N-1
            s = getPrime(bits, rand);

        s = s.pow(2).remainder(n); // returnez reziduul patratic modulo n( s^2 mod n)
        return s;
    }

    public void generateX0(int bits, Random rand){

        BigInteger s = getSeed(bits, rand);
        while(s.equals(p) || s.equals(q) || s.compareTo(one) <= 0) // x0 trebuie sa fie diferit de p si q si trebuie sa fie mai mare decat 1
            s = getSeed(bits, rand);
        x0 = s; // cand indeplineste conditiile, asignam valoarea lui x0
    }

    public File calculate(double fileSize){
        Random rand = new Random();
        File file = new File("output.bin");

        try {
            FileOutputStream fos = new FileOutputStream(file);

            generateN(100, rand);
            generateX0(50, rand);

            BigInteger x = x0;
            double wantedSize = fileSize * 1024 * 1024;
            while(file.length() < wantedSize){
                x = x.multiply(x).remainder(n); // x(i+1) = xi ^ 2 mod n
                BigInteger b = x.remainder(two); // luam bitul de paritate 
                fos.write(b.byteValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
