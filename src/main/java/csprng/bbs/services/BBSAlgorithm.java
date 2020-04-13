package csprng.bbs.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;


public class BBSAlgorithm {

    private static final BigInteger one = BigInteger.valueOf(1L);
    private static final BigInteger two = BigInteger.valueOf(2L);
    private static final BigInteger three = BigInteger.valueOf(3L);
    private static final BigInteger four = BigInteger.valueOf(4L);

    private BigInteger p;
    private BigInteger q;
    private BigInteger m;
    private BigInteger x0;


    private static BigInteger getPrime(int bits, Random rand) { //functie folosita pentru generarea lui p si q
        BigInteger p;
        do {
            p = new BigInteger(bits, 100, rand);
        } while (!p.mod(four).equals(three)); // numarul generat trebuie sa aiba proprietatea ca restul impartirii la 4 este 3
        return p;
    }

    public void generateM(int bits, Random rand) { // generam M = p * q
        this.p = getPrime(bits/2, rand);
        this.q = getPrime(bits/2, rand);

        while (p.equals(q)) {
            q = getPrime(bits, rand);
        }
        m = p.multiply(q);

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("m = " + m);
    }

    private BigInteger getSeed(int bits, Random rand) {
        BigInteger s = getPrime(bits, rand);
        while(s.compareTo(one) < 0 || s.compareTo(m) > 0) // s trebuie sa fie cuprins intre 1 si M-1
            s = getPrime(bits, rand);

        s = s.pow(2).remainder(m); // returnez reziduul patratic ( s^2 mod M)
        return s;
    }

    public void generateX0(int bits, Random rand){

        BigInteger s = getSeed(bits, rand);
        while(s.equals(p) || s.equals(q) || s.compareTo(one) <= 0) // x0 trebuie sa fie diferit de p si q si trebuie sa fie mai mare decat 1
            s = getSeed(bits, rand);
        x0 = s; // cand indeplineste conditiile, asignam valoarea lui x0
        System.out.println("seed = " + x0);

    }

    public void calculate(){
         ArrayList<Byte> bits = new ArrayList<>();
         StringBuilder bitOutput = new StringBuilder();
        Random rand = new Random();

        generateM(100, rand);
        generateX0(50, rand);

        BigInteger x = x0;
        System.out.println("Initial x is " + x);
        for(int i = 0; i < 10; i++){
            x = x.multiply(x).remainder(m);
            BigInteger b = x.remainder(two);
            bitOutput.append(b);
            bits.add(b.byteValue());
        }
        System.out.println(bitOutput);
        System.out.println(bits);
    }






}
