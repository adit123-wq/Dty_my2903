package ProjectRentalMobil;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Windows
 */
public class  RentalMobil{
    public static ArrayList<Car> cars = new ArrayList<>();
    public static ArrayList<RentalData> rentals = new ArrayList<>();
    public static BufferedReader Input = new BufferedReader(new InputStreamReader(System.in));
    public static boolean carss = true; 
    static class Car {
        int id;
        String namaMobil;
        double biaya;
        boolean isAvailable;
        boolean tujuans;
        Car(int id, String namaMobil, double biaya, boolean tujuans) {
            this.id = id;
            this.namaMobil = namaMobil;
            this.biaya = biaya;
            this.isAvailable = true;
            this.tujuans = tujuans;
        }
    }
static class RentalData {
    int rentalId;
    int carId;
    String nik;
    String nama;
    boolean supir;
    String tujuan;
    double totalBiaya;
    Date jadwalBerangkat;
    Date jadwalKembali;
    RentalData(int rentalId, int carId, String nik, String nama, boolean supir, 
               String tujuan, double totalBiaya, Date jadwalBerangkat, Date jadwalKembali) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.nik = nik;
        this.nama = nama;
        this.supir = supir;
        this.tujuan = tujuan;
        this.totalBiaya = totalBiaya;
        this.jadwalBerangkat = jadwalBerangkat;
        this.jadwalKembali = jadwalKembali;
    }
}
public static Car getCarById(int carId) {
    for(Car car : cars) {
        if(car.id == carId) {
            return car;
        }
    }
    return null;  
}
private static void initializeSampleRentalData() {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        rentals.add(new RentalData(
            1,                             
            1,                            
            "3525015201882",           
            "John Doe",                     
            true,                           
            "Surabaya",                     
            800000,                        
            sdf.parse("01-01-2025"),      
            sdf.parse("03-01-2025")        
        ));
    } catch (ParseException e) {
        System.out.println("Error initializing sample data: " + e.getMessage());
    }
}
public static void showRent() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    System.out.println("All Rental Data");
    System.out.println("======================================================================================================================================================");
    System.out.printf("|%-4s|%-15s|%-15s|%-18s|%-8s|%-12s|%-15s|%-12s|%-23s|%-16s|\n",
            "No.",
            "Nama",
            "NIK",
            "Mobil",
            "Supir",
            "Tujuan",
            "Total Biaya",
            "Durasi (hari)",
            "Jadwal Keberangkatan",
            "Jadwal Kembali");
    System.out.println("======================================================================================================================================================");
    for(int i = 0; i < rentals.size(); i++) {
        RentalData rental = rentals.get(i);
        Car car = getCarById(rental.carId);
        String carName = car != null ? car.namaMobil : "Unknown";
        long diffInMillies = rental.jadwalKembali.getTime() - rental.jadwalBerangkat.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        System.out.printf("|%-4d|%-15s|%-15s|%-18s|%-8s|%-12s|Rp %-12.0f|%-13d|%-23s|%-16s|\n",
            i + 1,
            rental.nama,
            rental.nik,
            carName,
            rental.supir ? "Ya" : "Tidak",
            rental.tujuan,
            rental.totalBiaya,
            diffInDays,
            sdf.format(rental.jadwalBerangkat),
            sdf.format(rental.jadwalKembali));
    }
    System.out.println("======================================================================================================================================================");
}

public static void insertRent() throws IOException, ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    boolean continueCustomer = true;
    int rentalId = rentals.size() + 1;
    while(continueCustomer) {
        System.out.print("Masukkan NIK: ");
        String nik = Input.readLine();
        System.out.print("Masukkan Nama: ");
        String nama = Input.readLine();
        boolean continueRental = true;
        while(continueRental && !cars.isEmpty()) {
            showallCars();
            System.out.print("Pilih nomor mobil (1-" + cars.size() + "): ");
            int carIndex = Integer.parseInt(Input.readLine()) - 1;
            if(carIndex >= 0 && carIndex < cars.size()) {
                Car selectedCar = cars.get(carIndex);
                if(!selectedCar.isAvailable) {
                    System.out.println("Mobil tidak tersedia untuk disewa!");
                    continue;
                }
                System.out.print("Menggunakan Supir? (y/t): ");
                boolean useSupir = Input.readLine().equalsIgnoreCase("y");
                System.out.print("Masukkan Tanggal Berangkat (dd-MM-yyyy): ");
                Date jadwalBerangkat = sdf.parse(Input.readLine());
                System.out.print("Masukkan Tanggal Kembali (dd-MM-yyyy): ");
                Date jadwalKembali = sdf.parse(Input.readLine());
                long diffInMillies = jadwalKembali.getTime() - jadwalBerangkat.getTime();
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                double totalBiaya;
                String tujuan = "-";
                if(selectedCar.tujuans) {
                    System.out.print("Masukkan Tujuan: ");
                    tujuan = Input.readLine();
                    System.out.print("Masukkan Biaya Sesuai Tujuan: ");
                    totalBiaya = Double.parseDouble(Input.readLine());
                } else {
                    System.out.print("Masukkan Tujuan: ");
                    tujuan = Input.readLine();
                    totalBiaya = selectedCar.biaya * diffInDays;
                }
                
                if(useSupir) {
                    totalBiaya += 400000 * diffInDays; 
                }
                
                rentals.add(new RentalData(rentalId, selectedCar.id, nik, nama, useSupir, 
                                         tujuan, totalBiaya, jadwalBerangkat, jadwalKembali));
                selectedCar.isAvailable = false;
                
                System.out.println("Rental berhasil ditambahkan!");
                System.out.println("Total Biaya: Rp " + String.format("%,.0f", totalBiaya));
                
                System.out.print("Sewa mobil lain untuk pelanggan yang sama? (y/t): ");
                if(Input.readLine().equalsIgnoreCase("t")) {
                    continueRental = false;
                }
                rentalId++;
            } else {
                System.out.println("Nomor mobil tidak valid!");
            }
        }
        
        System.out.print("Tambah pelanggan baru? (y/t): ");
        if(Input.readLine().equalsIgnoreCase("t")) {
            continueCustomer = false;
        }
    }
}
    public static void showMenu(){
        System.out.println("1. Tampilkan semua mobil");
        System.out.println("2. Tampilkan semua data rental");
        System.out.println("3. Masukan data rental");
        System.out.println("4. Edit data rental");
        System.out.println("5. Hapus data rental");
        System.out.println("6. Cetak laporan rental");
        System.out.println("0. Exit");
    }
    public static void showallCars()throws IOException {
        System.out.println("Data Mobil");
        System.out.println("=================================================================================");
        System.out.printf("|%-4s|%-15s|%-18s|%-15s|%-23s|",
                "No.",
                "Nama Mobil",
                "Harga Sewa",
                "Ketersediaan",
                "Keterangan");
        System.out.println();
        System.out.println("|====+===============+==================+===============+=======================|");
        
        for(int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            String ketersediaan = car.isAvailable ? "Tersedia" : "Tidak Tersedia";
            String keterangan = car.tujuans ? "Tergantung tujuan" : "Tidak Tergantung Tujuan";
            System.out.printf("|%-4d|%-15s|Rp %-,11.0f/day|%-15s|%-23s|",
                i + 1,
                car.namaMobil,
                car.biaya, 
                ketersediaan,
                keterangan);
            System.out.println();
        }
        System.out.println("=================================================================================");
    }
    private static void initializeSampleData() {
        cars.add(new Car(1, "Calya", 350000, false));
        cars.add(new Car(2, "Expander", 400000, false));
        cars.add(new Car(3, "Pajero Sport", 1200000, false));
        cars.add(new Car(4, "Brio", 300000, false));
        cars.add(new Car(5, "Avanza Veloz", 350000, true));
        cars.add(new Car(6, "Innova Reborn", 600000, true));
        //tambah
    }
   public static void deleteRent() throws IOException {
    boolean delete = true;
    while(delete) {
        if(rentals.isEmpty()) {
            System.out.println("Tidak Ada Data Rental yang bisa dihapus");
            return;
        }
        showRent();
        System.out.print("Masukan Nomor Rental yang akan dihapus (1-" + rentals.size() + "): ");
        int position = Integer.parseInt(Input.readLine());
        if (position >= 1 && position <= rentals.size()) {
            RentalData rentalToDelete = rentals.get(position - 1);
            for(Car car : cars) {
                if(car.id == rentalToDelete.carId) {
                    car.isAvailable = true;
                    break;
                }
            }
            rentals.remove(position - 1);
            System.out.println("Data Rental berhasil dihapus!");
        } else {
            System.out.println("Nomor Rental tidak valid!");
        }
        System.out.print("Hapus data rental lain? (y/t): ");
        String jawab = Input.readLine();
        if(jawab.equalsIgnoreCase("t")) {
            delete = false;
        }
    }
}
   public static void editRent() throws IOException, ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    boolean continueEdit = true;
    while(continueEdit) {
        if(rentals.isEmpty()) {
            System.out.println("Tidak Ada Data Rental yang bisa diedit");
            return;
        }
        showRent();
        System.out.print("Masukan Nomor Rental yang akan diedit (1-" + rentals.size() + "): ");
        int position = Integer.parseInt(Input.readLine());
        
        if (position >= 1 && position <= rentals.size()) {
            RentalData rental = rentals.get(position - 1);
            Car currentCar = getCarById(rental.carId);
            System.out.println("\nTekan Enter untuk mempertahankan data lama");
            System.out.print("Masukkan Nama Baru [" + rental.nama + "]: ");
            String newName = Input.readLine();
            if(!newName.isEmpty()) {
                rental.nama = newName;
            }
            System.out.print("Masukkan NIK Baru [" + rental.nik + "]: ");
            String newNik = Input.readLine();
            if(!newNik.isEmpty()) {
                rental.nik = newNik;
            }
            System.out.print("Ganti Mobil? (y/t): ");
            if(Input.readLine().equalsIgnoreCase("y")) {
                showallCars();
                System.out.print("Pilih nomor mobil baru (1-" + cars.size() + "): ");
                int newCarIndex = Integer.parseInt(Input.readLine()) - 1;
                
                if(newCarIndex >= 0 && newCarIndex < cars.size()) {
                    Car newCar = cars.get(newCarIndex);
                    if(!newCar.isAvailable && newCar.id != currentCar.id) {
                        System.out.println("Mobil tidak tersedia untuk disewa!");
                    } else {
                        currentCar.isAvailable = true;
                        newCar.isAvailable = false;
                        rental.carId = newCar.id;
                    }
                } else {
                    System.out.println("Nomor mobil tidak valid!");
                }
            }
            System.out.print("Gunakan Supir? (y/t) [" + (rental.supir ? "y" : "t") + "]: ");
            String newSupir = Input.readLine();
            if(!newSupir.isEmpty()) {
                rental.supir = newSupir.equalsIgnoreCase("y");
            }
            System.out.print("Masukkan Tanggal Berangkat Baru (dd-MM-yyyy) [" + sdf.format(rental.jadwalBerangkat) + "]: ");
            String newStartDate = Input.readLine();
            if(!newStartDate.isEmpty()) {
                rental.jadwalBerangkat = sdf.parse(newStartDate);
            }
            System.out.print("Masukkan Tanggal Kembali Baru (dd-MM-yyyy) [" + sdf.format(rental.jadwalKembali) + "]: ");
            String newEndDate = Input.readLine();
            if(!newEndDate.isEmpty()) {
                rental.jadwalKembali = sdf.parse(newEndDate);
            }
            Car selectedCar = getCarById(rental.carId);
            System.out.print("Masukkan Tujuan Baru [" + rental.tujuan + "]: ");
            String newTujuan = Input.readLine();
            if(!newTujuan.isEmpty()) {
                rental.tujuan = newTujuan;
            }
            long diffInMillies = rental.jadwalKembali.getTime() - rental.jadwalBerangkat.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(selectedCar.tujuans) {
                System.out.print("Masukkan Biaya Sesuai Tujuan Baru: ");
                String newBiaya = Input.readLine();
                if(!newBiaya.isEmpty()) {
                    rental.totalBiaya = Double.parseDouble(newBiaya);
                }
            } else {
                rental.totalBiaya = selectedCar.biaya * diffInDays;
            }
            if(rental.supir) {
                rental.totalBiaya += 400000 * diffInDays;
            }
            System.out.println("Data Rental berhasil diupdate!");
            System.out.println("Total Biaya Baru: Rp " + String.format("%,.0f", rental.totalBiaya));
            
        } else {
            System.out.println("Nomor Rental tidak valid!");
        }
        
        System.out.print("Edit data rental lain? (y/t): ");
        String jawab = Input.readLine();
        if(jawab.equalsIgnoreCase("t")) {
            continueEdit = false;
        }
    }
}
   public static void cetakLaporan() throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final int ITEMS_PER_PAGE = 3; 
    int totalPages = (int) Math.ceil((double) rentals.size() / ITEMS_PER_PAGE);
    double grandTotal = 0;
    for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LAPORAN DATA RENTAL MOBIL");
        System.out.println("545 RENTCAR");
        System.out.println("Halaman: " + currentPage + " dari " + totalPages);
        System.out.println("======================================================================================================================================================");
        System.out.printf("|%-4s|%-15s|%-15s|%-18s|%-8s|%-12s|%-15s|%-12s|%-23s|%-16s|\n",
                "No.",
                "Nama",
                "NIK",
                "Mobil",
                "Supir",
                "Tujuan",
                "Total Biaya",
                "Durasi (hari)",
                "Jadwal Keberangkatan",
                "Jadwal Kembali");
        System.out.println("======================================================================================================================================================");
        int startIdx = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIdx = Math.min(startIdx + ITEMS_PER_PAGE, rentals.size());
        double subtotal = 0;
        for (int i = startIdx; i < endIdx; i++) {
            RentalData rental = rentals.get(i);
            Car car = getCarById(rental.carId);
            String carName = car != null ? car.namaMobil : "Unknown";
            long diffInMillies = rental.jadwalKembali.getTime() - rental.jadwalBerangkat.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            System.out.printf("|%-4d|%-15s|%-15s|%-18s|%-8s|%-12s|Rp %,-12.0f|%-13d|%-23s|%-16s|\n",
                i + 1,
                rental.nama,
                rental.nik,
                carName,
                rental.supir ? "Ya" : "Tidak",
                rental.tujuan,
                rental.totalBiaya,
                diffInDays,
                sdf.format(rental.jadwalBerangkat),
                sdf.format(rental.jadwalKembali));
                
            subtotal += rental.totalBiaya;
            grandTotal += rental.totalBiaya;
        }
        System.out.println("======================================================================================================================================================");
        System.out.printf("Subtotal Halaman %d: Rp %,.0f\n", currentPage, subtotal);
        System.out.println("======================================================================================================================================================");
        if (currentPage == totalPages) {
            System.out.printf("GRAND TOTAL: Rp %,.0f\n", grandTotal);
            System.out.println("======================================================================================================================================================");
            System.out.println("Data sudah habis. Tekan enter untuk selesai...");
            Input.readLine();
        } else {
            System.out.println("Tekan enter untuk halaman berikutnya...");
            Input.readLine();
        }
    }
    if (rentals.isEmpty()) {
        System.out.println("Tidak ada data rental untuk dicetak.");
        System.out.println("Tekan enter untuk kembali ke menu...");
        Input.readLine();
    }
}
    public static void main(String[] args)throws IOException, ParseException {
        initializeSampleData();
//        initializeSampleRentalData();
        while(true){
        System.out.println("Rental Mobil");
        System.out.println("24.240.0001 | Aditya Mahatva Yodha");
        showMenu();
        int choice;
        System.out.print("Masukan Nomer Menu : ");
        choice = Integer.parseInt(Input.readLine());
            switch(choice){
                case 1:
                    showallCars();
                    break;
                case 2:
                    showRent();
                    break;
                case 3:
                    insertRent();
                    break;
                case 4:
                    editRent();
                    break;
                case 5:
                    deleteRent();
                    break;
                case 6:
                    cetakLaporan();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default :
                    System.out.println("Input tidak Valid!");
            }
        }
    }
}
