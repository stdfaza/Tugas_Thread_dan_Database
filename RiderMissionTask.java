import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class RiderMissionTask implements Runnable {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/heisei_riders";
    private static final String USER = "heisei_user";
    private static final String PASS = "riderkick";

    private static final String[] HEISEI_RIDERS = {
        "Kuuga", "Agito", "Ryuki", "Faiz", "Blade", "Hibiki", "Kabuto", 
        "Den-O", "Kiva", "Decade", "W", "OOO", "Fourze", "Wizard", 
        "Gaim", "Drive", "Ghost", "Ex-Aid", "Build", "Zi-O"
    };

    private static final Random rand = new Random();
    private int missionId;

    public RiderMissionTask(int missionId) {
        this.missionId = missionId;
    }

    @Override
    public void run() {
        String rider = HEISEI_RIDERS[rand.nextInt(HEISEI_RIDERS.length)];
        String threadName = Thread.currentThread().getName();
        String riderInCharge = threadName + " (" + rider + ")";
        String report = "Menjalankan Misi #" + this.missionId + " (Status: Selesai)";

        System.out.println(riderInCharge + " memulai misi...");

        String sql = "INSERT INTO mission_logs (rider_in_charge, mission_report) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, riderInCharge);
            pstmt.setString(2, report);
            pstmt.executeUpdate();

            System.out.println("✅ " + riderInCharge + " BERHASIL mengirim laporan.");

        } catch (Exception e) {
            System.err.println("❌ " + riderInCharge + " GAGAL: " + e.getMessage());
        }
    }
}
