# CLAUDE.md - D'Classic Book



#### Overview

D'Classic book merupakan sebuah aplikasi android yang memberikan pengalaman bagi pengguna agar dapat menjelajahi berbagai koleksi buku klasik pilihan, baik genre fiction maupun nonfiction. Mplikasi ini dirancang untuk memberikan pengalaman belanja yang praktis dan nyaman. Dilengkapi dengan navigasi yang mudah, pengguna bisa menelusuri daftar buku unggulan, mencari lokasi fisik toko, serta mengisi formulir pemesanan tanpa ribet. Base aplikasi ini menggunakan Bahasa inggris.



#### Tech Stack

Languages \& Frameworks

Java - Bahasa utama untuk logic

XML - Untuk layout Android

Android SDK - Framework Android

RecyclerView - Untuk list books

SharedPreferences - Untuk simpan user preferences (optional)





#### Architecture Pattern

Mediator Pattern - Single entry point untuk semua data requests

Handler Pattern - Setiap fitur ada handler-nya sendiri

DRY Principle - Reusable components dan utilities



#### Project Structure

dclassicbook/

├── app/

│   ├── manifests/

│   │   └── AndroidManifest.xml          <-- \[PENTING] Semua Activity baru WAJIB terdaftar di sini

│   │

│   ├── java/

│   │   └── com.example.dclassicbook/         <-- Package utama sesuai nama project Anda

│   ├── application/               ← APPLICATION LAYER (Logic)

│   │   ├── Mediator.java          ← Entry point utama

│   │   └── handler/

│   │       ├── Get(FeatureActivity)Handler.java

│   │       │

│   │       ├── data/

│   │       │   └── (Feature)Data.java        <-- Tempat menyimpan ArrayList data statis

│   │       │

│   │       ├── models/

│   │       │   └── (Feature).java            <-- Blueprint / POJO Class yang berisi atribut

│   │       │

│   │       └── ui/                      <-- Tempat berkumpulnya semua halaman (Activity)

│   │           ├── (Feature)Activity.java    <-- Otak Java untuk Halaman

│   │

│   └── res/                             <-- Tempat semua aset visual, desain layout, dan nilai statis

│       ├── drawable/                    <-- Tempat menyimpan foto cover, ikon SVG (XML), dan background

│       │   ├── bg\_button\_primary.xml    <-- Desain bentuk tombol custom

│       │   ├── ic\_nav\_home.xml          <-- Ikon untuk navbar home

│       │   ├── ic\_nav\_booklist.xml      <-- Ikon navbar ke halaman booklist

│       │   ├── ic\_nav\_ourstore          <-- Ikon untuk ke halaman ourstore

│       │   ├── ic\_nav\_logout.xml        <-- Ikon untuk logout

│       │   ├── ic\_nav\_hamburg.xml       <-- Ikon hamburg untuk membuka sidebar

│       │   ├── ic\_nav\_profile.xml       <-- Ikon profile (static tidak bisa dibuka)

│       │

│       ├── layout/                      <-- Tempat semua file desain tampilan (XML Layout)

│       │   ├── activity\_(feature).xml   <-- Desain tampilan untuk Halaman

│       │   └── item\_(feature).xml       <-- Desain satu kotak/baris kartu buku untuk daftar

│       │

│       ├── values/                      <-- Tempat menyimpan konfigurasi nilai global

│       │   ├── colors.xml               <-- Tempat mendaftarkan palet warna aplikasi (Hex Code)

│       │   ├── strings.xml              

│       │   └── themes / theme.xml  <-- Pengaturan tema gaya aplikasi (font style termasuk warna)

│       │

│       └── mipmap/                      <-- Khusus untuk menyimpan ikon peluncur aplikasi (App Icon)

│           └── ic\_launcher.png

│

└── Gradle Scripts/                      <-- File konfigurasi sistem kompilasi Android

&#x20;   ├── build.gradle (Project)

&#x20;   └── build.gradle (Module: app)       <-- Tempat menambahkan library tambahan jika diperlukan



#### Code Architecture

\- models : Pure data models tanpa logic apapun. Hanya berisi data fields

\- ui : Activities, Fragments, Adapters, dan UI utilities. Layer ini hanya bertanggung jawab untuk display.

\- data : Berisi hardcoded data buku, dan data store.

\- mediators : single entry point untuk semua data requests. Semua Activity/Fragment harus request data lewat Mediator, bukan langsung ke Handler.

\- handler : setiap fitur data punya handler-nya sendiri. Handler handle filtering, sorting, transformasi data.



#### Naming

Java Classes

Activities: \[Nama]Activity.java → HomeActivity.java, BookListActivity.java

Adapters: \[Item]Adapter.java → BookAdapter.java

Handlers: \[Aksi]\[Entity]Handler.java → GetBooksHandler.java, GetTopBooksHandler.java

Models: \[Entity].java → Book.java, User.java

Utilities: \[Fungsi]Helper.java → UIHelper.java, DateHelper.java



XML Layouts

Activities: activity\_\[nama].xml → activity\_home.xml, activity\_booklist.xml

Items: item\_\[nama].xml → item\_book.xml

Fragments: fragment\_\[nama].xml



#### Patterns We Use

\- Memberikan dan menyampaikan plan terlebih dahulu sebelum melakukan code

\- Gunakan color dan themes untuk warna dan teks

\- Menunggu perstujuan plan terlebih dahulu

\- Reusable Layouts (DRY)

\- Get Data Melalui Mediator

\- Semua Activity baru WAJIB terdaftar andoridmanifest

\- Input user untuk login disimpan dalam global variable



#### LARANGAN!!!!!!

\- Jangan pernah melakukan push, commit, add dan pull request ke GitHub

\- Jangan tambahkan backend apapun

\- Jangan mengubah global style yang sudah disiapkan

\- Jangan mengulang - ulang pekerjaan yang bisa digunakan Kembali

