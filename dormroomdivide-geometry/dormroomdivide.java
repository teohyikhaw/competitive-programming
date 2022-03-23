import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class dormroomdivide {
    private static double x1, y1;
    public static void main(String [] args){
        FastReader input = new FastReader();
        int n = input.nextInt();
        double [][] lookup_table = new double[n][3];
        double area = 0;
        x1 = input.nextDouble();
        y1 = input.nextDouble();
        lookup_table[0][0] = x1;
        lookup_table[0][1] = y1;
        lookup_table[0][2] = 0;

        lookup_table[1][0] = input.nextDouble();
        lookup_table[1][1] = input.nextDouble();
        lookup_table[1][2] = 0;

        for (int i=2; i<n; i++){
            double x3 = input.nextDouble();
            double y3 = input.nextDouble();
            double curr_area = getArea(lookup_table[i-1][0],lookup_table[i-1][1],x3,y3);
            area+=curr_area;
            lookup_table[i][0] = x3;
            lookup_table[i][1] = y3;
            lookup_table[i][2] = area;
        }
        double half_area = area/2;
        int k = n/2;

        while(true) {
            if(half_area >= lookup_table[k][2]){
                if(half_area<=lookup_table[k + 1][2]) break;
                else k++;
            }
            else k--;
        }
        k++;
        double target_first = half_area - lookup_table[k-1][2];
        double target_second;
        if(n==3) target_second = target_first;
        else target_second = half_area - (area - lookup_table[k][2]);

        double x2 = lookup_table[k-1][0];
        double y2 = lookup_table[k-1][1];
        double x3 = lookup_table[k][0];
        double y3 = lookup_table[k][1];
        double [] results = getPoints(target_first,target_second,x2,y2,x3,y3);
        System.out.println(results[0]+" "+results[1]);

    }
    public static double getArea(double x2, double y2, double x3, double y3){
        double area = 0.5*(Math.abs(x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2)));
        return area;
    }
    public static double[] getPoints(double P, double S, double x2, double y2, double x3, double y3){
        double [] results = new double[2];
        x2-=x1; y2-=y1; x3-=x1; y3-=y1;
        double y = 2*(P*y3+S*y2)/(x2*y3-x3*y2)+y1;
        double x = 2*(P*x3+S*x2)/(x2*y3-x3*y2)+x1;
        results [0] = x; results[1] = y;

        return results;
    }
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }

        long nextLong() { return Long.parseLong(next()); }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
