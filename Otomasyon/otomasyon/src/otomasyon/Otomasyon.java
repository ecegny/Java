package otomasyon;
import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
interface UYEISLEM
{
    public void ozlukbilgileri();
}
abstract class BILGI implements UYEISLEM 
{
       String TC;               
       String unvan;            
       String ad;
       String soyad;
       long okulno;          
       String[] dersler;
       long EvTel;
       long CepTel;
       public void yerlestir(String[] dizi) 
    {
       int stop=50;
       for(int i = 0 ; i < dizi.length ;  i++)
       { 
           if("A".equals(dizi[i]))
           {
               ad = dizi[i+1];
               for(int j = i + 2 ; j < stop ; j++)
               {
                    if(!"D".equals(dizi[j]))
                        ad = ad + " " + dizi[j];
                    else
                        stop = 0;
               } 
           }
           if("D".equals(dizi[i]))
           {
               soyad = dizi[i+1];
                for(int j = i + 1 ; j < stop ; j++)
               {
                   if(!"S".equals(dizi[j]))
                       soyad = soyad + " " + dizi[j];
                   else
                    stop=0;
               } 
           }
       }
       
        TC = dizi[0];
        okulno = Long.parseLong(dizi[1]);
        unvan = dizi[2];
        EvTel = Long.parseLong(dizi[3]);
        CepTel = Long.parseLong(dizi[4]);
    }  
}
class OGRENCI extends BILGI                                                                                              
{  
  @Override
    public void ozlukbilgileri() 
    {
        System.out.printf("\nAdı:%s   OkulNo:%d\nSoyadı:%s  Unvan:%s\nTC:%s  Ev Tel:%d\nCep Tel:%d\n",ad,okulno,soyad,unvan,TC,EvTel,CepTel);
        long no = okulno;
        long NumArr [] = {0,0,0,0,0,0,0,0,0,0};
        int bas = 10;
        for(int i=0;i<10;i++)
        {
           long temp = no % bas;
           NumArr[i]= temp;
           no = no - temp;
           no = no / 10;
        }
    String kisi = ad +" " + soyad;
    long yil = 2000 + (NumArr[5]*10) +(NumArr[4]*1);
    long sira = (NumArr[1]*10) +NumArr[0];
    System.out.printf("%s adlı öğrenci %d yılında %d. sırada okula kayıt olmuştur.\n",kisi,yil,sira);
    } 
}
class OGRETMEN extends BILGI
{
    @Override
    public void ozlukbilgileri() {
       System.out.printf("\nAdı:%s   AkademikNo:%d\nSoyadı:%s  Unvan:%s\nTC:%s  Ev Tel:%d\nCep Tel:%d\n",ad,okulno,soyad,unvan,TC,EvTel,CepTel);
    }   
}
class IDARI extends BILGI
{                                     
    public void uyeekle()
    {
        System.out.println("Eklenecek kisinin TC numarasını giriniz.");
        Scanner scan = new Scanner(System.in);
        String TC_no = scan.next();
        String file_kisi= "kullanicibilgileri";
        String file_uye="uyebilgileri";
        String bilgi = "bos";
        DosyaIslem islem = new DosyaIslem();
        try {
            bilgi = islem.Satir_Bul(TC_no, 0, file_kisi);
        } catch (IOException ex) {
            Logger.getLogger(IDARI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(bilgi=="null")
        {
            String ekle_unvan = "null";
            System.out.println("Eklenecek kisinin adını giriniz.");
            scan.nextLine();
            String ad_ekle = scan.nextLine();
            System.out.println("Eklenecek kisinin ev telefon numarasını giriniz.");
            String evtel_ekle = scan.next();
            System.out.println("Eklenecek kisinin soyadını giriniz.");
            scan.nextLine();
            String soyad_ekle = scan.nextLine();
            System.out.println("Eklenecek kisinin cep telefon numarasını giriniz.");
            String ceptel_ekle = scan.next();
            int dongu = 0;
            while(dongu == 0)
            {
                System.out.println("Eklenecek kisinin unvanını seçeneklerden giriniz.(OGRETMEN,OGRENCI,IDARI)");
                ekle_unvan = scan.next();
                System.out.printf("%s", ekle_unvan);
                if("OGRETMEN".equals(ekle_unvan))
                    dongu = 1;
                if("OGRENCI".equals(ekle_unvan))
                    dongu=1;
                if("IDARI".equals(ekle_unvan))
                    dongu=1;
            }
            System.out.println("Eklenecek kisinin okul(akademik) numarasını giriniz.");
            String okulno_ekle = scan.next();
            String kontrol ;
            int count = 1;
            while(count==1)
            {
                try {
                kontrol = islem.Satir_Bul(okulno_ekle, 1, file_kisi);
                if("null".equals(kontrol))
                {
                    count = 0;
                } 
                else
                {
                    System.out.println("Okul numarası alınmış farklı numara deneyiniz.");
                    okulno_ekle = scan.next();
                }
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(IDARI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.printf("\n%s numaralı kayıdın şifresini giriniz.",okulno_ekle);
            String sifre_ekle = scan.next();
            String eklenecek_uye = TC_no + " " + sifre_ekle;
            String eklenecek_kisi = TC_no + " " + okulno_ekle + " " + ekle_unvan + " " + evtel_ekle + " "+ceptel_ekle + " A " + ad_ekle + " D " + soyad_ekle + " S" ;
            try 
            {
                islem.Satir_Ekle(eklenecek_uye, file_uye);
                islem.Satir_Ekle(eklenecek_kisi, file_kisi);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(IDARI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.printf("\n%s\nKAYIT BAŞARILI\n", eklenecek_kisi);
        
        }
        else
        {
            System.out.printf("Kayıt yapmaya çalıştığınız kişi zaten sistemde bulunmaktadır.");
        }
                                  
    }
    @Override
    public void ozlukbilgileri() {
        System.out.printf("\nAdı:%s   PersonelNo:%d\nSoyadı:%s  Unvan:%s\nTC:%s  Ev Tel:%d\nCep Tel:%d\n",ad,okulno,soyad,unvan,TC,EvTel,CepTel);
    }
}
interface NOTISLEM {    
    public void notgor(long OkulNo);
}
class NOT implements NOTISLEM 
{
    private static String dersadial(String kod) throws IOException 
    {
        DosyaIslem islem = new DosyaIslem(); 
        String dersadi = null;
        String satir;
        String get="dersbilgileri";
        satir=islem.Satir_Bul(kod,0,get);
                    
        String dersad[] = satir.split(" ");
        for(int z=0;z<dersad.length;z++)
        {
            if("A".equals(dersad[z]))
            {
                dersadi=dersad[z+1];
                for(int j=z+2; j<dersad.length;j++)
                {
                    if("B".equals(dersad[j]))
                    {
                        break;
                    }
                    else
                    {
                        dersadi = dersadi + " " + dersad[j];
                    }
                }
            }
        
        
        }
        return dersadi;    
    }    
    @Override
    public void notgor(long OkulNo) 
    { 
        String ogrencino= Long.toString(OkulNo);
        DosyaIslem islem = new DosyaIslem();
        String file="derskayit";
        String satir="null";
        try {
            satir= islem.Satir_Bul(ogrencino,0,file);
        } catch (IOException ex) {
            Logger.getLogger(Otomasyon.class.getName()).log(Level.SEVERE, null, ex);
        }
        if("null".equals(satir))
        {
            System.out.printf("\nÖğrencinin ders kaydı bulunmamaktadır");
        }
        else
        {
           System.out.printf("\n%s ",ogrencino);
           String words[] = satir.split(" ");
           for(int i=1; i<words.length;i++)
            {
                String filename=words[i];                
                String bilgi = null;
               try {
                   bilgi = islem.Satir_Bul(ogrencino,0,filename);
               } catch (IOException ex) {
                   Logger.getLogger(Otomasyon.class.getName()).log(Level.SEVERE, null, ex);
               }
               String ders[] = bilgi.split(" ");
               if("-1".equals(ders[2]) && "-1".equals(ders[4]) && "-1".equals(ders[6]))
               {
                   System.out.printf("", filename);
               }
               else
               {  
                    String dersadi = null;
                    try {
                        dersadi = dersadial(filename);
                    } catch (IOException ex) {
                        Logger.getLogger(Otomasyon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.printf("\n%s ",dersadi);
                    if("-1".equals(ders[2]))
                    {
                        System.out.printf(" ");
                    }
                    else
                    {
                        System.out.printf("\n   Vize Notu: %s Yüzdesi: %s",ders[2],ders[1]);                    
                    }
                     if("-1".equals(ders[4]))
                    {
                        System.out.printf(" ");
                    }
                     else
                    {
                        System.out.printf("\n   Ödev Notu: %s Yüzdesi: %s",ders[4],ders[3]);
                    }
                      if("-1".equals(ders[6]))
                    {
                        System.out.printf(" ");
                    }
                      else
                    {
                        System.out.printf("\n   Final Notu: %s Yüzdesi: 50  ",ders[6]);                    
                    }
                    if(!"HN".equals(ders[7]))
                    {
                        System.out.printf("HN: %s\n",ders[7]);
                    }
               }
            }
        }      
    }  
}
class NOT_YETKI implements NOTISLEM
{
    private static String dersadial(String kod) throws IOException {
        DosyaIslem islem = new DosyaIslem(); 
        String dersadi = null;
        String satir;
        String get="dersbilgileri";
        satir=islem.Satir_Bul(kod,0,get);
                    
        String dersad[] = satir.split(" ");
        for(int z=0;z<dersad.length;z++)
        {
            if("A".equals(dersad[z]))
            {
                dersadi=dersad[z+1];
                for(int j=z+2; j<dersad.length;j++)
                {
                    if("B".equals(dersad[j]))
                    {
                        break;
                    }
                    else
                    {
                        dersadi = dersadi + " " + dersad[j];
                    }
                }
            }
        
        
        }
        return dersadi;    
    }
    @Override
    public void notgor(long OkulNo) { //Öğretmenin veya idarenin herhangi bir öğrenciye ait notlara bakması için
        DosyaIslem islem = new DosyaIslem();
        Scanner scan = new Scanner(System.in);
        System.out.printf("\nNotlarını görmek istediğiniz öğrencinin okul numarasını giriniz.\n");
        String ogrencino;
        ogrencino = scan.next();
        String file="derskayit";
        String satir="null";
        try {
            satir= islem.Satir_Bul(ogrencino,0,file);
        } catch (IOException ex) {
            Logger.getLogger(Otomasyon.class.getName()).log(Level.SEVERE, null, ex);
        }
        if("null".equals(satir))
        {
            System.out.printf("\nÖğrencinin ders kaydı bulunmamaktadır");
        }
        else
        {
           System.out.printf("\n%s ",ogrencino);
           String words[] = satir.split(" ");
           for(int i=1; i<words.length;i++)
            {
                String filename=words[i];                
                String bilgi = null;
               try {
                   bilgi = islem.Satir_Bul(ogrencino,0,filename);
               } catch (IOException ex) {
                   Logger.getLogger(Otomasyon.class.getName()).log(Level.SEVERE, null, ex);
               }
               String ders[] = bilgi.split(" ");
               if("-1".equals(ders[2]) && "-1".equals(ders[4]) && "-1".equals(ders[6]))
               {
                   System.out.printf("", filename);
               }
               else
               {  
                    String dersadi = null;
                    try {
                        dersadi = dersadial(filename);
                    } catch (IOException ex) {
                        Logger.getLogger(Otomasyon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.printf("\n%s ",dersadi);
                    if("-1".equals(ders[2]))
                    {
                        System.out.printf(" ");
                    }
                    else
                    {
                        System.out.printf("\n   Vize Notu: %s Yüzdesi: %s",ders[2],ders[1]);                    
                    }
                     if("-1".equals(ders[4]))
                    {
                        System.out.printf(" ");
                    }
                     else
                    {
                        System.out.printf("\n   Ödev Notu: %s Yüzdesi: %s",ders[4],ders[3]);
                    }
                      if("-1".equals(ders[6]))
                    {
                        System.out.printf(" ");
                    }
                      else
                    {
                        System.out.printf("\n   Final Notu: %s Yüzdesi: 50  ",ders[6]);                    
                    }
                    if(!"HN".equals(ders[7]))
                    {
                        System.out.printf("HN: %s\n",ders[7]);
                    }
               }
            }
        }
    }
    public void notgir(long Okulno) 
    {
        DosyaIslem islem= new DosyaIslem();
       String file = "dersbilgileri";
        String no = Long.toString(Okulno);
        int kontrol = 0;
        while(kontrol!=1)
        {
            try {
                islem.Listele(no,1,file);
            } catch (IOException ex) {
                Logger.getLogger(NOT.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.printf("\nHangi dersinize işlem yapmak istiyorsanız kodunu giriniz.Çıkmak için 0 giriniz.\n");
            Scanner scan = new Scanner(System.in);
            
            String kod = scan.next();
            if(kod.equals("0"))
                    {
                        kontrol=1;
                    }
            String satir = null;
            try {
                satir = islem.Satir_Bul(kod,0,file);
            } catch (IOException ex) {
                Logger.getLogger(NOT.class.getName()).log(Level.SEVERE, null, ex);
            }
            String control[] = satir.split(" ");
            if("null".equals(satir))
            {
                System.out.printf("Dersin kodu bulunmamaktadır");
            }
            else
            {
                if(control[1].equals(no))
                {
                    int sayac =0;
                    while(sayac==0)
                    {
                        System.out.printf("\nÖğrencinin numarasını giriniz.Bitirmek için 1 e basınız.\n");
                        String ogrnno=scan.next();
                        int cikis = Integer.parseInt(ogrnno);
                        String ogrn = null;
                        try {
                            ogrn = islem.Satir_Bul(ogrnno,0,kod);
                        } catch (IOException ex) {
                            Logger.getLogger(NOT.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(cikis==1)
                        {
                             sayac=1;
                        }
                        else if(ogrn.equals("null"))
                        {
                            System.out.printf("\n%s numaralı öğrenci bu dersi almamaktadır", ogrnno);
                        }
                        else
                        {
                            String bilgi[]=ogrn.split(" ");
                            System.out.printf("\n%s nolu ogrencinin VİZE notunu giriniz.Yoksa -1 giriniz\n",ogrnno);
                            String vize=scan.next();
                            bilgi[2]=vize;
                            System.out.printf("\n%s nolu ogrencinin ÖDEV notunu giriniz.Yoksa -1 giriniz\n",ogrnno);
                            String odev=scan.next();
                            bilgi[4]=odev;
                            System.out.printf("\n%s nolu ogrencinin FİNAL notunu giriniz.Yoksa -1 giriniz\n",ogrnno);
                            String fin=scan.next();
                            bilgi[6]=fin;
                            String son=null;
                            son=ogrnno+ " ";
                            for(int i=1;i<bilgi.length;i++)
                            {
                                son = son + bilgi[i]+ " ";
                            }
                            try {
                                islem.Satir_Sil(ogrn,kod);
                                islem.Satir_Ekle(son,kod);
                            } catch (IOException ex) {
                                Logger.getLogger(NOT.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                else
                {
                    System.out.printf("\nDers size ait değildir.\n");
                }
            }
        }   
    }
    public void harfnotu() throws IOException //idarinin harfnotunu belirlemesi için
    {
        DosyaIslem dosya = new DosyaIslem();
        Scanner scan = new Scanner(System.in);
        System.out.printf("\nHarf notunu belirlemek istediğiniz dersin kodunu giriniz.\n");
        String kod=scan.next();
        String filename= "dersbilgileri";
        String dersbilgi= dosya.Satir_Bul(kod,0,filename);
        int sira=0;
        int kalansayisi=0;
        double finalsinir = 50;
        long[] bilgi = new long [500];
        long[] FF = new long[500];
        double[] ortalama = new double [500];
        if("null".equals(dersbilgi))
        {
            System.out.printf("\nYanlış kod girdiniz.\n");
        }
        else
        {
            String file = kod + ".txt";
            FileInputStream fi=new FileInputStream(file);
            BufferedReader buf=new BufferedReader(new InputStreamReader(fi));
            String satir; 
            double genel_ortalama=0;
            double sayac=0;
            while ((satir=buf.readLine())!=null)
            {
                String words[] = satir.split(" ");
                
                if(words[0].equals("0000000000"))
                {
                    System.out.printf("\n");
                } 
                else
                {
                    long no=Long.parseLong(words[0]);
                    double vizeyuzde=Double.parseDouble(words[1]);
                    double vizenot=Double.parseDouble(words[2]);
                    double odevyuzde=Double.parseDouble(words[3]);
                    double odevnot=Double.parseDouble(words[4]);
                    double finalnot=Double.parseDouble(words[6]);
                    
                    double ort = ((vizenot * vizeyuzde)/100)+ ((odevnot * odevyuzde) / 100) + ((finalnot*50) / 100);
                    bilgi[sira]=no;
                    if(finalnot<finalsinir)
                    {
                        FF[kalansayisi]=no;
                        kalansayisi++;
                    }
                    ortalama[sira]=ort;
                    genel_ortalama = genel_ortalama + ort;
                    sira=sira+1;
                    sayac=sayac +1;
                }
            }  
            buf.close();
            fi.close();
            genel_ortalama = genel_ortalama / sayac;
            double toplam=0;
            for(int i=0 ;i<sayac ; i++)
            {
                toplam = toplam + ( (ortalama[i]-genel_ortalama) * (ortalama[i]-genel_ortalama));
            }
            toplam = toplam / sayac;
            double altsinir = 0;
            double ustsinir = 100;
            double ss =Math.sqrt(toplam);
            double s0 = 49 - ss ;
            double s1 = 54 - ss;
            double s2 = 59 - ss;
            double s3 = 65 - ss;
            double s4 = 72- ss;
            double s5 = 79 - ss;
            double s6 = 87 - ss;
            String harfnotu = "HN";
            for(int i =0; i<sayac; i++) //kişinin ortalamasına göre harf notu belirleyip atıyoruz
            {
               if(s6<ortalama[i] && ortalama[i]<=ustsinir)
               {
                   harfnotu = "AA";
               }
               if(s5<ortalama[i] && ortalama[i]<=s6)
               {
                   harfnotu = "BA";                   
               }
               if(s4<ortalama[i] && ortalama[i]<=s5)
               {
                   harfnotu = "BB";                   
               }
               if(s3<ortalama[i] && ortalama[i]<=s4)
               {
                   harfnotu = "CB";  
               }
               if(s2<ortalama[i] && ortalama[i]<=s3)
               {
                   harfnotu = "CC";         
               }
               if(s1<ortalama[i] && ortalama[i]<=s2)
               {
                   harfnotu = "DC";         
               }
               if(s0<ortalama[i] && ortalama[i]<=s1)
               {
                   harfnotu = "DD";                 
               }
               if(altsinir<=ortalama[i] && ortalama[i]<=s0)
               {
                   harfnotu = "FF";                  
               }
                String numara = Long.toString(bilgi[i]);
                String degis = dosya.Satir_Bul(numara,0,kod);
                String words[] = degis.split(" ");
                String yeni= words[0]+" " +words[1]+ " "+words[2]+" " +words[3]+" " +words[4]+" " +words[5]+ " " +words[6]+" "+harfnotu;
                dosya.Satir_Sil(degis,kod);
                dosya.Satir_Ekle(yeni,kod);
            }
            for(int i=0; i<kalansayisi; i++ ) //final notu 50 altında olanları sonradan düzeltiyoruz
            {
                String numara = Long.toString(FF[i]);
                String degis = dosya.Satir_Bul(numara,0,kod);
                String words[] = degis.split(" ");
                String yeni= words[0]+" " +words[1]+ " "+words[2]+" " +words[3]+" " +words[4]+" " +words[5]+ " " +words[6]+" FF";
                dosya.Satir_Sil(degis,kod);
                dosya.Satir_Ekle(yeni,kod);
            }       
        }
                System.out.printf("\n Harf notları sisteme girilmiştir.\n");
    } 
}
interface DERSISLEM {
    public void derskayit(long ID)throws IOException;  //ogrenci derslerini kaydedicek ogretmen onaylayacak idari dersi oluşturacak    
}
abstract class DERS implements DERSISLEM {
    String dersadi;
    String derskodu;
}
class DERS_YETKI extends DERS
{
   @Override
   public void derskayit(long ID) //ders i sistemde oluşturmak için öğretmenin yaptığı kayıt
   {
       DosyaIslem islem = new DosyaIslem();
       Scanner scan = new Scanner(System.in);
       System.out.printf("\nAçmak istediğiniz dersin kodunu giriniz.\n");
       String kod = scan.nextLine();
       String dosya = "dersbilgileri";
       int colom = 0;
       String kod_satir = null;
       try {
           kod_satir = islem.Satir_Bul(kod, colom,dosya );
       } catch (IOException ex) {
           Logger.getLogger(DERS_YETKI.class.getName()).log(Level.SEVERE, null, ex);
       }       
       if("null".equals(kod_satir))
       {           
           System.out.printf("Açmak istediğiniz dersin adını giriniz.(Sadece İngilizce harf kullanınız.)");
           String ders_adi=scan.nextLine();
           String ders_bilgi= kod + " "+ ID +" A "+ ders_adi + " B" ;
           try {
               islem.Satir_Ekle(ders_bilgi, dosya);
           } catch (IOException ex) {
               Logger.getLogger(DERS_YETKI.class.getName()).log(Level.SEVERE, null, ex);
           }
           System.out.printf("\n%s\nListeye eklenmiştir.\n",ders_bilgi );
           System.out.print("\nVize yüzdesini giriniz.(Sadece sayı)\n");
           String vize = scan.next();
           int Vize = Integer.parseInt(vize);
           System.out.printf("\nÖdev yüzdesini giriniz.Yoksa 0 girin.(Sadece sayı)\n");
           String odev = scan.next();
           int Odev=Integer.parseInt(odev);
           while(Odev+Vize!=50)
           {
               System.out.print("\nVize yüzdesi ve ödev yüzdesinin toplamı 50 olmalıdır.\n");
               System.out.print("\nVize yüzdesini giriniz.\n");
               vize = scan.next();
               int Vize_y = Integer.parseInt(vize);
               System.out.printf("\nÖdev yüzdesini giriniz.\n");
               odev = scan.next();
               int Odev_y=Integer.parseInt(odev);
               Odev = Odev_y;
               Vize = Vize_y;
           }
           String ana ="0000000000 "+vize+" -1 "+odev+" -1 50 -1 HN -1";
           try {
               islem.Satir_Ekle(ana, kod);
           } catch (IOException ex) {
               Logger.getLogger(DERS_YETKI.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       else
       {
           System.out.printf("Girmek istediğiniz kod bulunmaktadır tekrar deneyiniz.");
       }
   }
}
class DERS_OGRN extends DERS 
{
    @Override
    public void derskayit(long ID) throws IOException //öğrencinin ders alması için yaptığı kayıt
    { 
        Scanner scan = new Scanner(System.in);
        DosyaIslem islem = new DosyaIslem();
        String A = "A";
        String file1 = "dersbilgileri";
        String file = "derskayit";
        try {
            islem.Listele(A, 2, file1);
        } catch (IOException ex) {
            Logger.getLogger(DERS_OGRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        String kod ;
        String satir;
        String onceki=null;
        String no = Long.toString(ID);
            onceki = islem.Satir_Bul(no, 0, file);        
        if("null".equals(onceki))
        {
            satir = no + " " ;
        }
        else
        {
            satir = no + " ";
            String sira[]=onceki.split(" ");
            for(int a=1;a<sira.length;a++)
            {
                satir = satir + sira[a] + " ";
            }
            islem.Satir_Sil(onceki, file); //önceki yaptığı kayıdı silmek
        }
        int derssayisi=0;
        int i;
        for(i=10; 0<i ; i--)
        {
            System.out.printf("\nKayıt olmak istediğiniz dersin kodunu giriniz.Çıkmak için 0 giriniz.\n");
            kod = scan.next();
            if("0".equals(kod))
            {
                i=0;
            }
            String kontrol = null;            
            kontrol = islem.Satir_Bul(kod, 0, file1);
            if("null".equals(kontrol))
            {
                i = i+1;
                System.out.printf("\nLütfen geçerli bir kod giriniz.\n");
            }
            else
            {
                int control=0;
                String sira[]=satir.split(" ");
                for(int a=1;a<sira.length;a++)
                {
                    if(kod.equals(sira[a]))
                    {
                        control=1;
                    }                 
                }
                if(control==1)
                {
                    System.out.printf("\nBu dersi zaten almışsınız.\n");
                }
                else
                {
                    satir = satir + kod + " ";
                    String def = "0000000000";
                    String defa=islem.Satir_Bul(def, 0, kod);
                    String defau[] = defa.split(" ");
                    String defaul = no + " "+defau[1]+" -1 "+ defau[3]+ " -1 50 -1 HN ";
                    islem.Satir_Ekle(defaul, kod);
                    derssayisi=derssayisi +1;                
                }                
            }
        }
            if(derssayisi==10)
            {
                System.out.printf("\nAlabileceğiniz ders kotasını doldurdunuz\n");
            }
            islem.Satir_Ekle(satir, file);        
    }
}
class DosyaIslem 
{
    public void Satir_Ekle(String data, String filename) throws IOException 
    {
        Writer output;
        filename = filename + ".txt";
        output = new BufferedWriter(new FileWriter(filename, true));
        data = data + "\n";
        output.append(data);
        output.close();
    }
    public String Satir_Bul (String data,int colom, String filename) throws IOException 
    {
           String Satir_Al = "null";
           filename=filename + ".txt";
        try
        {
            FileInputStream fi=new FileInputStream(filename);
            BufferedReader buf=new BufferedReader(new InputStreamReader(fi));
            String satir;           
            while ((satir=buf.readLine())!=null)
            {
                String words[] = satir.split(" ");
                if(words[colom].equals(data))
                {
                    Satir_Al = satir;
                } 
            }
           buf.close();
           fi.close();
        }
        catch(FileNotFoundException f)
        {System.out.println("dosya yok");}
        catch(IOException ioex)
        {System.out.println("başka bir hata oldu"); }
       return Satir_Al;
    }
    public void Satir_Sil(String data, String filename) throws IOException
    {
                String inputFileName = filename + ".txt";
        String outputFileName = "temp.txt";
        String lineToRemove = data;
        try {
        File inputFile = new File(inputFileName);
        File outputFile = new File(outputFileName);
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(lineToRemove)) {
                writer.write(line);
                writer.newLine();
            }
        }        
        reader.close();
        writer.close();
    }
    if (inputFile.delete()) {        
        if (!outputFile.renameTo(inputFile)) {
            throw new IOException("Could not rename " + outputFileName + " to " + inputFileName);
        }
    } else {
        throw new IOException("Could not delete original input file " + inputFileName);
    }
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }            
    }
    public void Listele (String data, int sira , String filename) throws IOException
    {
           filename=filename + ".txt";
           
        try
        {
            FileInputStream fi=new FileInputStream(filename);
            BufferedReader buf=new BufferedReader(new InputStreamReader(fi));
            String satir; 
           
            while ((satir=buf.readLine())!=null)
            {
                String words[] = satir.split(" ");
                if(words[sira].equals(data))
                {
                    System.out.printf("\n%s\n", satir);
                } 
            } 
            buf.close();
            fi.close();
        }
        catch(FileNotFoundException f)
        {System.out.println("dosya yok");}
        catch(IOException ioex)
        {System.out.println("başka bir hata oldu"); }
    
    }
}
public class Otomasyon 
{      
    public static void main(String[] args) throws IOException 
    {
        String TC = null;
        boolean  izin  = false;
        while(izin == false)
        {
           System.out.printf("Tc numaranizi giriniz: \n");
           Scanner scan = new Scanner(System.in);
           TC = scan.next();
           System.out.printf("Sifrenizi giriniz: \n");
           String sifre = scan.next();
           String bos = " ";
           String sat = TC+bos+sifre;
           try
           {
               String a= "uyebilgileri.txt"; 
                FileInputStream fi=new FileInputStream(a);
                BufferedReader buf=new BufferedReader(new InputStreamReader(fi));
                String satir;
                while ((satir=buf.readLine())!=null)
                {
                    if(satir.equals(sat))
                    {
                        izin = true;
                    }
                }
                if(izin == false)
                {
                    System.out.println("TC ya da sifre hatali.\nTekrar denemek için 1 giriniz.\n");  
                    int kontrol = scan.nextInt();
                    if(kontrol != 1) 
                        System.exit(0);
                }   
            }
            catch(IOException ioex)
            {   System.out.println("HATA"); }
        }                                                                         
        String unvan = null;
        String dosya = "kullanicibilgileri";
        DosyaIslem giris = new DosyaIslem();
        String Satir_Al = giris.Satir_Bul(TC, 0, dosya);
        String[] kisi = Satir_Al.split(" ");  
        unvan = kisi[2];
        int sec = 0;
        if("OGRENCI".equals(unvan))
            sec = 1;
        if("OGRETMEN".equals(unvan))
            sec = 2;
        if("IDARI".equals(unvan))
            sec = 3;
        switch(sec)
        {
            case(1):   //OGRENCİ GİRİŞİ
                BILGI ogr = new OGRENCI();
                ogr.yerlestir(kisi);                   
                for(;;)
               { 
                   System.out.printf("\n1.Özlük Bilgileri\n2.Ders İşlemleri\n3.Sınav Sonuçları\n4.Çıkış\n");
                   Scanner scan = new Scanner(System.in);
                   int ogr_sec = scan.nextInt();
                   switch(ogr_sec) //OGRENCI MENÜSÜ BAŞLANGICI
                   {
                        case(1): //Özlük bilgileri ÖĞRENCİ
                          ogr.ozlukbilgileri();
                          break;
                       case(2):  //Ders işlemleri ÖĞRENCİ 
                            int next = 0;
                            while(next != 3)
                            {
                                System.out.printf("\n1.Ders Kayıt\n2.Ders Listesi\n3.Geri Dön\n");
                                next = scan.nextInt();
                                switch(next)
                                {
                                    case (1): 
                                        DERS islem = new DERS_OGRN();
                                        islem.derskayit(ogr.okulno);
                                        break;
                                    case (2):
                                        DosyaIslem liste = new DosyaIslem();
                                        String A = "A";
                                        String file1 = "dersbilgileri";
                                        liste.Listele(A, 2, file1);
                                        break;
                                }
                            }
                            break;
                       case(3): // Sınav sonuçları ÖĞRENCİ
                          NOTISLEM islem = new NOT();
                          islem.notgor(ogr.okulno);
                          break;
                       case(4): //çıkış
                          System.exit(0);
                    }
                }            
            case(2): //OGRETMEN GİRİŞİ
                BILGI ogrt = new OGRETMEN();
                ogrt.yerlestir(kisi);
                 for(;;)
                 { 
                   System.out.printf("\n1.Özlük Bilgileri\n2.Ders Aç\n3.Not İşlemleri\n4.Çıkış\n"); 
                   Scanner scan = new Scanner(System.in);
                   int ogrt_sec = scan.nextInt();
                   switch(ogrt_sec) //OGRETMEN MENÜSÜ BAŞLANGICI
                   {
                        case(1): //Özlük bilgileri ÖĞRETMEN
                            ogrt.ozlukbilgileri();
                          break;
                        case(2):  //Ders oluştur ÖĞRETMEN              
                                DERS ogrt_islem = new DERS_YETKI();                                
                                ogrt_islem.derskayit(ogrt.okulno);
                            break;                                   
                       case(3): //Not Gir ÖĞRETMEN      
                           int ogrt_not = 0;
                           while(ogrt_not != 3)
                            {
                               System.out.printf("\n1.Not Gör\n2.Not Gir\n3.Geri Dön\n");
                               ogrt_not = scan.nextInt();
                               NOT_YETKI not = new NOT_YETKI();
                               switch(ogrt_not)
                               {                                   
                                   case 1:                                       
                                       not.notgor(ogrt.okulno);
                                       break;
                                   case 2:                                       
                                       not.notgir(ogrt.okulno);
                                       break;
                               }
                            }
                          break;   
                       case(4): // çıkış
                          System.exit(0);  
                    }
                } 
            case(3): //İDARİ GİRİŞİ
                BILGI idari = new IDARI();
                IDARI islem = new IDARI() ;
                idari.yerlestir(kisi);
                for(;;)
                { 
                   System.out.printf("\n1.Özlük Bilgileri\n2.Üye Ekle\n3.Not İşlemleri\n4.Çıkış\n");
                   Scanner scan = new Scanner(System.in);
                   int idari_sec = scan.nextInt();
                   switch(idari_sec) //İDARİ MENÜSÜ BAŞLANGICI
                   {
                        case(1): //özlük bilgileri İDARİ
                            idari.ozlukbilgileri();
                            break;
                        case(2): //kayıt işlemleri İDARİ
                            islem.uyeekle();                        
                            break; 
                       case(3): //harf notu belirle İDARİ
                           int idarinot = 0;
                           NOT_YETKI idari_not = new NOT_YETKI();                           
                           while(idarinot != 3)
                            {
                               System.out.printf("\n1.Not Gör\n2.Harf Notu Belirle\n3.Geri Dön\n");
                               idarinot = scan.nextInt();
                              
                               switch(idarinot)
                               {                                   
                                   case 1:                                       
                                       idari_not.notgor(idari.okulno);
                                       break;
                                   case 2:                                       
                                       idari_not.harfnotu();
                                       break;
                               }
                            }                           
                          break;   
                       case(4): //çıkış
                          System.exit(0);  
                    }
                }   
            default:
                System.out.printf(" \n Sisteme erişmek için yetkiniz yok lütfen fakültenizin idarisi ile iletişime geçiniz. \n.");
                System.exit(0);
                break;       
        }   
    }     
}