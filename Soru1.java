import java.util.Scanner;

public class Soru1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Maksimum karakter sayısı belirleyin: ");
        int limit = Integer.parseInt(scanner.nextLine());

        String cumle;
        while (true) {
            System.out.print("Lütfen bir cümle girin: ");
            cumle = scanner.nextLine();
            if (cumle.length() > limit) {
                System.out.println("Uyarı: Girilen cümle karakter limitini aşıyor. Lütfen tekrar girin.");
            } else {
                break;
            }
        }

        String tercih;
        while (true) {
            System.out.print("Büyük/küçük harf duyarlılığı aktif olsun mu? (Evet/Hayır): ");
            tercih = scanner.nextLine();
            if (tercih.equals("Evet") || tercih.equals("Hayır")) {
                break;
            } else {
                System.out.println("Lütfen geçerli bir cevap giriniz.");
            }
        }

        String girdi;
        while (true) {
            System.out.print("Analiz etmek için bir karakter girin: ");
            girdi = scanner.nextLine();
            if (girdi.isEmpty()) {
                System.out.println("Geçerli bir karakter giriniz.");
            } else {
                break;
            }
        }

        char aranan = girdi.charAt(0);
        int sayac = 0;

        for (int i = 0; i < cumle.length(); i++) {
            if (tercih.equals("Evet")) {
                if (cumle.charAt(i) == aranan) {
                    sayac++;
                }
            } else {
                if (Character.toLowerCase(cumle.charAt(i)) == Character.toLowerCase(aranan)) {
                    sayac++;
                }
            }
        }

        System.out.println("Girilen cümlede '" + aranan + "' harfi toplamda " + sayac + " defa geçmektedir.");

        scanner.close();
    }
}