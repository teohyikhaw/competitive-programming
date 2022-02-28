import java.io.*;
import java.util.HashSet;
import java.util.Vector;

public class golfbot {
    public static void main(String [] args){
        Reader input = new Reader();
        int n = input.nextInt();
        HashSet<Integer> coff = new HashSet<>();
        int max = 0;
        for(int i=0; i<n;i++){
            int temp = input.nextInt();
            if(temp>max) max=temp;
            coff.add(temp);
        }
        int size = nextPowerOf2(max+1);
        Vector<complex> Ax = new Vector<>(size);
        complex ones = new complex(1,0);
        complex zeros = new complex(0,0);
        for(int i=0;i<size;i++){
            if(coff.contains(i)) Ax.add(ones);
            else Ax.add(zeros);
        }
        Vector<complex> Bx = Ax;
        Bx.set(0, ones);
        Vector<complex> results_C = elem_product(recc_fft(Ax),recc_fft(Bx));
        Vector<complex> Cx = recc_ifft(results_C);
        int Cx_size = Cx.size();
        int testcount = input.nextInt();
        int success = 0;
        for(int i=0; i<testcount;i++){
            int test = input.nextInt();
            if(test>Cx_size) continue;
            if(Cx.get(test).r>0.5) success++;
        }
        System.out.println(success);
    }
    static int nextPowerOf2(int n) {
        //https://www.geeksforgeeks.org/smallest-power-of-2-greater-than-or-equal-to-n/
        int count = 0;
        if (n > 0 && (n & (n - 1)) == 0) return n;
        while(n != 0) {
            n >>= 1;
            count += 1;
        }
        return 1 << count;
    }
    public static Vector<complex> elem_product(Vector<complex> a, Vector<complex> b){
        int length = a.size();
        Vector<complex> results = new Vector<>();
        for (int i=0; i<length;i++){
            results.add(complex.multiply(a.get(i),b.get(i)));
        }
        return results;
    }
    public static Vector<complex> recc_fft(Vector<complex> a){
        int n = a.size();
        if(n==1){
            a.add(1,a.get(0));
            return a;
        }
        double theta = Math.PI/n;
        complex temp = new complex();
        complex omega_n = complex.exp(theta);
        complex omega = new complex(1,0);
        //Splitting array
        Vector<complex> a0 = new Vector<>(n/2);
        Vector<complex> a1 = new Vector<>(n/2);
        for (int i = 0; i < n; i+=2) {
            a0.add(a.get(i));
            a1.add(a.get(i+1));
        }
        //End of splitting array
        Vector<complex> y0 = recc_fft(a0);
        Vector<complex> y1 = recc_fft(a1);
        Vector<complex> y = new Vector<>(2*n);
        for(int i=0;i<2*n;i++) y.add(temp);

        for(int k=0; k<=n-1;k++){
            complex p = y0.get(k); //y0[k]
            complex q = complex.multiply(omega,y1.get(k)); //omega*y1[k]
            y.set(k,complex.add(p,q));
            y.set(k+n,complex.subtract(p,q));
            omega = complex.multiply(omega,omega_n);
        }
        return y;
    }
    public static Vector<complex> recc_ifft(Vector<complex> y){
        int n = y.size();
        if(n==1){
            return y;
        }
        double theta = -Math.PI*2/n;

        complex omega_n = complex.exp(theta);
        complex omega = new complex(1,0);
        //Splitting array
        Vector<complex> y0 = new Vector<>(n/2);
        Vector<complex> y1 = new Vector<>(n/2);
        for (int i = 0; i < n; i+=2) {
            y0.add(y.get(i));
            y1.add(y.get(i+1));
        }
        //End of splitting array
        Vector<complex> a0 = recc_ifft(y0);
        Vector<complex> a1 = recc_ifft(y1);
        Vector<complex> a = y;
        for(int k=0; k<=n/2-1;k++){
            complex p = a0.get(k); //y0[k]
            complex q = complex.multiply(omega,a1.get(k)); //omega*y1[k]
            a.set(k,complex.add(p,q));
            a.set(k+n/2,complex.subtract(p,q));
            omega = complex.multiply(omega,omega_n);
        }
        return a;
    }
    public static int rev(int k, int highbit){
        return Integer.reverse(k) >>> (32-highbit);
    }
    public static Vector<complex> recc_fft_n(Vector<complex> a){
        int n = a.size();
        if(n==1){
            return a;
        }
        double theta = Math.PI*2/n;

        complex omega_n = complex.exp(theta);
        complex omega = new complex(1,0);
        //Splitting array
        Vector<complex> a0 = new Vector<>(n/2);
        Vector<complex> a1 = new Vector<>(n/2);
        for (int i = 0; i < n; i+=2) {
            a0.add(a.get(i));
            a1.add(a.get(i+1));
        }
        //End of splitting array
        Vector<complex> y0 = recc_fft_n(a0);
        Vector<complex> y1 = recc_fft_n(a1);
        Vector<complex> y = a;
        for(int k=0; k<=n/2-1;k++){
            complex p = y0.get(k); //y0[k]
            complex q = complex.multiply(omega,y1.get(k)); //omega*y1[k]

            y.set(k,complex.add(p,q));
            y.set(k+n/2,complex.subtract(p,q));
            omega = complex.multiply(omega,omega_n);
        }
        return y;
    }
}
//Fast IO
class Reader {
    BufferedInputStream bi;

    Reader() {
        bi = new BufferedInputStream(System.in);
    }

    /**
     * Scans for an integer. Skips the character immediately after the number.
     *
     * @return The number.
     * @return 0 at end-of-file
     */
    int nextInt() {
        try {
            int input = bi.read();
            int output = 0;
            int sign = 1;
            // keep reading until a number or sign is reached
            while (!((input >= '0' && input <= '9') || input == '-' || input == -1)) {
                input = bi.read();
            }
            // EOF
            if (input == -1) {
                return 0;
            }
            // read the number
            if (input == '-') {
                sign = -1;
                input = bi.read();
            }
            while (input >= '0' && input <= '9') {
                output *= 10;
                output += input - '0';
                input = bi.read();
            }
            return output * sign;
        } catch (IOException ignored) {
            return 0;
        }
    }
}

class complex{
    double r;
    double i;
    complex(){}
    complex(double r, double i){
        this.r = r;
        this.i = i;
    }
    static complex exp(double u){
        return new complex(Math.cos(u),Math.sin(u));
    }
    static complex add (complex a, complex b){
        return new complex(a.r+b.r,a.i+b.i);
    }
    static complex subtract(complex a, complex b){
        return new complex(a.r-b.r,a.i-b.i);
    }
    static complex multiply(complex a, complex b){
        return new complex(a.r*b.r-a.i*b.i,a.r*b.i+a.i*b.r);
    }
    static complex divide(complex a, double n){
        return new complex(a.r/n,a.i/n);
    }
    void divide(double n){
        r = r/n;
        i = i/n;
    }
    void print(){
        System.out.println(r+"+"+i+"i");
    }
}
