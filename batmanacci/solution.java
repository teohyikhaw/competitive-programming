import java.util.Scanner;

public class solution {
    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        long K = input.nextLong();

        long [] fibs = new long[89];
        fibs[0] = 0; fibs[1] = 1;
        for(int i=2; i<89;i++){
            fibs[i] = fibs[i-1]+fibs[i-2];
        }

        if (N>88) N=88;
        while(N>2){
            if (K > fibs[N - 2]) {
                K -= fibs[N - 2];
                N--;
            } else {
                N-=2;
            }
        }
        if(N==1) System.out.println("N");
        else if(N==2) System.out.println("A");
    }
}
