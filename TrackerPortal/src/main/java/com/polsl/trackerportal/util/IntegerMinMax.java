/* 
   (c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
       1:1 Realny obraz Twojej firmy
*/

package com.polsl.trackerportal.util;

/**
 *
 * @author Damian Badura damian.badura@spiid.pl
 * @version 1.0
 * @since 
 */
public class IntegerMinMax {

        private Integer min;
        private Integer max;

        public IntegerMinMax(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        /**
         * @return the min
         */
        public Integer getMin() {
            return min;
        }

        /**
         * @return the max
         */
        public Integer getMax() {
            return max;
        }

        /**
         * @param min the min to set
         */
        public void setMin(Integer min) {
            this.min = min;
        }

        /**
         * @param max the max to set
         */
        public void setMax(Integer max) {
            this.max = max;
        }
    }