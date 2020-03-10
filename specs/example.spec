Getting Started with Gauge
==========================

This is an executable specification file. This file follows markdown syntax. Every heading in this file denotes a scenario. Every bulleted point denotes a step.
To execute this specification, use `mvn test`


Get Started
-----------

* HepsiBurada.com sayfası açılır ve giriş yapılır
* Login kontrolü yapılır. Eğer login başarılı olmuşsa sepet tutar kontrol edilir
* Anasayfa'ya gidilir
* Rastgele bir kategorinin üzerine gelinir ve alt kategorilerden rastgele bir alt kategori seçilir
* Rastgele bir marka seçilir
* Fiyat aralığına "10" ile "4000" değerleri girilir ve filtrelenir
* Rastgele bir ürün seçilir. Ürünün ismi ve tutar bilgisi csv dosyasına yazdırılır
* Ürün detay sayfasında ilk olarak bir önceki sayfada aldığınız ürünün fiyatı ile detay sayfasındaki fiyatı karşılaştırılıp kontrol edilir
* Seçilen ürün sepete eklenir
* Write the product name and the price to csv file
* Eklenen ürün adeti sayısı ile sepetin üzerindeki sayı kontrol edilir ve sepete gidilir
* Go shopping cart page
* Adet sayısı "2" arttırılır ve tutar kontrol edilir
* Ürün toplamı değeri ve kargo tutarı csv dosyasına yazdırılır
* Complete the shopping
* Wait "2" seconds
* Adres bilgileri girilir
* Ödeme tipi kredi kartı seçilir
* Kredi kartı bilgileri girilir ve anasayfaya gidilir
* Sepete tıklanır ve sepet temizlenir
* Adreslerim alanına gidilir ve eklenen adresler silinir
