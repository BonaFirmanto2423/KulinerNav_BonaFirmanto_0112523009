# KulinerNav — Daftar Tempat Kuliner

Tugas Pertemuan 5 — Multi-Screen Navigation Challenge (tema NIM akhir 8–9: **Daftar Tempat / Kuliner**).

## Fitur
- **Home**: `LazyColumn` berisi 10 tempat kuliner (data in-memory), FAB *Tambah*, menu *Tes buka ID invalid (999)*.
- **Detail**: membuka data sesuai `id`; tombol Edit dan Hapus (dengan dialog konfirmasi).
- **Form Add/Edit**: 5 input (nama, kategori, lokasi, rating, deskripsi), validasi (nama min. 3 karakter, field wajib, rating 1.0–5.0), tombol **Simpan**, **Batal**, dan **Reset**.
- **Invalid ID**: Detail menampilkan halaman "tidak ditemukan" bila ID tidak ada.
- Back (tombol panah / system back) mengikuti back stack.

## Route / Destination (type-safe, `kotlinx.serialization`)
| Route | Argument | Keterangan |
|---|---|---|
| `Home` | – | start destination |
| `Detail(id: Int)` | id wajib | dibaca dengan `backStackEntry.toRoute<Detail>()` |
| `PlaceForm(id: Int = -1)` | id opsional | `-1` = tambah, selain itu edit |

```
Home ──klik item──▶ Detail(id) ──Edit──▶ PlaceForm(id)
  │                    ▲   │                  │ Simpan/Batal → popBackStack()
  │                    │   └─Hapus→ popBackStack(Home)
  └──Tambah──▶ PlaceForm() ──Simpan──▶ Detail(newId) (popUpTo Home)
```

## Struktur
```
app/src/main/java/com/example/kulinernav/
├── MainActivity.kt            // hanya setContent + tema
├── data/Place.kt, PlaceRepository.kt
├── navigation/Routes.kt       // route type-safe
├── navigation/KulinerNavHost.kt // satu-satunya pemegang NavController
└── ui/
    ├── PlaceViewModel.kt
    ├── screens/HomeScreen.kt, DetailScreen.kt, PlaceFormScreen.kt  // data + callback
    └── theme/Theme.kt
```
Semua screen hanya menerima data + callback (tidak menerima `NavController`), sehingga mudah di-preview dan diuji.

## Cara Menjalankan
1. Clone repo, buka folder di **Android Studio** (Ladybug 2024.2 atau lebih baru).
2. Tunggu Gradle Sync selesai (JDK 17 bawaan Android Studio).
3. Pilih emulator (API 24+) lalu klik **Run ▶**.

## Known Limitation
- Data tersimpan in-memory; perubahan hilang saat aplikasi ditutup (Room belum dipakai).

## Deklarasi Penggunaan AI
Saya menggunakan AI generatif sebagai alat bantu. Tools: Claude.
Bagian yang dibantu: _(isi sendiri, mis. contoh struktur navigation & form)_.
Saya memahami seluruh kode yang dikumpulkan dan bersedia menjelaskan atau memodifikasinya.
