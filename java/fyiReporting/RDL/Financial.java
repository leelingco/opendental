//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;


/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General public License for more details.
    You should have received a copy of the GNU Lesser General public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* The Financial class holds a number of static functions relating to financial
* calculations.
* 
* Note: many of the financial functions use the following function as their basis
* pv*(1+rate)^nper+pmt*(1+rate*type)*((1+rate)^nper-1)/rate)+fv=0
* if rate = 0
* (pmt*nper)+pv+fv=0
*/
final public class Financial   
{
    /**
    * Double declining balance depreciation (when factor = 2).  Other factors may be specified.
    * 
    *  @param cost Initial cost of asset
    *  @param salvage Salvage value of asset at end of depreciation
    *  @param life Number of periods over which to depreciate the asset.  AKA useful life
    *  @param period The period for which you want to know the depreciation amount.
    *  @return
    */
    static public double dDB(double cost, double salvage, int life, int period) throws Exception {
        return DDB(cost, salvage, life, period, 2);
    }

    /**
    * Double declining balance depreciation (when factor = 2).  Other factors may be specified.
    * 
    *  @param cost Initial cost of asset
    *  @param salvage Salvage value of asset at end of depreciation
    *  @param life Number of periods over which to depreciate the asset.  AKA useful life
    *  @param period The period for which you want to know the depreciation amount.
    *  @param factor The rate at which the balance declines.  Defaults to 2 (double declining) when omitted.
    *  @return
    */
    static public double dDB(double cost, double salvage, int life, int period, double factor) throws Exception {
        if (period > life || period < 1 || life < 1)
            return double.NaN;
         
        // invalid arguments
        double depreciation = 0;
        for (int i = 1;i < period;i++)
        {
            depreciation += (cost - depreciation) * factor / life;
        }
        if (period == life)
            return cost - salvage - depreciation;
        else
            return (cost - depreciation) * factor / life; 
    }

    // for last year we force the depreciation so that cost - total depreciation = salvage
    /**
    * Returns the future value of an investment when using periodic, constant payments and
    * constant interest rate.
    * 
    *  @param rate Interest rate per period
    *  @param periods Total number of payment periods
    *  @param pmt Amount of payment each period
    *  @param presentValue Lump sum amount that a series of payments is worth now
    *  @param endOfPeriod Specify true if payments are due at end of period, otherwise false
    *  @return
    */
    static public double fV(double rate, int periods, double pmt, double presentValue, boolean endOfPeriod) throws Exception {
        int type = endOfPeriod ? 0 : 1;
        double fv = new double();
        if (rate == 0)
            fv = -(pmt * periods + presentValue);
        else
        {
            double temp = Math.Pow(1 + rate, periods);
            fv = -(presentValue * temp + pmt * (1 + rate * type) * ((temp - 1) / rate));
        } 
        return fv;
    }

    /**
    * Returns the interest payment portion of a payment given a particular period.
    * 
    *  @param rate Interest rate per period
    *  @param period Period for which you want the interest payment.
    *  @param periods Total number of payment periods
    *  @param presentValue Lump sum amount that a series of payments is worth now
    *  @param futureValue Cash balance you want to attain after last payment
    *  @param endOfPeriod Specify true if payments are due at end of period, otherwise false
    *  @return
    */
    static public double iPmt(double rate, int period, int periods, double presentValue, double futureValue, boolean endOfPeriod) throws Exception {
        // TODO -- routine needs more work.   off when endOfPeriod is false; must be more direct way to solve
        if (!endOfPeriod)
            throw new Exception("IPmt doesn't support payments due at beginning of period.");
         
        if (period < 0 || period > periods)
            return double.NaN;
         
        if (!endOfPeriod)
            period--;
         
        double pmt = -pmt(rate,periods,presentValue,futureValue,endOfPeriod);
        double prin = presentValue;
        double interest = 0;
        for (int i = 0;i < period;i++)
        {
            interest = rate * prin;
            prin = prin - pmt + interest;
        }
        return -interest;
    }

    /**
    * Returns the number of periods for an investment.
    * 
    *  @param rate Interest rate per period
    *  @param pmt Amount of payment each period
    *  @param presentValue Lump sum amount that a series of payments is worth now
    *  @param futureValue Future value or cash balance you want after last payment
    *  @param endOfPeriod Specify true if payments are due at end of period, otherwise false
    *  @return
    */
    static public double nPer(double rate, double pmt, double presentValue, double futureValue, boolean endOfPeriod) throws Exception {
        if (Math.Abs(pmt) < double.Epsilon)
            return double.NaN;
         
        int type = endOfPeriod ? 0 : 1;
        double nper = new double();
        if (rate == 0)
            nper = -(presentValue + futureValue) / pmt;
        else
        {
            double r1 = 1 + rate * type;
            double pmtr1 = pmt * r1 / rate;
            double y = (pmtr1 - futureValue) / (presentValue + pmtr1);
            nper = Math.Log(y) / Math.Log(1 + rate);
        } 
        return nper;
    }

    /**
    * Returns the periodic payment for an annuity using constant payments and
    * constant interest rate.
    * 
    *  @param rate Interest rate per period
    *  @param periods Total number of payment periods
    *  @param presentValue Lump sum amount that a series of payments is worth now
    *  @param futureValue Cash balance you want to attain after last payment
    *  @param endOfPeriod Specify true if payments are due at end of period, otherwise false
    *  @return
    */
    static public double pmt(double rate, int periods, double presentValue, double futureValue, boolean endOfPeriod) throws Exception {
        if (periods < 1)
            return double.NaN;
         
        int type = endOfPeriod ? 0 : 1;
        double pmt = new double();
        if (rate == 0)
            pmt = -(presentValue + futureValue) / periods;
        else
        {
            double temp = Math.Pow(1 + rate, periods);
            pmt = -(presentValue * temp + futureValue) / ((1 + rate * type) * (temp - 1) / rate);
        } 
        return pmt;
    }

    /**
    * Returns the present value of an investment.  The present value is the total
    * amount that a series of future payments is worth now.
    * 
    *  @param rate Interest rate per period
    *  @param periods Total number of payment periods
    *  @param pmt Amount of payment each period
    *  @param futureValue Cash balance you want to attain after last payment is made
    *  @param endOfPeriod Specify true if payments are due at end of period, otherwise false
    *  @return
    */
    static public double pV(double rate, int periods, double pmt, double futureValue, boolean endOfPeriod) throws Exception {
        int type = endOfPeriod ? 0 : 1;
        double pv = new double();
        if (rate == 0)
            pv = -(pmt * periods + futureValue);
        else
        {
            double temp = Math.Pow(1 + rate, periods);
            pv = -(pmt * (1 + rate * type) * ((temp - 1) / rate) + futureValue) / temp;
        } 
        return pv;
    }

    /**
    * Returns the interest rate per period for an annuity.  This routine uses an
    * iterative approach to solving for rate.   If after 30 iterations the answer
    * doesn't converge to within 0.0000001 then double.NAN is returned.
    * 
    *  @param periods Total number of payment periods
    *  @param pmt Amount of payment each period
    *  @param presentValue Lump sum amount that a series of payments is worth now
    *  @param futureValue Cash balance you want to attain after last payment
    *  @param endOfPeriod Specify true if payments are due at end of period, otherwise false
    *  @param guess Your guess as to what the interest rate will be.
    *  @return
    */
    static public double rate(int periods, double pmt, double presentValue, double futureValue, boolean endOfPeriod, double guess) throws Exception {
        // TODO:  should be better algorithm: linear interpolation, Newton-Raphson approximation???
        int type = endOfPeriod ? 0 : 1;
        // Set the lower bound
        double gLower = guess > .1 ? guess - .1 : 0;
        double power2 = .1;
        while (rateGuess(periods,pmt,presentValue,futureValue,type,gLower) > 0)
        {
            gLower -= power2;
            power2 *= 2;
        }
        // Set the upper bound
        double gUpper = guess + .1;
        power2 = .1;
        while (rateGuess(periods,pmt,presentValue,futureValue,type,gUpper) < 0)
        {
            gUpper += power2;
            power2 *= 2;
        }
        double z = new double();
        double incr = new double();
        double newguess = new double();
        for (int i = 0;i < 30;i++)
        {
            z = rateGuess(periods,pmt,presentValue,futureValue,type,guess);
            if (z > 0)
            {
                gUpper = guess;
                incr = (guess - gLower) / 2;
                newguess = guess - incr;
            }
            else
            {
                gLower = guess;
                incr = (gUpper - guess) / 2;
                newguess = guess + incr;
            } 
            if (incr < 0.0000001)
                return guess;
             
            // Is the difference within our margin of error?
            guess = newguess;
        }
        return double.NaN;
    }

    // we didn't converge
    static private double rateGuess(int periods, double pmt, double pv, double fv, int type, double rate) throws Exception {
        // When the guess is close the result should almost be 0
        if (rate == 0)
            return pmt * periods + pv + fv;
         
        double temp = Math.Pow(1 + rate, periods);
        return pv * temp + pmt * (1 + rate * type) * ((temp - 1) / rate) + fv;
    }

    /**
    * SLN returns the straight line depreciation for an asset for a single period.
    * 
    *  @param cost Initial cost of asset
    *  @param salvage Salvage value of asset at end of depreciation
    *  @param life Number of periods over which to depreciate the asset. AKA useful life
    *  @return
    */
    static public double sLN(double cost, double salvage, double life) throws Exception {
        if (life == 0)
            return double.NaN;
         
        return (cost - salvage) / life;
    }

    /**
    * Sum of years digits depreciation.  An asset often loses more of its value early in its lifetime.
    * SYD has this behavior.
    * 
    *  @param cost Initial cost of asset
    *  @param salvage Salvage value of asset at end of depreciation
    *  @param life Number of periods over which to depreciate the asset.  AKA useful life
    *  @param period The period for which you want to know the depreciation amount.
    *  @return
    */
    static public double sYD(double cost, double salvage, int life, int period) throws Exception {
        int sumOfPeriods = new int();
        if (life % 2 == 0)
            // sum = n/2 * (n+1) when even
            sumOfPeriods = life / 2 * (life + 1);
        else
            // sum = (n+1)/2 * n when odd
            sumOfPeriods = (life + 1) / 2 * life; 
        return ((life + 1 - period) * (cost - salvage)) / sumOfPeriods;
    }

}


