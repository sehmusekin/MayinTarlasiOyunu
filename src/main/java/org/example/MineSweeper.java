package org.example;

import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private int satirSayisi;
    private int sutunSayisi;
    private char[][] mayinTarlasi;
    private boolean[][] mayinlar;
    private boolean[][] acikAlanlar;
    private int toplamMayinSayisi;

    public MineSweeper(int satirSayisi, int sutunSayisi) {
        this.satirSayisi = satirSayisi;
        this.sutunSayisi = sutunSayisi;
        this.mayinTarlasi = new char[satirSayisi][sutunSayisi];
        this.mayinlar = new boolean[satirSayisi][sutunSayisi];
        this.acikAlanlar = new boolean[satirSayisi][sutunSayisi];
        this.toplamMayinSayisi = (satirSayisi * sutunSayisi) / 4;
        mayinlariYerlestir();
        tarlayiHazirla();
    }

    private void mayinlariYerlestir() {
        Random random = new Random();
        int mayinYerleştirilen = 0;

        while (mayinYerleştirilen < toplamMayinSayisi) {
            int satir = random.nextInt(satirSayisi);
            int sutun = random.nextInt(sutunSayisi);

            if (!mayinlar[satir][sutun]) {
                mayinlar[satir][sutun] = true;
                mayinYerleştirilen++;
            }
        }
    }

    private void tarlayiHazirla() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                mayinTarlasi[i][j] = '-';
            }
        }
    }

    private void tarlaYazdir() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(mayinTarlasi[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int etraftakiMayinlariSay(int satir, int sutun) {
        int mayinSayisi = 0;
        int[][] yonler = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},         {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] yon : yonler) {
            int yeniSatir = satir + yon[0];
            int yeniSutun = sutun + yon[1];

            if (yeniSatir >= 0 && yeniSatir < satirSayisi && yeniSutun >= 0 && yeniSutun < sutunSayisi && mayinlar[yeniSatir][yeniSutun]) {
                mayinSayisi++;
            }
        }
        return mayinSayisi;
    }

    public void oyunuBaslat() {
        Scanner scanner = new Scanner(System.in);
        boolean oyunDevam = true;
        int kalanAlan = satirSayisi * sutunSayisi - toplamMayinSayisi;

        System.out.println("Mayın Tarlası Oyununa Hoşgeldiniz!");
        tarlaYazdir();

        while (oyunDevam) {
            System.out.print("Satır giriniz: ");
            int satir = scanner.nextInt();
            System.out.print("Sütun giriniz: ");
            int sutun = scanner.nextInt();

            if (satir < 0 || satir >= satirSayisi || sutun < 0 || sutun >= sutunSayisi) {
                System.out.println("Geçersiz konum. Lütfen tekrar deneyin.");
                continue;
            }

            if (mayinlar[satir][sutun]) {
                System.out.println("Mayına bastınız! Oyunu kaybettiniz.");
                oyunDevam = false;
            } else {
                int mayinSayisi = etraftakiMayinlariSay(satir, sutun);
                mayinTarlasi[satir][sutun] = (char) (mayinSayisi + '0');
                acikAlanlar[satir][sutun] = true;
                kalanAlan--;

                tarlaYazdir();

                if (kalanAlan == 0) {
                    System.out.println("Tüm güvenli alanları açtınız! Oyunu kazandınız.");
                    oyunDevam = false;
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Satır sayısını giriniz: ");
        int satirSayisi = scanner.nextInt();
        System.out.print("Sütun sayısını giriniz: ");
        int sutunSayisi = scanner.nextInt();

        MineSweeper oyun = new MineSweeper(satirSayisi, sutunSayisi);
        oyun.oyunuBaslat();

        scanner.close();
    }
}
