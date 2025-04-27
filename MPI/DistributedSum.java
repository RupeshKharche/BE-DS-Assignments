import java.util.Arrays;

import mpi.MPI;

public class DistributedSum {
    public static void main(String[] args) throws Exception {
        // Initialize MPJ
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        // System.out.println(Arrays.toString(args));

        // Skip MPJ-specific arguments (e.g., "smpdev")
        // In multicore mode, user arguments start after MPJ arguments
        int mpjArgCount = 3;
        if (args.length <= mpjArgCount) {
            if (rank == 0) {
                System.err.println("Error: No array elements provided. Usage: mpjrun.sh -np <num> DistributedSum <num1> <num2> ...");
            }
            MPI.Finalize();
            return;
        }

        // Parse user-provided arguments into an array
        int n = args.length - mpjArgCount; // Number of actual array elements
        int[] array = new int[n];
        try {
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(args[i + mpjArgCount]);
            }
        } catch (NumberFormatException e) {
            if (rank == 0) {
                System.err.println("Error: Invalid number format in arguments.");
            }
            MPI.Finalize();
            return;
        }

        // Calculate the chunk size for each process
        int chunkSize = n / size;
        int start = rank * chunkSize;
        int end = (rank == size - 1) ? n : start + chunkSize; // Last process handles remaining elements

        // Compute the partial sum for this process's chunk
        int partialSum = 0;
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }

        System.out.println("Received partial array at rank " + rank + ": " + Arrays.toString(Arrays.copyOfRange(array, start, end)) + ", partial sum = " + partialSum);

        // Reduce all partial sums to get the total sum (sent to rank 0)
        int[] totalSum = new int[1];
        MPI.COMM_WORLD.Reduce(new int[]{partialSum}, 0, totalSum, 0, 1, MPI.INT, MPI.SUM, 0);

        // Master process (rank 0) prints the result
        if (rank == 0) {
            System.out.println("Total sum: " + totalSum[0]);
        }

        MPI.Finalize();
    }
}