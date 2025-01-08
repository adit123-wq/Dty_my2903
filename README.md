# Pseudescode
Program RentalMobil

Deklarasi:
    List cars (untuk menyimpan data mobil)
    List rentals (untuk menyimpan data rental)
    Input (untuk input pengguna)

Class Car:
    id: integer
    namaMobil: string
    biaya: double
    isAvailable: boolean (menandakan ketersediaan mobil)
    tujuans: boolean (menandakan apakah mobil tergantung tujuan)

    Constructor Car(id, namaMobil, biaya, tujuans)

Class RentalData:
    rentalId: integer
    carId: integer
    nik: string
    nama: string
    supir: boolean (menandakan apakah menggunakan supir)
    tujuan: string
    totalBiaya: double
    jadwalBerangkat: Date
    jadwalKembali: Date

    Constructor RentalData(rentalId, carId, nik, nama, supir, tujuan, totalBiaya, jadwalBerangkat, jadwalKembali)

Fungsi getCarById(carId):
    Untuk setiap car di cars:
        Jika car.id == carId, return car
    Return null

Fungsi initializeSampleRentalData():
    Coba:
        Buat objek RentalData contoh dengan tanggal dan simpan ke rentals
    Tangkap Exception jika terjadi kesalahan parsing tanggal

Fungsi showRent():
    Tampilkan data semua rental dengan format tabel

Fungsi insertRent():
    Inisialisasi rentalId
    Perulangan untuk memasukkan data rental:
        Minta input NIK dan Nama
        Perulangan untuk memilih mobil:
            Tampilkan daftar mobil
            Pilih mobil dan cek ketersediaannya
            Tanyakan apakah menggunakan supir
            Minta input tanggal berangkat dan kembali
            Hitung durasi dan biaya
            Tambahkan rental data ke rentals
            Tandai mobil sebagai tidak tersedia
            Tanyakan apakah ingin menyewa mobil lain
        Tanyakan apakah ingin tambah pelanggan baru

Fungsi showMenu():
    Tampilkan pilihan menu

Fungsi showallCars():
    Tampilkan daftar semua mobil dengan ketersediaan dan keterangan

Fungsi initializeSampleData():
    Tambahkan mobil contoh ke dalam list cars

Fungsi deleteRent():
    Perulangan untuk menghapus data rental:
        Tampilkan daftar rental
        Pilih nomor rental yang akan dihapus
        Tandai mobil yang dikembalikan sebagai tersedia
        Hapus rental data dari list rentals
        Tanyakan apakah ingin menghapus rental lain

Fungsi editRent():
    Perulangan untuk mengedit data rental:
        Tampilkan daftar rental
        Pilih nomor rental yang akan diedit
        Minta input baru untuk nama, NIK, mobil, supir, tanggal berangkat dan kembali, serta tujuan
        Hitung ulang biaya berdasarkan perubahan yang dilakukan
        Tampilkan biaya baru

Fungsi cetakLaporan():
    Tentukan jumlah item per halaman
    Hitung total halaman
    Perulangan untuk mencetak laporan dalam beberapa halaman:
        Tampilkan data rental per halaman
        Tampilkan subtotal per halaman
        Hitung total biaya keseluruhan
        Tanyakan apakah ingin melanjutkan ke halaman berikutnya
    Jika tidak ada data rental, tampilkan pesan

Main Program:
    Inisialisasi data mobil (initializeSampleData)
    Perulangan untuk menampilkan menu dan memilih opsi:
        Jika pilihan menu adalah:
            1: Tampilkan semua mobil (showallCars)
            2: Tampilkan semua data rental (showRent)
            3: Masukkan data rental (insertRent)
            4: Edit data rental (editRent)
            5: Hapus data rental (deleteRent)
            6: Cetak laporan rental (cetakLaporan)
            0: Keluar dari program
        Jika input tidak valid, tampilkan pesan kesalahan
