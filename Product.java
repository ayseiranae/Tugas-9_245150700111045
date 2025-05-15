public class Product {
    int id;
    String name;
    String ctg;
    double price;
    int qty;

    public Product(int _id, String _name, String _ctg, double _price, int _qty) {
        id = _id;
        name = _name;
        ctg = _ctg;
        price = _price;
        qty = _qty;
    }

    int getId() { return id; }
    void setId(int _id) { id = _id; }
    String getName() { return name; }
    void setName(String _name) { name = _name; }
    String getCtg() { return ctg; }
    void setCtg(String _ctg) { name = _ctg; }
    double getPrice() { return price; }
    void setPrice(double _price) { price = _price; }
    int getQty() { return qty; }
    void setQty(int _qty) { qty = _qty; }

    public String toCsvLine() {
        return id + "," + name + "," + ctg + "," + price + "," + qty;
    }

    // 1,telor,bahan,5000.0,2
    /* 0 = 1
     * 1 = telor
     * 2 = bahan
     * 3 = 5000.0
     * 4 = 2
     */
    
    public static Product fromCsvLine(String line) {
        String[] parts = line.split(",", 5);
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String ctg = parts[2];
        double price = Double.parseDouble(parts[3]);
        int qty = Integer.parseInt(parts[4]);
        return new Product(id, name, ctg, price, qty);
    }
}