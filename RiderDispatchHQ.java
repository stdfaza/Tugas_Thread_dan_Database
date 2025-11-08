import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RiderDispatchHQ {

    public static void main(String[] args) {
        ExecutorService riderPool = Executors.newFixedThreadPool(5);
        
        System.out.println("--- MARKAS PUSAT: Mengirim 20 Misi (TANPA POOL) ---");

        for (int i = 1; i <= 20; i++) {
            Runnable mission = new RiderMissionTask(i);
            riderPool.submit(mission);
            
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {}
        }

        riderPool.shutdown();

        try {
            if (!riderPool.awaitTermination(1, TimeUnit.MINUTES)) {
                riderPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            riderPool.shutdownNow();
        }

        System.out.println("--- MARKAS PUSAT: Semua Misi Selesai. ---");
    }
}
