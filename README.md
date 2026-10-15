# Sistem Manajemen Salon

## Identitas Mahasiswa

**Nama:** ADELIA GITHA NAVEEZHA HERMAWAN

**NIM:** 2509116110

## 1. Deskripsi Singkat Program

Sistem Manajemen Salon merupakan program aplikasi berbasis konsol (Command Line Interface) yang dibuat menggunakan bahasa pemrograman Java dengan menerapkan prinsip Pemrograman Berorientasi Objek. Program ini merupakan pengembangan lanjutan dari Mini Project 3.

Sebuah salon kecantikan setiap harinya melayani banyak pelanggan dengan berbagai jenis perawatan, mulai dari potong rambut, creambath, smoothing, facial, hingga manicure pedicure. Apabila seluruh data tersebut dicatat secara manual, pengelola salon akan kesulitan ketika ingin mencari, mengubah, maupun menghapus data tertentu.

Terdapat tiga data utama yang dikelola oleh program ini. Data pertama adalah data pelanggan yang berisi ID pelanggan, nama, dan nomor telepon. Data kedua adalah data layanan yang berisi ID layanan, nama layanan, kategori, harga, serta keterangan tambahan sesuai kategorinya. Data ketiga adalah data reservasi yang berisi ID reservasi, pelanggan yang memesan, layanan yang dipesan, dan tanggal reservasi.

Perbedaan terbesar dibandingkan versi sebelumnya terletak pada tempat penyimpanan datanya. Pada Mini Project 3, seluruh data disimpan di dalam ArrayList yang berada di memori, sehingga setiap kali program ditutup seluruh data hilang dan harus diisi ulang dari awal. Pada Mini Project 4 ini, seluruh data dipindahkan ke dalam database MySQL dan diakses menggunakan JDBC dengan PreparedStatement melalui pola DAO. Dengan demikian data yang ditambahkan, diubah, maupun dihapus melalui program akan tetap tersimpan walaupun program dimatikan dan dijalankan ulang.

## 2. Pengembangan dari Mini Project 3

Pada Mini Project 4 ini terdapat lima pengembangan utama dibandingkan versi sebelumnya.

Pengembangan pertama adalah penggantian ArrayList menjadi database MySQL. Seluruh data pelanggan, layanan, dan reservasi kini disimpan di dalam tiga tabel pada database bernama db_salon.

Pengembangan kedua adalah penambahan package config yang berisi class Koneksi. Class ini bertugas membuka jembatan antara program Java dengan database menggunakan JDBC.

Pengembangan ketiga adalah penerapan pola DAO. Seluruh perintah SQL dikumpulkan ke dalam tiga class khusus, yaitu PelangganDAO, LayananDAO, dan ReservasiDAO, dan seluruh perintah tersebut ditulis menggunakan PreparedStatement.

Pengembangan keempat adalah pemindahan dummy data. Method isiDataAwal beserta class DataSalon yang sebelumnya menyimpan seluruh ArrayList kini dihapus seluruhnya, karena tugas penyimpanan data sudah diambil alih oleh database. Data awal sekarang diisi melalui file db_salon.sql.

Pengembangan kelima adalah penerapan framework ORM Hibernate sebagai nilai tambah, yang ditambahkan sebagai menu tersendiri agar perbedaannya dengan cara JDBC manual terlihat jelas.

## 3. Penjelasan Struktur Package

Program ini dibagi ke dalam enam package sesuai perannya masing-masing.

**Package config** berisi dua class, yaitu Koneksi dan TestKoneksi. Class Koneksi bertugas membuka koneksi ke database dan menyimpannya agar dapat dipakai berulang kali oleh seluruh bagian program. Class TestKoneksi merupakan class bantuan yang hanya dipakai untuk memastikan koneksi sudah berhasil sebelum program utama dijalankan.

**Package model** berisi delapan berkas yang terbagi menjadi dua kelompok. Kelompok pertama adalah lima class cetak biru data, yaitu Layanan, LayananRambut, LayananKecantikan, Pelanggan, dan Reservasi, yang isinya hanya atribut, constructor, serta getter dan setter. Kelompok kedua adalah tiga class DAO, yaitu PelangganDAO, LayananDAO, dan ReservasiDAO, yang berisi seluruh perintah SQL untuk berhubungan dengan database.

**Package view** berisi dua class, yaitu MenuView dan InputView. MenuView bertugas menampilkan tulisan ke layar, mulai dari menu utama, sub menu, judul, data, sampai pesan. Sedangkan InputView bertugas membaca masukan dari pengguna sekaligus memeriksa kebenarannya.

**Package controller** berisi empat berkas, yaitu interface KelolaData beserta tiga class PelangganController, LayananController, dan ReservasiController. Controller menerima pilihan dari pengguna melalui view, lalu meminta data kepada DAO, kemudian mengirimkan hasilnya kembali ke view untuk ditampilkan.

**Package orm** berisi tiga berkas yang dipakai untuk nilai tambah, yaitu PelangganORM, HibernateUtil, dan PelangganORMController.

**Package main** berisi satu class bernama Main yang merupakan titik awal program dijalankan.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/339e6bf5-91fc-4985-af72-d486dc0c0570" />

Alasan DAO diletakkan di dalam package model adalah karena DAO merupakan bagian dari urusan data, bukan urusan tampilan maupun alur program. Dengan begitu, apabila suatu saat database diganti dari MySQL ke database lain, yang perlu diubah hanya isi class DAO dan class Koneksi saja, sedangkan controller, view, dan Main tidak perlu disentuh sama sekali.

## 4. Perancangan Database

Database yang digunakan bernama db_salon dan terdiri dari tiga tabel.

Tabel pertama bernama pelanggan dengan kolom id_pelanggan sebagai primary key yang bersifat AUTO_INCREMENT, kolom nama, dan kolom no_telepon. Nomor telepon disimpan sebagai teks dan bukan angka, karena nomor telepon diawali angka nol yang akan hilang apabila disimpan sebagai angka.

Tabel kedua bernama layanan dengan kolom id_layanan sebagai primary key yang bersifat AUTO_INCREMENT, kolom nama_layanan, kolom harga, kolom kategori, kolom panjang_rambut, dan kolom durasi_menit. Dua kolom terakhir sengaja dibuat boleh kosong, karena layanan kategori rambut hanya mengisi kolom panjang_rambut sedangkan layanan kategori kecantikan hanya mengisi kolom durasi_menit. Kolom kategori inilah yang nantinya dipakai program untuk menentukan objek turunan mana yang harus dibuat ketika data dibaca kembali dari database.

Tabel ketiga bernama reservasi dengan kolom id_reservasi sebagai primary key yang bersifat AUTO_INCREMENT, kolom id_pelanggan, kolom id_layanan, dan kolom tanggal. Kolom id_pelanggan dan id_layanan merupakan foreign key yang menunjuk ke tabel pelanggan dan tabel layanan.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/77350bc6-f873-4f96-952d-251d9d606d40" />

Pada kedua foreign key tersebut ditambahkan aturan ON DELETE CASCADE. Artinya, apabila sebuah data pelanggan atau data layanan dihapus, maka seluruh data reservasi yang menunjuk ke data tersebut akan ikut terhapus secara otomatis oleh database. Aturan ini merupakan pengganti dari method hapusReservasiMilikPelanggan dan hapusReservasiMemakaiLayanan yang pada Mini Project 3 masih dikerjakan secara manual di dalam class DataSalon.

Penggunaan AUTO_INCREMENT juga membuat program tidak perlu lagi menyimpan penghitung ID seperti pada versi sebelumnya, karena pembuatan ID baru sepenuhnya diserahkan kepada database sehingga tidak mungkin terjadi ID ganda.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/dbffc19e-eba0-412b-b7ae-3d431996bfe1" />


## 5. Koneksi ke Database Menggunakan JDBC

JDBC merupakan penghubung standar milik Java yang memungkinkan program Java berbicara dengan berbagai jenis database tanpa harus mengubah cara penulisan kode Java secara drastis. Agar JDBC dapat terhubung ke MySQL, dibutuhkan sebuah driver yang dipasang melalui berkas pom.xml dengan menambahkan dependency mysql-connector-j.

Proses koneksinya sendiri ditangani oleh class Koneksi di dalam package config. Di dalam class tersebut terdapat tiga keterangan utama, yaitu URL yang berisi alamat database, USER yang berisi nama pengguna database, dan PASS yang berisi kata sandinya.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/059f337a-35cf-46e0-87a6-876bf4ec33f2" />

Method getConnection pada class tersebut tidak langsung membuat koneksi baru setiap kali dipanggil. Program terlebih dahulu memeriksa apakah koneksi yang lama masih hidup. Apabila koneksi masih tersedia, koneksi yang sama dipakai kembali, dan koneksi baru hanya dibuat apabila belum ada atau sudah terputus. Alasannya adalah agar program tidak membuka puluhan koneksi sekaligus ketika pengguna bolak-balik membuka menu, karena setiap koneksi memakan sumber daya pada database.

Seluruh proses koneksi dibungkus di dalam try catch yang menangkap SQLException. Dengan begitu, apabila database sedang mati atau nama databasenya salah, program tidak berhenti secara paksa melainkan hanya menampilkan pesan kesalahan.

Sebelum program utama dijalankan, koneksi diuji terlebih dahulu melalui class TestKoneksi yang hanya berisi pemeriksaan apakah koneksi berhasil dibuat atau tidak.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/100e41ea-3dfd-4158-a9ca-935903d66e07" />

## 6. Penerapan PreparedStatement

PreparedStatement adalah objek untuk menjalankan perintah SQL yang nilainya tidak digabung langsung ke dalam teks query, melainkan diisi melalui tanda tanya sebagai tempat kosong. Seluruh perintah SQL pada program ini, tanpa terkecuali, ditulis menggunakan PreparedStatement.

Cara kerjanya dapat dilihat pada method tambah di dalam class PelangganDAO. Perintah SQL ditulis terlebih dahulu dalam bentuk INSERT INTO pelanggan dengan dua tanda tanya sebagai tempat kosong. Setelah itu tempat kosong tersebut diisi satu per satu menggunakan method setString, barulah perintahnya dijalankan menggunakan executeUpdate.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/58d79985-8803-45b6-a453-a2209332f914" />

Alasan pertama penggunaan PreparedStatement adalah keamanan dari SQL Injection. Apabila nilai digabung langsung ke dalam teks query, pengguna dapat mengetikkan nama yang di dalamnya mengandung potongan perintah SQL, dan potongan tersebut akan ikut dijalankan oleh database. Dengan PreparedStatement, apa pun yang diketik pengguna tetap dianggap sebagai data biasa dan tidak pernah dianggap sebagai bagian dari perintah.

Alasan kedua adalah kerapian. Perintah SQL menjadi utuh dan mudah dibaca karena tidak terpotong-potong oleh tanda petik dan tanda tambah seperti pada penggabungan teks biasa.

Alasan ketiga adalah kecepatan ketika perintah yang sama dijalankan berulang kali, karena query hanya perlu disiapkan satu kali lalu dapat dijalankan berkali-kali dengan nilai yang berbeda.

Pada program ini terdapat dua jenis cara menjalankan perintah. Perintah SELECT dijalankan menggunakan executeQuery yang hasilnya ditampung ke dalam ResultSet untuk dibaca baris demi baris, sedangkan perintah INSERT, UPDATE, dan DELETE dijalankan menggunakan executeUpdate.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/72238e0f-84bf-42d6-9554-ccc65e6dc377" />

Seluruh PreparedStatement pada program ini ditulis di dalam kurung setelah kata try, sehingga Java akan menutup kembali statement tersebut secara otomatis setelah selesai dipakai, baik ketika berhasil maupun ketika terjadi kesalahan.

## 7. Penerapan DAO

DAO atau Data Access Object adalah pola yang memisahkan urusan akses database dari urusan logika program. Pada program ini terdapat tiga class DAO, dan ketiganya merupakan satu-satunya tempat di seluruh program yang boleh berisi perintah SQL.

Class PelangganDAO memiliki lima method utama. Method getSemua menjalankan perintah SELECT untuk seluruh data pelanggan lalu mengubah setiap baris hasilnya menjadi objek Pelanggan yang dikumpulkan ke dalam ArrayList. Method cariById menjalankan SELECT dengan syarat ID tertentu dan mengembalikan satu objek Pelanggan, atau mengembalikan null apabila ID tersebut tidak ada. Method tambah menjalankan INSERT, method ubah menjalankan UPDATE, dan method hapus menjalankan DELETE.

Class LayananDAO memiliki method yang serupa, hanya saja pada saat membaca data terdapat pemeriksaan terhadap kolom kategori. Apabila kategorinya berisi teks Rambut, program membuat objek LayananRambut, sedangkan apabila berisi teks lainnya program membuat objek LayananKecantikan. Dengan cara ini, data yang tadinya hanya berupa baris pada tabel dapat kembali menjadi objek turunan yang sesuai, sehingga seluruh konsep abstraction dan overriding dari Mini Project 3 tetap bekerja seperti semula.

Class ReservasiDAO berbeda dari kedua DAO lainnya karena satu baris reservasi membutuhkan data dari tiga tabel sekaligus. Oleh karena itu perintah SELECT pada class ini menggunakan JOIN untuk menggabungkan tabel reservasi dengan tabel pelanggan dan tabel layanan dalam satu perintah. Setelah hasilnya didapat, program membuat objek Pelanggan dan objek Layanan terlebih dahulu, baru kemudian keduanya dimasukkan ke dalam objek Reservasi.

Alasan pertama DAO diterapkan adalah agar perintah SQL tidak tersebar di mana-mana. Apabila suatu saat nama kolom pada database diubah, yang perlu diperiksa cukup tiga berkas DAO saja, bukan seluruh berkas program.

Alasan kedua adalah agar controller tidak perlu tahu sama sekali bahwa datanya berasal dari database. Bagi controller, PelangganDAO hanyalah tempat yang bisa dimintai data, persis seperti DataSalon pada Mini Project 3. Hal inilah yang membuat isi controller pada Mini Project 4 hampir tidak berubah walaupun tempat penyimpanan datanya berganti total.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/dfba27a6-fc07-4bcf-baaa-6e2a42109d44" />

Alasan ketiga adalah agar perintah SQL dapat dipakai berulang. Method getSemua milik PelangganDAO misalnya, dipanggil oleh PelangganController ketika menampilkan daftar, dan dipanggil juga oleh ReservasiController ketika memeriksa apakah daftar pelanggan masih kosong.

## 8. Penjelasan Alur Program

Bagian ini menjelaskan jalannya program dari pertama kali dijalankan sampai program berhenti, sekaligus menjadi dokumentasi hasil pengujiannya.

### 8.1 Program Mulai Dijalankan

Ketika tombol Run ditekan, program menjalankan method main yang berada di dalam class Main pada package main.

Langkah pertama, program membuat objek MenuView dan InputView. Langkah kedua, program membuat tiga objek controller beserta satu objek controller ORM. Berbeda dengan Mini Project 3, controller tidak lagi menerima titipan tempat penyimpanan data, karena masing-masing controller membuat objek DAO-nya sendiri. Koneksi ke database sendiri belum dibuka pada tahap ini, melainkan baru dibuka ketika perintah SQL pertama kali dijalankan.

Setelah seluruh objek siap, program masuk ke perulangan do-while yang menampilkan Menu Utama berisi lima pilihan, yaitu Kelola Pelanggan, Kelola Layanan, Kelola Reservasi, Tampilkan Pelanggan versi ORM, dan Keluar. Perulangan do-while dipilih karena menu harus ditampilkan minimal satu kali sebelum kondisi berhenti diperiksa.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/41ed81b1-7d92-4952-80e8-aa446a7e663c" />

### 8.2 Pengguna Memilih Menu

Pilihan pengguna dibaca melalui method inputAngka milik InputView, kemudian diproses menggunakan percabangan switch. Apabila pengguna memilih angka 1, program memanggil method jalankanMenu milik PelangganController. Angka 2 memanggil LayananController, dan angka 3 memanggil ReservasiController.

Di dalam setiap controller terdapat perulangan do-while lagi yang menampilkan sub menu berisi Tambah, Tampilkan, Ubah, Hapus, dan Kembali. Sub menu ini terus ditampilkan sampai pengguna memilih angka 5 untuk kembali ke Menu Utama.

### 8.3 Proses Tampilkan Pelanggan

Ketika pengguna memilih Tampilkan, controller memanggil method getSemua milik PelangganDAO. Method tersebut menjalankan perintah SELECT ke database, membaca hasilnya baris demi baris melalui ResultSet, lalu mengubah setiap baris menjadi objek Pelanggan. Hasilnya dikembalikan dalam bentuk ArrayList, kemudian ditelusuri menggunakan perulangan for dan ditampilkan melalui MenuView.

Pada tampilan ini terlihat lima data awal langsung muncul tanpa pengguna perlu memasukkan apa pun terlebih dahulu. Kelima data tersebut berasal dari file db_salon.sql yang dijalankan di phpMyAdmin, bukan dari kode program.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/17d17092-5d1d-4652-8683-7cdbfe54acff" />


### 8.4 Proses Tambah Pelanggan

Ketika pengguna memilih Tambah, controller meminta nama dan nomor telepon melalui InputView. Pengguna tidak perlu mengetik ID karena ID dibuat otomatis oleh database.

Setelah data terkumpul, controller membuat objek Pelanggan baru lalu menyerahkannya kepada method tambah milik PelangganDAO. Di dalam method itulah perintah INSERT dijalankan. Angka nol yang diisikan sebagai ID pada saat objek dibuat hanyalah nilai sementara dan tidak pernah ikut dikirim ke database, karena kolom ID sengaja tidak dicantumkan pada perintah INSERT agar diisi sendiri oleh AUTO_INCREMENT.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/4765db47-ae86-4813-872b-e66f1fe64f78" />


### 8.5 Pembuktian Data Tersimpan Permanen

Setelah data baru ditambahkan, program ditutup melalui menu Keluar lalu dijalankan kembali dari awal. Data yang ditambahkan tadi tetap muncul di dalam daftar.

Hal ini membuktikan data benar-benar tersimpan di dalam database dan bukan lagi di dalam memori. Pada Mini Project 3, data yang sama akan hilang seluruhnya begitu program ditutup, sehingga setiap kali program dijalankan daftar selalu kembali berisi lima data awal saja.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/7428cf45-bd35-422a-b89c-c1f9a1e98022" />

Data yang sama juga dapat dilihat langsung di dalam tabel pelanggan melalui phpMyAdmin, yang membuktikan data tersebut memang tersimpan di database dan bukan hanya tampil di layar program.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/021c8cb8-ea70-469b-ab1c-9eb207de4ef6" />


### 8.6 Proses Ubah Pelanggan

Controller terlebih dahulu menampilkan seluruh data pelanggan agar pengguna dapat melihat ID yang tersedia, kemudian meminta ID yang ingin diubah. ID tersebut dikirim ke method cariById milik PelangganDAO.

Apabila ID tidak terdaftar, method tersebut mengembalikan nilai null dan program menampilkan pesan bahwa ID tidak ditemukan tanpa mengubah data apa pun. Apabila ditemukan, pengguna diminta memasukkan nama dan nomor telepon yang baru, nilainya diganti melalui method setter, lalu objek yang sudah diperbarui tersebut diserahkan kepada method ubah untuk dijalankan perintah UPDATE-nya. ID tidak ikut diubah karena berfungsi sebagai identitas.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/f0ae73ee-1839-43f3-b649-256e26ab093d" />


### 8.7 Proses Hapus Pelanggan

Sama seperti proses ubah, program menampilkan daftar pelanggan lalu meminta ID yang ingin dihapus. Sebelum data benar-benar dihapus, program menampilkan konfirmasi terlebih dahulu beserta nama pelanggannya, dan hanya menerima jawaban y atau n.

Apabila pengguna menjawab y, controller memanggil method hapus milik PelangganDAO. Di dalam method tersebut, program terlebih dahulu menghitung ada berapa reservasi milik pelanggan itu menggunakan perintah SELECT COUNT, baru kemudian menjalankan perintah DELETE. Penghapusan data reservasinya sendiri dikerjakan otomatis oleh database melalui aturan ON DELETE CASCADE. Jumlah yang tadi dihitung dikembalikan ke controller untuk ditampilkan sebagai pemberitahuan kepada pengguna.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/beccd072-4ddb-4636-9c32-5144ed603979" />

### 8.8 Proses pada Menu Layanan

Menu Layanan memiliki alur yang mirip, namun pada proses tambah terdapat satu langkah tambahan. Sebelum menanyakan data lainnya, program meminta pengguna memilih kategori layanan terlebih dahulu.

Apabila pengguna memilih kategori rambut, program menanyakan panjang rambut lalu membuat objek LayananRambut. Apabila memilih kategori kecantikan, program menanyakan durasi pengerjaan lalu membuat objek LayananKecantikan.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/8914add1-2958-4fac-9ed4-2056c0c51590" />


Kedua jenis objek tersebut disimpan ke dalam tabel yang sama, hanya saja kolom yang terisi berbeda. Ketika data dibaca kembali, kolom kategori yang menentukan objek turunan mana yang dibuat, sehingga setiap data tetap menampilkan keterangan sesuai jenisnya masing-masing. Layanan rambut menampilkan baris panjang rambut, sedangkan layanan kecantikan menampilkan baris durasi.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/d17d093f-6e1b-4f3a-b116-19b80f2cb1d7" />


Perbedaan isi kolom tersebut juga dapat dilihat langsung pada tabel layanan di phpMyAdmin, di mana baris berkategori Rambut hanya mengisi kolom panjang_rambut sedangkan baris berkategori Kecantikan hanya mengisi kolom durasi_menit.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/10244108-d01a-4638-a4f6-6e9662b2f1df" />


Pada proses hapus layanan, seperti halnya pelanggan, seluruh reservasi yang memakai layanan tersebut ikut terhapus oleh database.

### 8.9 Proses pada Menu Reservasi

Menu Reservasi merupakan penghubung antara data pelanggan dan data layanan. Sebelum meminta masukan apa pun, program memeriksa terlebih dahulu apakah daftar pelanggan atau daftar layanan masih kosong, karena reservasi tidak mungkin dibuat tanpa keduanya.

Apabila kedua daftar sudah terisi, ReservasiController memanggil method tampilkan milik PelangganController agar daftar pelanggan muncul, lalu meminta pengguna memilih ID pelanggan. Setelah itu dilakukan hal yang sama untuk layanan. Terakhir pengguna memasukkan tanggal reservasi, lalu perintah INSERT dijalankan dengan menyimpan ID pelanggan dan ID layanan sebagai foreign key.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/7756d683-badf-46ef-943a-ba230d34e84a" />


Pada proses tampilkan reservasi, program menampilkan ID reservasi, nama pelanggan, nama layanan beserta kategorinya, harga, dan tanggal. Seluruh keterangan tersebut didapat dari satu perintah SELECT yang menggunakan JOIN terhadap tiga tabel sekaligus, sehingga program tidak perlu menjalankan tiga perintah terpisah untuk setiap baris reservasi.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/e1ed7c49-c1f9-4471-9e7b-c721256820ea" />


Pada proses ubah reservasi, data yang dapat diubah hanya tanggalnya saja, karena mengganti pelanggan atau layanan pada dasarnya berarti membuat pemesanan yang berbeda.

### 8.10 Program Berhenti

Apabila pengguna memilih angka 5 pada Menu Utama, program menampilkan pesan penutup. Kondisi pada perulangan do-while menjadi tidak terpenuhi sehingga perulangan berhenti, method main selesai dijalankan, dan program berakhir. Seluruh data yang sudah ditambahkan maupun diubah tetap tersimpan di dalam database.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/332ff449-03e9-4a75-a177-f890f882a6fd" />


## 9. Dummy Data Melalui File SQL

Seluruh data awal program diisi melalui berkas db_salon.sql yang terletak di dalam folder database pada repository ini. Berkas tersebut berisi perintah pembuatan database, pembuatan tiga tabel, serta perintah INSERT untuk lima data pelanggan, lima data layanan, dan lima data reservasi.

Cara menjalankannya adalah dengan membuka phpMyAdmin, memilih tab SQL, menempelkan seluruh isi berkas tersebut, lalu menekan tombol Go.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/6480c97c-68cc-4d07-9bbf-0ec67680c866" />


Data layanan awal sengaja dibuat mencakup kedua turunan, yaitu tiga layanan rambut dan dua layanan kecantikan, agar hasil penerapan abstraction dan overriding langsung terlihat ketika fitur tampilkan dijalankan.

Berbeda dengan Mini Project 3 yang mengisi data awal melalui method isiDataAwal di dalam class DataSalon, pada Mini Project 4 ini tidak ada satu baris kode Java pun yang mengisi data awal. Class DataSalon beserta seluruh isinya telah dihapus dari program. Dengan begitu, data awal benar-benar berasal dari database dan bukan dari program, sesuai dengan ketentuan yang diminta.

## 10. Penerapan Framework ORM sebagai Nilai Tambah

ORM atau Object Relational Mapping adalah cara menghubungkan objek Java dengan tabel database tanpa harus menulis perintah SQL secara manual. Framework ORM yang dipakai pada program ini adalah Hibernate, yang dipasang melalui pom.xml dengan menambahkan dependency hibernate-core.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/41de0fb9-247b-45eb-80dd-60b1c051ed1b" />

### 10.1 Letak Penerapan

Penerapan ORM diletakkan pada package orm yang berisi tiga berkas.

Berkas pertama adalah PelangganORM, yang merupakan class entity. Class ini ditandai dengan anotasi @Entity untuk menyatakan bahwa class tersebut mewakili sebuah tabel, anotasi @Table untuk menyebutkan nama tabelnya, anotasi @Id untuk menandai kolom primary key, serta anotasi @Column pada setiap atributnya untuk menyebutkan nama kolom yang diwakili. Anotasi inilah yang menjadi peta penghubung antara atribut Java dengan kolom database.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/a3c9a364-32cd-4505-b89a-a329346ee7fe" />

Berkas kedua adalah HibernateUtil, yang bertugas membuat SessionFactory, yaitu objek utama Hibernate yang nantinya dipakai untuk membuka sesi hubungan dengan database. Objek ini sengaja hanya dibuat satu kali lalu dipakai berulang, karena proses pembuatannya cukup berat.

Berkas ketiga adalah PelangganORMController, yang berisi satu method untuk menampilkan seluruh data pelanggan. Di dalamnya tidak terdapat satu pun perintah SQL. Yang ditulis hanyalah kalimat FROM PelangganORM ORDER BY idPelanggan, dan kalimat tersebut bukan SQL melainkan nama class Java beserta nama atributnya. Hibernate sendiri yang kemudian menerjemahkan kalimat tersebut menjadi perintah SQL yang sesungguhnya.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/6b9fde1e-ceff-42d1-ba6c-45b865035929" />


Pengaturan koneksi untuk Hibernate diletakkan terpisah pada berkas hibernate.cfg.xml di dalam folder resources, yang berisi alamat database, nama pengguna, kata sandi, serta pengaturan agar setiap perintah SQL yang dibuat Hibernate ditampilkan ke layar.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/968e83e3-1b62-4ee8-9f0c-4d422cdd4528" />


### 10.2 Hasil Penerapan

Hasil penerapan ORM dapat dilihat pada menu nomor 4. Ketika menu tersebut dipilih, pada layar muncul baris yang diawali tulisan Hibernate diikuti perintah select beserta seluruh nama kolomnya.

Perintah tersebut tidak pernah ditulis di dalam program. Perintah itu dibuat sendiri oleh Hibernate berdasarkan anotasi yang dipasang pada class PelangganORM. Setelah baris tersebut, daftar pelanggan ditampilkan dengan isi yang sama persis seperti menu nomor 1, karena keduanya membaca tabel yang sama hanya dengan cara yang berbeda.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/14410f78-4113-4946-bfbc-b9a187879356" />

### 10.3 Perbandingan dengan JDBC

Pada cara JDBC yang dipakai di menu 1 sampai 3, setiap perintah SQL ditulis sendiri baris demi baris, tempat kosongnya diisi satu per satu menggunakan setString atau setInt, hasilnya dibaca kolom demi kolom dari ResultSet, lalu diubah sendiri menjadi objek Java.

Pada cara ORM yang dipakai di menu 4, seluruh langkah tersebut tidak ada. Program cukup menyebutkan nama class yang ingin diambil datanya, dan hasilnya sudah langsung berupa objek Java yang siap dipakai.

Kelebihan ORM adalah kode menjadi jauh lebih pendek dan tidak ada risiko salah ketik pada nama kolom maupun perintah SQL. Kekurangannya adalah perintah SQL yang sebenarnya berjalan menjadi tersembunyi, sehingga apabila program melambat akan lebih sulit mencari penyebabnya. Selain itu Hibernate membawa banyak berkas tambahan sehingga ukuran program menjadi jauh lebih besar dibandingkan JDBC biasa.

Oleh karena itu pada program ini keduanya sengaja dipakai berdampingan. Cara JDBC dipakai untuk seluruh fitur utama agar dasar kerja database terlihat jelas, sedangkan ORM dipakai pada satu menu tersendiri sebagai perbandingan.

## 11. Konsep OOP yang Dipertahankan

Seluruh konsep Pemrograman Berorientasi Objek dari Mini Project sebelumnya tetap dipertahankan dan tetap bekerja walaupun tempat penyimpanan datanya sudah berganti menjadi database.

### 11.1 Abstraction

Abstraction diterapkan pada class Layanan yang berada di package model. Pada baris deklarasi class terdapat kata kunci abstract, dan di dalamnya terdapat satu abstract method bernama getKategori yang hanya berisi judul method tanpa isi sama sekali.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/237491b9-b345-4011-a4df-3f474c7bda6e" />
<img width="400" alt="image" src="https://github.com/user-attachments/assets/1381ba77-10e8-4485-b439-accac0df02a3" />

Abstract method tersebut wajib diisi oleh kedua class turunannya. Pada class LayananRambut, method getKategori diisi dengan mengembalikan teks Rambut. Pada class LayananKecantikan, method yang sama diisi dengan mengembalikan teks Kecantikan.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/4b5fb893-6f4b-4302-938a-8cebcae499b4" />

<img width="400" alt="image" src="https://github.com/user-attachments/assets/edb426b9-0fea-4dc1-a888-2973eb316c9c" />

Pada Mini Project 4 ini, hasil method getKategori tersebut mendapat kegunaan tambahan. Nilai yang dikembalikannya ikut disimpan ke dalam kolom kategori pada tabel layanan, dan nilai itu pula yang dibaca kembali oleh LayananDAO untuk menentukan objek turunan mana yang harus dibuat. Dengan demikian pembagian jenis layanan tidak hanya hidup di dalam program, tetapi juga tersimpan di dalam database.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/2d285834-4449-4c8d-a9f6-b7f872c5af93" />


Alasan abstraction diterapkan adalah karena kategori layanan tidak mungkin ditentukan di class induk. Layanan secara umum tidak punya kategori yang pasti, yang punya kategori jelas hanyalah turunannya. Selain itu dengan menjadikannya abstract, Java akan menolak apabila ada kode yang mencoba membuat objek Layanan secara langsung, sehingga kesalahan tersebut ketahuan sejak awal.

### 11.2 Polymorphism Bentuk Overriding

Method overriding adalah keadaan ketika class turunan menuliskan ulang method yang sudah ada di class induk dengan isi yang berbeda. Pada program ini method getInfo yang berada di class Layanan ditulis ulang oleh kedua turunannya, dan ditandai dengan anotasi @Override.

Letak penerapannya dapat dilihat pada screenshot class LayananRambut dan LayananKecantikan di bagian sebelumnya. Pada kedua class tersebut, method getInfo memanggil super.getInfo terlebih dahulu untuk mengambil keterangan umum dari class induk, kemudian menambahkan keterangan khususnya sendiri.

Alasan overriding diterapkan adalah karena setiap jenis layanan memiliki keterangan tambahan yang berbeda. Tanpa overriding, baris panjang rambut dan baris durasi tidak akan pernah muncul karena class induk tidak mengetahui adanya atribut tambahan pada turunannya. Selain itu dengan memanggil super.getInfo, keterangan umum seperti ID, nama, dan harga cukup ditulis satu kali saja di class induk.

Hasil penerapannya terlihat pada menu Tampilkan Layanan di bagian 8.8. Seluruh data ditampilkan menggunakan satu perulangan dan satu pemanggilan method getInfo yang sama, namun hasilnya berbeda sesuai jenis datanya.

### 11.3 Polymorphism Bentuk Overloading

Method overloading adalah keadaan ketika terdapat dua method dengan nama sama namun parameternya berbeda. Pada program ini overloading diterapkan di dua tempat.

Tempat pertama adalah method inputAngka di dalam class InputView, yang ditulis sebanyak dua kali. Bentuk pertama hanya menerima satu parameter berupa teks pertanyaan, dan dipakai ketika program hanya butuh memastikan masukan berupa angka yang lebih dari nol, misalnya saat meminta ID data yang ingin diubah atau dihapus. Bentuk kedua menerima tiga parameter, yaitu teks pertanyaan, nilai minimal, dan nilai maksimal, dan dipakai ketika masukan harus berada pada rentang tertentu.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/3663f55c-aa29-4b2e-b540-e99d5bd1fb4f" />

Tempat kedua merupakan penerapan baru pada Mini Project 4, yaitu method tambah di dalam class LayananDAO. Method tersebut juga ditulis dua kali, satu menerima objek LayananRambut dan satu lagi menerima objek LayananKecantikan. Keduanya menjalankan perintah INSERT yang berbeda, karena kolom yang diisi memang berbeda.

(screenshot dua method tambah pada class LayananDAO yang namanya sama tetapi parameternya berbeda)

Alasan overloading dipakai di sini adalah agar controller cukup memanggil satu nama method saja, yaitu tambah, tanpa perlu memikirkan jenis layanan apa yang sedang ditambahkan. Java sendiri yang memilih method mana yang dijalankan berdasarkan jenis objek yang dikirim.

### 11.4 Interface

Interface diterapkan pada berkas KelolaData yang berada di package controller. Isinya hanya berupa dua judul method tanpa isi, yaitu jalankanMenu dan tampilkan.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/75d198be-c356-468b-b15f-745643607089" />

Ketiga class controller menggunakan kata kunci implements KelolaData pada baris deklarasinya, sehingga ketiganya wajib mengisi kedua method tersebut. Interface ini juga dipakai pada class Main, di mana ketiga objek controller dideklarasikan dengan tipe KelolaData, bukan dengan nama class aslinya.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/cef2b000-d6af-4d48-a6ed-74f6e605fd9b" />


Selain itu, ReservasiController menyimpan PelangganController dan LayananController dengan tipe KelolaData, lalu memanggil method tampilkan milik keduanya ketika membuat reservasi baru, sehingga kode penampil data tidak perlu ditulis ulang.

### 11.5 Encapsulation

Encapsulation diterapkan dengan menjadikan seluruh atribut pada class model bersifat private, sehingga tidak dapat diakses langsung dari luar class. Pembacaan dan pengubahan nilainya hanya dapat dilakukan melalui method getter dan setter yang bersifat public.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/e9910202-5a00-4b79-8f42-5130338bf9ea" />

Pada Mini Project 4 ini penerapannya meluas sampai ke class Koneksi, yang menjadikan URL, USER, dan PASS bersifat private, serta ke seluruh class DAO yang menjadikan method penghitung reservasi bersifat private karena hanya dipakai di dalam class itu sendiri.

Alasannya adalah agar data tidak dapat diubah sembarangan dari luar, dan setiap perubahan selalu melewati jalur yang sudah ditentukan.

### 11.6 Inheritance

Inheritance diterapkan melalui class Layanan sebagai class induk yang diturunkan menjadi LayananRambut dan LayananKecantikan menggunakan kata kunci extends. Pada constructor kedua turunan terdapat pemanggilan super untuk mengisi atribut milik class induk yang bersifat private.

Alasannya adalah agar atribut yang sama seperti ID, nama layanan, dan harga tidak perlu ditulis ulang di kedua turunan, serta agar keduanya dapat disimpan dalam satu ArrayList maupun satu tabel database yang sama.

## 12. Validasi Input

Validasi input seluruhnya berada di dalam class InputView pada package view dan tidak diubah sama sekali dari Mini Project 3. Setiap method bekerja dengan cara menampilkan pertanyaan, membaca masukan, lalu memeriksanya. Apabila masukan tidak sesuai, program menampilkan pesan kesalahan dan mengulang pertanyaan tersebut sampai masukan yang diberikan benar.

Masukan berupa angka diperiksa agar tidak berisi huruf, nama diperiksa agar hanya berisi huruf dan spasi sekaligus dirapikan huruf besar kecilnya, nomor telepon diperiksa agar diawali angka nol delapan dengan panjang sepuluh sampai tiga belas digit, dan tanggal diperiksa agar sesuai format dua digit hari, dua digit bulan, dan empat digit tahun.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/82c2f204-4788-452c-ad82-75b6e4fb3768" />

Hasil penerapannya dapat dilihat ketika pengguna sengaja memasukkan data yang salah. Program tidak berhenti, melainkan menampilkan pesan kesalahan dan mengulang pertanyaannya.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/25c3404a-6e28-4789-87d8-2c72cffcb57e" />

Alasan validasi tetap dipertahankan adalah karena data yang salah kini tidak hanya mengotori tampilan, tetapi benar-benar tersimpan permanen di dalam database. Memperbaiki data yang terlanjur salah masuk ke database jauh lebih merepotkan dibandingkan mencegahnya sejak awal. Selain itu, pemeriksaan di sisi program ini bekerja berdampingan dengan pembatasan di sisi database, misalnya kolom nama yang dibatasi lima puluh karakter dan kolom yang ditandai NOT NULL.

## 13. Perbaikan Proses Hapus Data

Pada Mini Project 3, ketika pelanggan atau layanan dihapus, data reservasi yang terhubung ikut dihapus melalui dua method tambahan di dalam class DataSalon, yaitu hapusReservasiMilikPelanggan dan hapusReservasiMemakaiLayanan. Kedua method tersebut menelusuri ArrayList dari belakang ke depan agar tidak ada data yang terlewat.

Pada Mini Project 4, pekerjaan tersebut tidak lagi dilakukan oleh program, melainkan diserahkan kepada database melalui aturan ON DELETE CASCADE yang dipasang pada kedua foreign key tabel reservasi. Ketika perintah DELETE dijalankan terhadap satu pelanggan, database sendiri yang langsung menghapus seluruh reservasi miliknya.

Program hanya menghitung terlebih dahulu ada berapa reservasi yang akan ikut terhapus menggunakan perintah SELECT COUNT, agar jumlah tersebut dapat diberitahukan kepada pengguna.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/9a5c1312-c80b-485f-b32c-a2562ea27f91" />


Hasilnya dapat dibuktikan dengan melihat tabel reservasi di phpMyAdmin sebelum dan sesudah penghapusan. Baris reservasi milik pelanggan yang dihapus sudah tidak ada lagi tanpa program perlu menjalankan perintah DELETE terhadap tabel reservasi.

<img width="400" alt="image" src="https://github.com/user-attachments/assets/de1b5467-d417-4289-b769-dc46c0360e81" />

Cara ini lebih aman dibandingkan sebelumnya, karena aturan tersebut berlaku di tingkat database. Artinya, walaupun suatu saat data dihapus langsung dari phpMyAdmin tanpa melalui program, data reservasi yang menggantung tetap tidak akan pernah terjadi.

## 14. Kesimpulan

Program Sistem Manajemen Salon pada Mini Project 4 ini telah menerapkan seluruh ketentuan yang diminta.

Program telah terhubung ke database MySQL menggunakan JDBC melalui class Koneksi pada package config. Seluruh perintah SQL tanpa terkecuali ditulis menggunakan PreparedStatement dengan tanda tanya sebagai tempat kosong, sehingga program aman dari SQL Injection.

Pola DAO telah diterapkan melalui tiga class, yaitu PelangganDAO, LayananDAO, dan ReservasiDAO, yang menjadi satu-satunya tempat berisi perintah SQL di seluruh program, sehingga logika program benar-benar terpisah dari logika akses database.

Dummy data awal telah diisi melalui berkas db_salon.sql yang berisi lima data pelanggan, lima data layanan, dan lima data reservasi. Data tersebut langsung tampil ketika fitur tampilkan dijalankan tanpa pengguna perlu memasukkan apa pun, dan tidak ada satu baris kode Java pun yang mengisi data awal karena class DataSalon beserta method isiDataAwal telah dihapus sepenuhnya.

Sebagai nilai tambah, framework ORM Hibernate telah diterapkan melalui package orm, dengan class entity PelangganORM yang ditandai anotasi Entity, Table, Id, dan Column, serta ditampilkan melalui menu tersendiri agar perbedaannya dengan cara JDBC manual terlihat jelas.

Selain itu program tetap mempertahankan seluruh konsep dari Mini Project sebelumnya, yaitu encapsulation, inheritance, abstraction, polymorphism dalam bentuk overriding dan overloading, interface, struktur MVC, serta validasi input.

Berdasarkan hasil pengujian yang telah didokumentasikan, seluruh fitur tambah, tampilkan, ubah, dan hapus pada menu Pelanggan, Layanan, dan Reservasi telah berjalan sesuai dengan yang diharapkan, dan seluruh data terbukti tersimpan permanen di dalam database walaupun program ditutup dan dijalankan ulang.
