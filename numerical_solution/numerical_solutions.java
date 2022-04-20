public class numerical_solutions {
    public static void main(String [] args){
        eulers_method(1.0,-2,0,10);
        runge_katta(0.2,-2,0,10);
        improved_euler(0.0001,1,0,10000);
    }
    public static double f_x(double x){
        return -x;
        //return ((double) x-Math.pow(x,3));
    }
    public static double t_k_plus_one(double t_k,double delta){
        return t_k+delta;
    }
    public static double x_k_plus_one(double x_k, double delta){
        //System.out.println(x_k+" "+delta+" "+f_x(x_k));

        return x_k+delta*f_x(x_k);
    }
    public static double original_function(double t){
        return (Math.exp(t))/(Math.sqrt(-3/4+Math.exp(2*t)));
    }
    public static void eulers_method(double delta_t1,double x_temp, double t_temp, int no_of_steps){
        System.out.println("Step size: "+delta_t1);
        for(int i=1; i<=no_of_steps;i++){
            t_temp = t_k_plus_one(t_temp,delta_t1);
            x_temp = x_k_plus_one(x_temp,delta_t1);
            System.out.println("x"+i+"("+t_temp+")"+" = "+x_temp);
        }
        System.out.println("Original solution: x("+ t_temp+") ="+original_function(t_temp));
        System.out.println(" ");
        Double temp = (x_temp);
        if(temp.isNaN()) {
            System.out.println("Solution does not appear to be converging to an equilibrium");
        }
        else{
            System.out.println("x appears to be converging to an equilibrium of "+x_temp);
        }
        System.out.println(Math.exp(-1)-x_temp);

        System.out.println("End of Question");
        System.out.println("__________________________________________________");
        System.out.println(" ");
    }

    public static void runge_katta(double delta_t1,double x_temp, double t_temp, int no_of_steps){
        double k1,k2,k3,k4;
        System.out.println("Step size: "+delta_t1);
        double xtemp1 = x_temp;
        for(int i=1; i<=no_of_steps;i++){
            k1 = f_x(xtemp1)*delta_t1;
            k2 = f_x(xtemp1+0.5*k1)*delta_t1;
            k3 = f_x(xtemp1+0.5*k2)*delta_t1;
            k4 = f_x(xtemp1+k3)*delta_t1;
            xtemp1 = xtemp1+((double) 1/6)*(k1+2*k2+2*k3+k4);
            t_temp = t_k_plus_one(t_temp,delta_t1);
            System.out.println("k1: "+k1+", k2: "+k2+", k3: "+k3+", k4: "+k4);
            System.out.println("x"+i+"("+t_temp+")"+" = "+xtemp1);
        }
        System.out.println(Math.exp(-1)-xtemp1);
        System.out.println(" ");
        Double temp = (xtemp1);
        if(temp.isNaN()) {
            System.out.println("Solution does not appear to be converging to an equilibrium");
        }
        else{
            System.out.println("x appears to be converging to an equilibrium of "+x_temp);
        }

        System.out.println("End of Question");
        System.out.println("__________________________________________________");
        System.out.println(" ");
    }

    public static void improved_euler(double delta_t1,double x_temp, double t_temp, int no_of_steps){
        for(int i=1; i<=no_of_steps;i++){
            t_temp = t_k_plus_one(t_temp,delta_t1);
            double x_bar = x_temp+delta_t1*f_x(x_temp);
            //System.out.println(x_bar+" "+x_temp);
            x_temp = x_temp+0.5*(f_x(x_temp)+f_x(x_bar))*delta_t1;
            System.out.println("x"+i+"("+t_temp+")"+" = "+x_temp);
        }
        System.out.println(Math.exp(-1)-x_temp);
    }
}
