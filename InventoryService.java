
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class InventoryService {

    public void initDataDirectory(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    public List<Product> loadProducts(Path csvPath) throws IOException {
        List<Product> products = new ArrayList<>();
        if (Files.exists(csvPath)) {
            try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
                String line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    products.add(Product.fromCsvLine(line));
                }
            }
        }
        return products;
    }

    public void saveProducts(List<Product> products, Path csvPath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath)) {
            writer.write("ID,Name,Category,Price,Quantity\n");
            for (Product p : products) {
                writer.write(p.toCsvLine() + "\n");
                }
        }
    }

    public List<Product> searchProducts(List<Product> products, String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public void sortProducts(List<Product> products, String criteria) {
        switch (criteria) {
            case "name":
            products.sort(Comparator.comparing(Product::getName));
            break;
            case "category":
            products.sort(Comparator.comparing(Product::getCtg));
            break;
        }
    }

    public List<Product> filterByPrice(List<Product> products, double min, double max) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            double harga = p.getPrice();
            if (harga >= min && harga <= max) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public void runMenu(List<Product> products) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            Type.center("INVENTORY MANAGER");
            System.out.println("__________________________________________________");
            System.out.println("1. Lihat Produk");
            System.out.println("2. Tambah Produk");
            System.out.println("3. Update Stok");
            System.out.println("4. Hapus Produk");
            System.out.println("5. Cari Produk");
            System.out.println("6. Sort Produk");
            System.out.println("7. Filter Harga");
            System.out.println("8. Simpan & Keluar");
            System.out.print("Pilih: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    viewAll(products);  
                    break;
                case "2":
                    addProduct(products, sc);
                    break;
                case "3":
                    updateQuantity(products, sc);
                    break;
                case "4":
                    deleteProduct(products, sc);
                    break;
                case "5":
                    System.out.print("Masukkan keyword: ");
                    String keyword = sc.nextLine();
                    List<Product> hasil = searchProducts(products, keyword);
                    viewAll(hasil);
                    break;
                case "6":
                    System.out.print("Sort by (name/category): ");
                    String criteria = sc.nextLine();
                    sortProducts(products, criteria);
                    viewAll(products);
                    break;
                case "7":
                    System.out.print("Min harga: ");
                    double min = Double.parseDouble(sc.nextLine());
                    System.out.print("Max harga: ");
                    double max = Double.parseDouble(sc.nextLine());
                    List<Product> filtered = filterByPrice(products, min, max);
                    viewAll(filtered);
                    break;
                case "8":
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

   private void viewAll(List<Product> products) {
        System.out.printf("%-5s %-20s %-15s %-10s %-8s\n", "ID", "Nama", "Kategori", "Harga", "Stok");
        for (Product p : products) {
            System.out.printf("%-5d %-20s %-15s %-10.2f %-8d\n",
            p.getId(), p.getName(), p.getCtg(), p.getPrice(), p.getQty());
        }
   }

   private void addProduct(List<Product> products, Scanner sc) {
        int id = products.stream().mapToInt(Product::getId).max().orElse(0) + 1;
        System.out.print("Nama: ");
        String name = sc.nextLine();
        System.out.print("Kategori: ");
        String category = sc.nextLine();
        System.out.print("Harga: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Stok: ");
        int qty = Integer.parseInt(sc.nextLine());
        products.add(new Product(id, name, category, price, qty));
        System.out.println("Produk ditambahkan.");
   }

   private void updateQuantity(List<Product> products, Scanner sc) {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Product p : products) {
            if (p.getId() == id) {
                System.out.print("Stok baru: ");
                int qty = Integer.parseInt(sc.nextLine());
                p.setQty(qty);
                System.out.println("Stok diperbarui.");
                return;
            }
        }
   }

   private void deleteProduct(List<Product> products, Scanner sc) {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Product p : products) {
            if (p.getId() == id) {
                products.remove(p);
                System.out.println("Produk dihapus.");
                return;
            }
        }
   }
}