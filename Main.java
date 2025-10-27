package week08.kristian.id.ac.umn;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Item> ListOfItems = new ArrayList<>();
    static ArrayList<Payment> ListOfPayments = new ArrayList<>();
    static Scanner s = new Scanner(System.in);

    public static void seedData() {
        if (ListOfItems.isEmpty()) {
            ListOfItems.add(new Item("Kulkas", "Elektronik", 4800000));
            ListOfItems.add(new Item("TV", "Elektronik", 1280000));
            ListOfItems.add(new Item("Laptop", "Komputer", 6000000));
            ListOfItems.add(new Item("PC", "Komputer", 12000000));
        }
    }

    public static void printItem(Item item) {
        System.out.println("Nama   : " + item.getName());
        System.out.println("Tipe   : " + item.getType());
        System.out.println("Harga  : " + item.getPrice());
    }

    public static void printTransaction(Payment p, int num) {
        System.out.println("No                 : " + num);
        System.out.println("Nama               : " + p.getItemName());
        System.out.println("Tipe               : " + p.getItem().getType());
        System.out.println("Status             : " + p.getStatus());
        System.out.println("Sisa Pembayaran    : " + p.getRemainingAmount());
        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        int opt;
        seedData();

        do {
            System.out.println("---Program Toko Elektronik---");
            System.out.println("1. Pesan Barang");
            System.out.println("2. Lihat Pesanan");
            System.out.println("0. Keluar");
            System.out.print("Pilih : ");
            opt = s.nextInt();
            s.nextLine();

            if (opt == 1) {
                System.out.println("---Daftar Barang---");
                for (int i = 0; i < ListOfItems.size(); i++) {
                    System.out.println("No : " + (i + 1));
                    printItem(ListOfItems.get(i));
                    System.out.println("---------------------------------");
                }
                System.out.print("Pilih : ");
                int id = s.nextInt() - 1;
                s.nextLine();

                if (id < 0 || id >= ListOfItems.size()) {
                    System.out.println("Pilihan tidak valid.");
                    continue;
                }

                Item chosenItem = ListOfItems.get(id);
                printItem(chosenItem);
                System.out.println("---Tipe pembayaran---");
                System.out.println("1. Cash");
                System.out.println("2. Credit");
                System.out.print("Pilih : ");
                int payOpt = s.nextInt();
                s.nextLine();
                
                if (payOpt == 1) {
                    Payment newPayment = new Cash(chosenItem);
                    System.out.print("Bayar (Y/N): ");
                    String confirm = s.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        int amount = newPayment.pay();
                        System.out.println("Harga Pembayaran : " + amount);
                        System.out.println("Bayar : " + amount);
                        System.out.println("Transaksi telah dibayar lunas");
                    } else {
                        System.out.println("Transaksi telah disimpan");
                    }
                    ListOfPayments.add(newPayment);

                } else if (payOpt == 2) {
                    int install;
                    do {
                        System.out.print("Lama Cicilan (3/6/9/12): ");
                        install = s.nextInt();
                    } while (install != 3 && install != 6 && install != 9 && install != 12);
                    s.nextLine();

                    Payment newPayment = new Credit(chosenItem, install);
                    int installmentAmount = newPayment.pay(); 

                    System.out.println("Harga Pembayaran : " + installmentAmount);
                    System.out.println("Bayar : " + installmentAmount);
                    System.out.println("Transaksi telah dibayar");
                    ListOfPayments.add(newPayment);
                }

            } else if (opt == 2) {
                if (ListOfPayments.isEmpty()) {
                    System.out.println("Belum ada transaksi.");
                    System.out.println("---------------------------------");
                    continue;
                }

                for (int i = 0; i < ListOfPayments.size(); i++) {
                    printTransaction(ListOfPayments.get(i), i + 1);
                }

                System.out.print("Pilih No Transaksi : ");
                int transId = s.nextInt() - 1;
                s.nextLine();

                if (transId < 0 || transId >= ListOfPayments.size()) {
                    System.out.println("Transaksi tidak ditemukan.");
                    continue;
                }

                Payment p = ListOfPayments.get(transId);

                System.out.println("No                 : " + (transId + 1));
                System.out.println("Nama               : " + p.getItemName());
                System.out.println("Tipe               : " + p.getItem().getType());
                System.out.println("Status             : " + p.getStatus());
                System.out.println("Sisa Pembayaran    : " + p.getRemainingAmount());

                if (p.isPaidOff()) {
                    System.out.println("Transaksi telah lunas");
                    continue;
                }

                int paymentAmount = 0;
                if (p instanceof Cash) {
                    paymentAmount = p.getRemainingAmount();
                } else if (p instanceof Credit) {
                    paymentAmount = p.getItem().getPrice() / ((Credit) p).getMaxInstallmentAmount();
                }
                
                System.out.println("Harga Pembayaran : " + paymentAmount);
                System.out.print("Bayar : ");
                int bayar = s.nextInt();
                s.nextLine();

                if (bayar == paymentAmount) {
                    p.pay();
                    if (p.isPaidOff()) {
                        System.out.println("Transaksi telah dibayar lunas");
                    } else {
                        System.out.println("Transaksi telah dibayar");
                    }
                } else {
                    System.out.println("Pembayaran gagal, jumlah tidak sesuai.");
                }

            } else if (opt == 0) {
                System.out.println("Terima Kasih");
            } else {
                System.out.println("Salah Input");
            }
        } while (opt != 0);
    }
}
