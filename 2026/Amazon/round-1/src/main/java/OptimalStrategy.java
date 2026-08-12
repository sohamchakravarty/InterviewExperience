public class OptimalStrategy {
    public int getMaxAmount(int[] arr) {
        int i=0, j=arr.length - 1;
        boolean isTurnOfFirstPlayer = true;

        int playerAValue = 0;
        while(i < j) {
            int optimalCoinAmount = isLeftCoinSelected(arr, i, j) ? arr[i++] : arr[j--];
            
            if (isTurnOfFirstPlayer) {
                playerAValue += optimalCoinAmount;
            }

            isTurnOfFirstPlayer = !isTurnOfFirstPlayer;
        }

        return playerAValue;
    }

    private boolean isLeftCoinSelected(int[] arr, int i, int j) {
        // Base Case: If length is less than 4
        if (j - i < 3) {
            return arr[i] > arr[j];
        }

        /*
         * if (max (a+d,b+d,a+c) == a+d)
                select max(a,d)
            else
                if (max(a+c, d+b) == a+c)
                    select a
                else
                    select d
         */
        int value1 = arr[i] + arr[j];   // a+d
        int value2 = arr[i+1] + arr[j]; // b+d
        int value3 = arr[i] + arr[j-1]; // a+c

        if ( Math.max(value1, Math.max(value2, value3)) == value1) {
            return arr[i] > arr[j];
        }
        
        return Math.max(value2, value3) == value3;
    }
}