import java.lang.Math;

public class BlackScholes {
    // Function to calculate the cumulative distribution function of
    // the standard normal distribution
    public static double normCDF(double x) {
        double a1 = 0.31938153;
        double a2 = -0.356563782;
        double a3 = 1.781477937;
        double a4 = -1.821255978;
        double a5 = 1.330274429;
        double L;
        double K;
        double w;
	 
        L = Math.abs(x);
        K = 1.0 / (1.0 + 0.2316419 * L);
        w = 1.0 - 1.0 / Math.sqrt(2*Math.PI)*Math.exp(-L*L/2.) *
                (a1*K + a2*K*K + a3*Math.pow(K,3) +
                a4*Math.pow(K,4) + a5*Math.pow(K,5));
    
        if (x<0)
            w = 1.0-w;

        return w;
    }

    // Function to calculate the Black-Scholes option price
    public static double blackScholes(double S, double K, double r,
            double sigma, double T, boolean isCall) {
        
        double d1 = (Math.log(S / K) + (r + 0.5 * sigma * sigma) * T) /
                (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);

        if (isCall) {
            // Call option price
            return S * normCDF(d1) - K * Math.exp(-r * T) * normCDF(d2);
        } else {
            // Put option price
            return K * Math.exp(-r * T) * normCDF(-d2) - S * normCDF(-d1);
        }
    }

    public static void main(String[] args) {
        // Example parameters
        double S = 100.0;   // Current stock price
        double K = 100.0;   // Strike price
        double r = 0.05;    // Risk-free interest rate
        double sigma = 0.2; // Volatility of the stock
        double T = 0.25;    // Time to expiration in years

        // Calculate call and put option prices
        double callPrice = blackScholes(S, K, r, sigma, T, true);
        double putPrice  = blackScholes(S, K, r, sigma, T, false);

        // Output the results
        System.out.printf("Call Option Price: %.2f%n", callPrice);
        System.out.printf("Put Option Price: %.2f%n", putPrice);
    }
}

