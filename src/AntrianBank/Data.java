package AntrianBank;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import java.util.Queue;

public class Data {
    protected String nama;
    protected String keperluan;
    protected int no, numberRiwayat;

    Scanner input = new Scanner(System.in);
    Queue<String> ListNama = new LinkedList<>();
    Queue<String> ListKeperluan = new LinkedList<>();
    Queue<Integer> ListNo = new LinkedList<>();

    ArrayList<String> RiwayatNama = new ArrayList<>();
    ArrayList<String> RiwayatKeperluan = new ArrayList<>();
    ArrayList<Integer> noAntrian = new ArrayList<>();

    public void SetNama() {
        System.out.print("\n\t\t\t\tNama\t\t: ");
        nama = input.nextLine();
        ListNama.add(nama);
        RiwayatNama.add(nama);
    }

    public void SetKeperluan() {
        System.out.print("\t\t\t\tKeperluan\t: ");
        keperluan = input.nextLine();
        ListKeperluan.add(keperluan);
        RiwayatKeperluan.add(keperluan);
    }

    public int SetNo() {
        no++;
        ListNo.add(no);
        return no;
    }

    public void GetData() throws InterruptedException, IOException {
        if (isEmpty()) {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("\n\t\t\tMaaf Data Masih Kosong");
            Thread.sleep(3000);
        } else {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("\n\t\t\t================================================");
            System.out.print("\t\t\tNama\t|\tKeperluan\t|\tNo Antri\n");
            System.out.println("\t\t\t================================================");
            for (int i = 0; i < ListNama.size(); i++) {
                System.out.print("\t\t\t"+ListNama.toArray()[i]+"\t|\t"+ListKeperluan.toArray()[i]+"\t\t|\t"+ListNo.toArray()[i]);
                System.out.println("\n");
            }
            System.out.print("\n\t\t\t========================================================");
            Thread.sleep(3000);
        }
    }

    public int NoRiwayat() {
        numberRiwayat++;
        noAntrian.add(numberRiwayat);
        return no;
    }

    public void GetRiwayat() throws InterruptedException, IOException {
        if (noAntrian.isEmpty()) {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("\n\t\t\tMaaf Data Masih Kosong");
            Thread.sleep(2000);
        } else {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("\n\t\t\t========================================================");
            System.out.print("\t\t\tNama\t|\tKeperluan\t|\tNo Antri\n");
            System.out.println("\t\t\t========================================================");
            for (int i = 0; i < RiwayatNama.size(); i++) {
                System.out.print("\t\t\t"+RiwayatNama.get(i)+"\t|\t"+RiwayatKeperluan.get(i)+"\t\t|\t"+noAntrian.get(i));
                System.out.println("\n");
            }
            System.out.print("\n\t\t\t========================================================");
            Thread.sleep(3000);
        }
    }

    public boolean isEmpty() {
        return ListNo.isEmpty();
    }

    Connection connect;
    public void dbGetStatus() throws SQLException {
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/quebank", "irfan", "root");
        if ( connect != null) {
            System.out.println("\t\t\t\t\tConnection Success");
            
        } else{
            System.out.println("Tolong check database dan koneksi anda");
        }
    }

    public void dbSetData () {
        Date JavaDate = new Date();
        java.sql.Date MySQLDate = new java.sql.Date(JavaDate.getTime());
        try {
            PreparedStatement ps = connect.prepareStatement("insert into quebanklogs values (?, ?, ?, ?)");
            ps.setString(1, nama);
            ps.setString(2, keperluan);
            ps.setInt(3, NoRiwayat());
            ps.setDate(4, MySQLDate);

            ps.addBatch();
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}