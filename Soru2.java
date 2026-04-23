import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Soru2 {

    static String[] urunAdlari;
    static double[] fiyatlar;
    static int[] stoklar;
    static double[] puanlar;
    static int urunSayisi;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Kaç farklı ürün gireceksiniz: ");
            urunSayisi = Integer.parseInt(scanner.nextLine());
            if (urunSayisi < 2) {
                System.out.println("Uyarı: En az 2 farklı ürün girmelisiniz.");
            } else {
                break;
            }
        }

        urunAdlari = new String[urunSayisi];
        fiyatlar = new double[urunSayisi];
        stoklar = new int[urunSayisi];
        puanlar = new double[urunSayisi];

        for (int i = 0; i < urunSayisi; i++) {
            System.out.println("Ürün " + (i + 1) + ":");

            while (true) {
                System.out.print("Ürün Adı: ");
                String ad = scanner.nextLine();
                if (ad.length() > 20) {
                    System.out.println("Ürün adı en fazla 20 karakter olmalıdır. Tekrar girin.");
                } else {
                    urunAdlari[i] = ad;
                    break;
                }
            }

            while (true) {
                System.out.print("Birim Fiyat: ");
                double fiyat = Double.parseDouble(scanner.nextLine());
                if (fiyat < 1 || fiyat > 100) {
                    System.out.println("Fiyat 1 ile 100 arasında olmalıdır. Tekrar girin.");
                } else {
                    fiyatlar[i] = fiyat;
                    break;
                }
            }

            while (true) {
                System.out.print("Stok Miktarı: ");
                int stok = Integer.parseInt(scanner.nextLine());
                if (stok < 1) {
                    System.out.println("Stok miktarı en az 1 olmalıdır. Tekrar girin.");
                } else {
                    stoklar[i] = stok;
                    break;
                }
            }

            System.out.print("Değerlendirme Puanı (5 üzerinden): ");
            puanlar[i] = Double.parseDouble(scanner.nextLine());
        }

        System.out.print("\nÜrünleri hangi kritere göre sıralamak istersiniz? (name/stock/rating): ");
        String kriter = scanner.nextLine();

        System.out.print("Sıralama türü artan mı azalan mı olsun? (artan/azalan): ");
        String yon = scanner.nextLine();

        Integer[] indeksler = new Integer[urunSayisi];
        for (int i = 0; i < urunSayisi; i++) indeksler[i] = i;

        Comparator<Integer> comparator;
        if (kriter.equals("name")) {
            comparator = Comparator.comparing(i -> urunAdlari[i]);
        } else if (kriter.equals("stock")) {
            comparator = Comparator.comparingInt(i -> stoklar[i]);
        } else {
            comparator = Comparator.comparingDouble(i -> puanlar[i]);
        }

        if (yon.equals("azalan")) {
            comparator = comparator.reversed();
        }

        Arrays.sort(indeksler, comparator);

        System.out.println("\nSıralanmış Ürünler:");
        for (int idx : indeksler) {
            System.out.printf("%s - Fiyat: %.2f, Stok: %d, Değerlendirme: %.1f%n",
                    urunAdlari[idx], fiyatlar[idx], stoklar[idx], puanlar[idx]);
        }

        ArrayList<String> sepetAdlar = new ArrayList<>();
        ArrayList<Integer> sepetAdetler = new ArrayList<>();

        while (true) {
            System.out.print("\nSepete ürün eklemek ister misiniz? (Evet/Hayır): ");
            String cevap = scanner.nextLine();

            if (cevap.equals("Hayır")) {
                if (sepetAdlar.size() < 2) {
                    System.out.println("Sepette en az 2 ürün olmalıdır. Ürün eklemeye devam edin.");
                    continue;
                }
                break;
            }

            if (!cevap.equals("Evet")) {
                System.out.println("Lütfen geçerli bir cevap giriniz.");
                continue;
            }

            System.out.print("Eklemek istediğiniz ürünün adı: ");
            String urunAdi = scanner.nextLine();

            int urunIndex = -1;
            for (int i = 0; i < urunSayisi; i++) {
                if (urunAdlari[i].equalsIgnoreCase(urunAdi)) {
                    urunIndex = i;
                    break;
                }
            }

            if (urunIndex == -1) {
                System.out.println("Böyle bir ürün bulunamadı. Lütfen tekrar deneyin.");
                continue;
            }

            int adet;
            while (true) {
                System.out.print("Eklemek istediğiniz adet: ");
                adet = Integer.parseInt(scanner.nextLine());
                if (adet > stoklar[urunIndex]) {
                    System.out.println("Stokta yeterli ürün yok. Mevcut stok: " + stoklar[urunIndex] + ". Lütfen yeni bir adet girin.");
                } else {
                    break;
                }
            }

            stoklar[urunIndex] -= adet;
            sepetAdlar.add(urunAdlari[urunIndex]);
            sepetAdetler.add(adet);
            System.out.println(urunAdlari[urunIndex] + " sepetinize eklendi.");
        }

        double[] sepetFiyatlar = new double[sepetAdlar.size()];
        for (int i = 0; i < sepetAdlar.size(); i++) {
            for (int j = 0; j < urunSayisi; j++) {
                if (urunAdlari[j].equals(sepetAdlar.get(i))) {
                    sepetFiyatlar[i] = fiyatlar[j];
                    break;
                }
            }
        }

        double[] indirimliSepetFiyatlar = sepetFiyatlar.clone();
        for (int i = 0; i < sepetAdlar.size() - 1; i++) {
            if (indirimliSepetFiyatlar[i] > indirimliSepetFiyatlar[i + 1]) {
                indirimliSepetFiyatlar[i] = indirimliSepetFiyatlar[i] - indirimliSepetFiyatlar[i + 1];
            }
        }

        System.out.println("\nSepetiniz:");
        double toplamTutar = 0;
        for (int i = 0; i < sepetAdlar.size(); i++) {
            double toplam = indirimliSepetFiyatlar[i] * sepetAdetler.get(i);
            toplamTutar += toplam;
            System.out.printf("%s - Adet: %d, Toplam Fiyat: %.2f%n",
                    sepetAdlar.get(i), sepetAdetler.get(i), toplam);
        }

        System.out.printf("Sepet Toplamı: %.2f%n", toplamTutar);

        scanner.close();
    }
}
