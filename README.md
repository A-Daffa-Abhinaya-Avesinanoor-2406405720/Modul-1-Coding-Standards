# Refleksi 1

Saya sudah menerapkan clean code dengan menerapkan penamaan kelas, method, dan variabel yang deskriptif dan memfokuskan method pada suatu tugas sehingga mudah dipahami dan di-maintain untuk kedepannya. Saya juga menerapkan konsistensi penamaan dan pemisahan concern. Menurut saya, source code yang diberikan memiliki kekurangan karena belum ada validasi input, misalnya pada variable productQuantity. Saya menambahkan validasi ketika input productQuantity pada CreateProduct.html supaya jumlahnya harus integer positif dengan menambahkan method validasi di ProductServiceImpl.java dan memunculkan pesan error jika tidak valid di ProductController.java.

