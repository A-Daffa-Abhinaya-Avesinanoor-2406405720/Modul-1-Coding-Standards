<details>
<summary>Modul 1</summary>

# Refleksi 1

Saya sudah menerapkan clean code dengan menerapkan penamaan kelas, method, dan variabel yang deskriptif dan memfokuskan method pada suatu tugas sehingga mudah dipahami dan di-maintain untuk kedepannya. Saya juga menerapkan konsistensi penamaan dan pemisahan concern. Menurut saya, source code yang diberikan memiliki kekurangan karena belum ada validasi input, misalnya pada variable productQuantity. Saya menambahkan validasi ketika input productQuantity pada CreateProduct.html supaya jumlahnya harus integer positif dengan menambahkan method validasi di ProductServiceImpl.java dan memunculkan pesan error jika tidak valid di ProductController.java.

# Refleksi 2

Menurut saya, jumlah unit test di suatu kelas tidak ditentukan angka coverage, tetapi yang penting mencakup skenario utama, edge case, dan kondisi gagal. Kecukupan test bisa dibantu dengan code coverage, namun coverage tinggi hanya menunjukkan baris yang dijalankan, bukan jaminan kodenya bebas bug atau logikanya. Jadi 100% coverage tetap bisa memiliki error jika assert-nya lemah atau skenario penting belum diuji.

Jika membuat functional test baru untuk menghitung jumlah item list dengan setup yang sama seperti test sebelumnya, codenya bisa tidak jadi clean code karena banyak duplikat dan magic string. Hal ini membuat code sulit di-maintain ketika UI berubah, dan meningkatkan risiko timing, element selector atau delay. Fix yang bisa dilakukan adalah membuat helper method atau Page Object untuk setup atau menyimpan URL dalam constant, dan menggunakan explicit wait yang konsisten. Dengan cara itu, kode test bisa lebih menjadi clean code.
</details>

<details>
<summary>Modul 2</summary>

# Refleksi 1

Masalah code quality yang saya perbaiki adalah validasi input pada fitur produk, khususnya pada name dan quantity. Sebelumnya, nama produk bisa null atau hanya spasi, dan quantity bisa nol atau negatif sehingga data tidak valid. Saya menambahkan validasi di serviceimpl, lalu menampilkan pesan error dengan controller ketika validasi gagal. Selain itu say memperbaiki issue mengenai code scanning alerts dari scorecard mengenai `Token-Permissions: no top level permission defined` pad `ci.yml` dan `sonarcloud.yml` dengan menambahkan
```yml
permissions:
  contents: read
```

# Refleksi 2

Menurut saya, workflow CI saat ini sudah memenuhi definisi Continuous Integration karena pipeline-nya otomatis menjalankan build dan unit test setiap ada push atau pull request. Untuk Continuous Deployment, workflow deploy ke Koyeb berjalan otomatis saat ada push ke branch main, jadi ini sudah termasuk CD karena tidak perlu deploy manual. Jadi, CI dan CD berjalan dengan baik.

</details>

<details>
<summary>Modul 3</summary>

# Refleksi 1


#### Single Responsibility Principle:

- Satu class hanya memiliki satu tugas atau _responsibility_. Di projek ini, `ProductController` hanya menangani HTTP flow untuk `product`, sedangkan `CarController` hanya menangani HTTP flow untuk `car`. Model (`Product`, `Car`) hanya menyimpan data. Repository mengelola penyimpanan data. Service mengelola logic.

- Implementasi SRP: sebelumnya logic `CarController` berada dalam `ProductController`. Sekarang `CarController` dipisah menjadi class sendiri sehingga setiap controller hanya bertanggung jawab untuk satu domain.

#### Open-Closed Principle:
- Class harus terbuka untuk ekstensi tetapi tertutup untuk modifikasi. Di projek ini, validasi produk dibuat extensible dengan interface `ProductValidator`. Menambah aturan validasi baru cukup dengan membuat class validator baru, tanpa mengubah `ProductServiceImpl`.

- Implementasi OCP: `ProductServiceImpl` tidak lagi punya method validasi hard-coded. `ProductServiceImpl` menerima list `ProductValidator` dan run semuanya. Untuk menambah aturan validasi, cukup menambah validator baru.

#### Liskov Substitution Principle:
- child class harus bisa menggantikan peran parent tanpa merusak behavior program. Di projek ini, implementasi service (`ProductServiceImpl`, `CarServiceImpl`) dapat digunakan melalui interface (`ProductService`, `CarService`) tanpa mengubah behavior yang diharapkan. Tidak ada subclass yang bertentangan dengan parent.

- LSP sudah terimplementasi karena semua implementasi interface tetap memenuhi kontrak metode yang sama dan tidak mengubah hasil logika program.

#### Interface Segregation Principle:
- Interface harus kecil dan spesifik agar client tidak dipaksa memakai method yang tidak dipakai. Di projek ini `ProductService` dan `CarService` hanya berisi method yang memang digunakan oleh controller. Tidak ada interface besar yang membebani client.

- ISP sudah terimplementasi karena interface service bersifat kecil dan cohesive sesuai kebutuhan client masing-masing.

#### Dependency Inversion Principle:
- Module tingkat tinggi tidak boleh bergantung ke detail. Di projek ini, service bergantung pada interface repository (`ProductRepository`, `CarRepository`), sementara implementasi detailnya ada di `ProductRepositoryImpl` dan `CarRepositoryImpl`.

- Implementasi DIP: service tidak lagi bergantung pada concrete class repository, melainkan pada interface. Dengan demikian, repositori dapat diganti (misalnya ke database) tanpa mengubah service.

# Refleksi 2
Keuntungan menerapkan SOLID:

- SRP:
  Mempermudah maintenance dan mengurangi risiko perubahan tidak relevan. Contoh: perubahan tampilan `car` hanya perlu mengubah `CarController`.

- OCP:
  Mudah menambah fitur tanpa mengubah class lama. Contoh: menambah validator baru seperti `ProductNameLengthValidator` untuk batas panjang minimal nama tanpa mengubah `ProductServiceImpl`.

- LSP:
  Memastikan substitusi implementasi aman. Contoh: mengganti `ProductServiceImpl` dengan implementasi lain (misalnya mock untuk test) tetap bekerja karena mengikuti kontrak `ProductService`.

- ISP:
  Mencegah class untuk dipaksa mengimplementasi method yang tidak dibutuhkan. Contoh: controller produk hanya bergantung ke `ProductService`.

- DIP:
  Meningkatkan fleksibilitas dan testability. Contoh: service bisa di-test dengan mock `ProductRepository` karena bergantung ke interface.

# Refleksi 3
Kekurangan tidak menerapkan SOLID:

- SRP:
  Class menjadi sulit dirawat. Contoh: jika controller `product` juga menangani `car`, setiap perubahan kecil bisa saja merusak fitur lain.

- OCP:
  Setiap aturan validasi baru harus mengubah class lama dan meningkatkan risiko regression. Contoh: menambah validasi produk harus langsung mengubah `ProductServiceImpl`.

- LSP:
  Substitusi objek menjadi berbahaya. Contoh: implementasi `ProductService` yang melempar exception untuk input valid akan merusak client yang mengandalkan kontrak.

- ISP:
  Interface membuat client bergantung pada method tidak perlu. Contoh: service `car` dipaksa punya method khusus `product` walau tidak dipakai.

- DIP:
  Module tingkat tinggi terlalu bergantung ke detail sehingga sulit diganti dan di-test. Contoh: `ProductServiceImpl` langsung membuat `ProductRepositoryImpl` sehingga unit test perlu database asli.

</details>

<details>
<summary>Modul 4</summary>

# Refleksi 1
Menurut saya, alur TDD pada modul ini berguna, terutama saat membangun fitur `Order` dari nol. Alur pengerjaannya terstruktur: membuat test repository (`save`, `findById`, `findAllByAuthor`), implementasi supaya lolos test, lalu lanjut ke service dengan mock repository. Dengan pola ini, saya tidak menebak-nebak kebutuhan kode karena fungsinya sudah didefinisikan oleh test terlebih dahulu (Test Driven Development)

Berdasarkan Percival (2017) pada bagian *Evaluating Your Testing Objectives*:
- Apakah test membantu mencegah regression? Ya, karena setiap perubahan di `OrderRepository` dan `OrderServiceImpl` langsung tervalidasi oleh test yang sudah ada.
- Apakah test membantu desain kode? Ya, terutama dalam pemisahan responsibility antara repository dan service, serta penetapan behavior pada kasus valid dan invalid.
- Apakah test yang dibuat sudah menguji behavior penting, bukan hanya implementasi? Sebagian besar iya (contoh: duplicate ID, invalid status, order tidak ditemukan), walau masih ada ruang perbaikan.
- Apakah feedback loop cepat? Cukup cepat karena sebagian besar test adalah unit test tanpa dependency eksternal.

Yang perlu saya tingkatkan pada pengerjaan berikutnya:
- Menambah edge case yang belum diuji, misalnya input `null` pada `orderId`, `author`, atau `status`.
- Mengurangi duplikasi setup data test dengan helper/factory agar test lebih maintainable.

# Refleksi 2
Menurut saya, unit test yang saya buat mengikuti prinsip F.I.R.S.T., tetapi belum sempurna.

- **Fast**: Terpenuhi. Test berjalan cepat karena berbasis unit test dan mock, tanpa database/network.
- **Independent**: Sebagian besar terpenuhi. Tiap test punya setup sendiri, tetapi masih ada pola data fixture yang serupa di banyak test yang bisa dirapikan agar tidak saling bergantung secara implisit.
- **Repeatable**: Terpenuhi. Hasil test konsisten ketika dijalankan berulang di environment yang sama.
- **Self-Validating**: Terpenuhi. Test menggunakan assertion (`assertEquals`, `assertNull`, `assertThrows`) sehingga hasil pass/fail otomatis dan jelas.
- **Timely**: Cukup terpenuhi. Test ditulis sebelum/bersamaan dengan implementasi pada alur TDD, walau pada beberapa langkah saya masih mengikuti contoh tutorial sehingga belum sepenuhnya eksploratif dari requirement sendiri.

Perbaikan untuk test berikutnya:
- Membuat nama test lebih deskriptif
- Menambah negative test untuk boundary dan null-safety.
- Mengurangi duplikasi data setup melalui method util
- Memastikan setiap test hanya memverifikasi satu behavior utama.
</details>
