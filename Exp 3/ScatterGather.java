import mpi.*;

public class ScatterGather {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Can be any size

        int totalLength = array.length;
        int chunkSize = totalLength / size;
        int remainder = totalLength % size;

        // Compute start and end indices for each process
        int startIndex = rank * chunkSize + Math.min(rank, remainder);
        int endIndex = startIndex + chunkSize + (rank < remainder ? 1 : 0);

        int localSum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            localSum += array[i];
        }

        System.out.println("Process " + rank + " intermediate sum: " + localSum);

        int[] recvBuffer = new int[1];
        MPI.COMM_WORLD.Reduce(new int[]{localSum}, 0, recvBuffer, 0, 1, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            System.out.println("Final sum: " + recvBuffer[0]);
        }

        MPI.Finalize();
    }
}
