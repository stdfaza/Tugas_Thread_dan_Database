# Tugas_Thread_dan_Database
Ngetes Lewat linux njir

# Proyek Thread dan Database Java (Kamen Rider)

Ini adalah contoh program Java sederhana yang mendemonstrasikan **Multithreading** yang berinteraksi dengan **Database SQL** (MariaDB/MySQL).

Program ini mensimulasikan "Markas Pusat" yang mengirim 20 "Misi" ke 5 "Rider" (Threads) yang tersedia. Setiap Rider yang menerima misi harus mencatat laporan misinya ke database.

## Isi File

Ada 2 file utama:

1.  **`RiderDispatchHQ.java`**
    * Ini adalah "Manajer" atau Markas Pusat.
    * Tugasnya adalah membuat **`ExecutorService`** (sebuah *Thread Pool*) dengan 5 thread (Rider yang siaga).
    * Ia kemudian membuat 20 tugas (`RiderMissionTask`) dan memberikannya ke *thread pool* untuk dieksekusi.

2.  **`RiderMissionTask.java`**
    * Ini adalah "Pekerja" atau Misi itu sendiri.
    * File ini mengimplementasikan `Runnable`, yang berarti bisa dijalankan oleh sebuah thread.
    * **Di sinilah koneksi Database terjadi.**
    * Setiap kali sebuah thread menjalankan tugas ini, ia akan:
        1.  Membuat **koneksi database BARU** ke `localhost` menggunakan `DriverManager.getConnection()`.
        2.  Menyiapkan perintah SQL (`INSERT`).
        3.  Menjalankan perintah SQL untuk mencatat laporan misi.
        4.  Menutup koneksi database (secara otomatis berkat `try-with-resources`).

## Catatan Penting: Tanpa Connection Pool

Contoh ini sengaja dibuat sederhana **tanpa library eksternal** seperti HikariCP (Connection Pool).

* **Apa artinya?** Setiap thread (dari 20 tugas) harus membuat koneksi baru ke database. Membuat koneksi adalah proses yang **lambat** (membutuhkan otentikasi, alokasi memori, dll).
* **Di dunia nyata:** Ini adalah praktik yang buruk dan tidak efisien. Seharusnya, kita menggunakan **Connection Pool** di mana 5 koneksi disiapkan di awal dan para thread tinggal "meminjam" koneksi yang ada, lalu "mengembalikannya" saat selesai.

Namun, untuk tujuan tugas yang hanya menunjukkan "Thread" + "SQL", kode ini sudah valid.

## Cara Menjalankan

Proyek ini tidak menggunakan Maven, jadi harus dijalankan secara manual.

**1. Siapkan Database Anda**

Pastikan database `heisei_riders`, tabel `mission_logs`, dan user `heisei_user` sudah ada di MariaDB `localhost` Anda.

**2. Download Driver JDBC**

Program ini membutuhkan driver agar Java bisa "berbicara" dengan MariaDB.
1.  Download file `.jar` driver dari [situs resmi MariaDB](https://mariadb.com/kb/en/mariadb-connector-j-download/).
2.  Cari file seperti `mariadb-java-client-3.3.3.jar`.
3.  Letakkan file `.jar` ini di folder yang **SAMA** dengan file `.java` Anda.

**3. Compile dan Jalankan**

Buka terminal di folder proyek Anda:

```bash
# 1. Compile semua file .java menjadi .class
javac .java

# 2. Jalankan file utama
# Kita harus memberitahu Java di mana driver-nya (-cp)
# "."  = folder saat ini (untuk file .class)
# ":"  = pemisah
# "..." = nama file .jar driver
java -cp ".:mariadb-java-client-3.3.3.jar" RiderDispatchHQ
