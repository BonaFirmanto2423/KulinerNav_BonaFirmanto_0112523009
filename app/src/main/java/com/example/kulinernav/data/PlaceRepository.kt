package com.example.kulinernav.data

/** Data awal in-memory (Room belum diwajibkan pada Pertemuan 5). */
object PlaceRepository {
    val initialPlaces: List<Place> = listOf(
        Place(1, "Sate Khas Senayan", "Sate", "Jakarta Pusat", 4.6,
            "Sate ayam dan kambing dengan bumbu kacang kental, cocok untuk makan malam keluarga."),
        Place(2, "Soto Betawi H. Ma'ruf", "Soto", "Jakarta Pusat", 4.5,
            "Soto Betawi legendaris berkuah santan dengan daging dan jeroan sapi."),
        Place(3, "Gudeg Yu Djum", "Gudeg", "Yogyakarta", 4.7,
            "Gudeg kering khas Jogja dengan krecek, telur, dan ayam kampung."),
        Place(4, "Bakmi GM", "Mie", "Jakarta Selatan", 4.4,
            "Bakmi ayam klasik dengan pangsit goreng yang renyah."),
        Place(5, "Nasi Goreng Kambing Kebon Sirih", "Nasi Goreng", "Jakarta Pusat", 4.5,
            "Nasi goreng berbumbu rempah dengan potongan daging kambing empuk."),
        Place(6, "Kerak Telor Bang Udin", "Jajanan", "Jakarta Pusat", 4.2,
            "Jajanan khas Betawi dari beras ketan, telur bebek, dan serundeng."),
        Place(7, "Rawon Setan Mbak Endang", "Rawon", "Surabaya", 4.6,
            "Rawon berkuah hitam pekat dari kluwek, buka sampai larut malam."),
        Place(8, "Ayam Taliwang Moro Seneng", "Ayam Bakar", "Mataram", 4.5,
            "Ayam kampung bakar pedas khas Lombok disajikan dengan plecing kangkung."),
        Place(9, "Pempek Candy", "Pempek", "Palembang", 4.6,
            "Pempek kapal selam dan lenjer dengan cuko yang asam pedas manis."),
        Place(10, "Es Teler 77", "Minuman", "Jakarta Barat", 4.3,
            "Es teler berisi alpukat, kelapa muda, dan nangka dengan susu kental manis."),
        Place(11, "Mie Aceh Seulawah", "Mie", "Jakarta Pusat", 4.5,
            "Mie kuning tebal berbumbu kari khas Aceh, tersedia versi goreng dan kuah."),
        Place(12, "Coto Makassar Daeng", "Coto", "Makassar", 4.6,
            "Coto berkuah kental dari kacang dan rempah, disantap dengan ketupat.")
    )
}
