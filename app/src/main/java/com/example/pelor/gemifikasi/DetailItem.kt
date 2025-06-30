package com.example.pelor.gemifikasi

data class SejarahItem(
    val title: String,
    val sejarah: String
)

data class KategoriDetail(
    val category: String,
    val items: List<SejarahItem>
)

val kategoriDetails = listOf(
    KategoriDetail(
        category = "Sejarah",
        items = listOf(
            SejarahItem(
                title = "Balai Adat Melayu",
                sejarah = """
                    Balai Adat Melayu di Pulau Penyengat merupakan pusat kegiatan adat dan budaya masyarakat Melayu. Bangunan ini menjadi tempat berkumpulnya para tetua adat, tokoh masyarakat, dan warga untuk menyelenggarakan berbagai upacara adat, musyawarah, serta kegiatan budaya lainnya. Arsitekturnya mencerminkan gaya khas Melayu dengan ukiran kayu yang sarat makna filosofis.

                    Sebagai simbol pelestarian tradisi, Balai Adat Melayu memainkan peran penting dalam menjaga keberlangsungan nilai-nilai luhur yang diwariskan dari generasi ke generasi. Di sini, berbagai keputusan penting yang berkaitan dengan adat dan kehidupan masyarakat diambil melalui musyawarah mufakat.

                    Selain fungsi sosial dan budaya, Balai Adat Melayu juga menjadi tempat pendidikan informal di mana generasi muda diajarkan tentang adat istiadat, sejarah, dan nilai-nilai moral masyarakat Melayu. Hal ini menjadikan balai ini sebagai lembaga pendidikan budaya yang vital.

                    Keberadaan Balai Adat Melayu hingga kini menunjukkan komitmen masyarakat Pulau Penyengat dalam melestarikan warisan budaya mereka. Balai ini tidak hanya menjadi saksi bisu sejarah, tetapi juga terus hidup sebagai bagian dari kehidupan sehari-hari masyarakat.
                """.trimIndent()
            ),
            SejarahItem(
                title = "Benteng Bukit Kursi",
                sejarah = """
                    Meriam Bukit Kursi adalah peninggalan bersejarah dari masa Kesultanan Riau-Lingga yang terletak di atas bukit di Pulau Penyengat. Meriam ini dulunya digunakan sebagai alat pertahanan utama melawan serangan dari penjajah dan bajak laut yang sering mengincar wilayah perairan Kesultanan.

                    Posisi meriam yang strategis menghadap ke laut menunjukkan pentingnya lokasi ini dalam sistem pertahanan kerajaan. Selain fungsinya sebagai senjata, meriam ini juga menjadi simbol perlawanan dan keberanian rakyat Melayu dalam mempertahankan tanah air mereka.

                    Hingga saat ini, Meriam Bukit Kursi menjadi saksi bisu perjuangan dan sering dikunjungi oleh wisatawan yang ingin melihat langsung jejak sejarah perlawanan lokal. Lokasinya yang berada di atas bukit juga menawarkan panorama laut lepas yang indah.

                    Keberadaan meriam ini mengingatkan generasi muda akan pentingnya semangat juang dan patriotisme dalam mempertahankan kedaulatan dan kehormatan bangsa.
                """.trimIndent()
            ),
            SejarahItem(
                title = "Gedung Tabib",
                sejarah = """
                    Gedung Tabib merupakan salah satu fasilitas penting pada masa Kesultanan Riau-Lingga yang digunakan sebagai tempat pelayanan kesehatan tradisional. Gedung ini menjadi tempat tinggal sekaligus praktik para tabib kerajaan yang ahli dalam pengobatan tradisional Melayu, termasuk ramuan herbal, pijat, dan terapi alami lainnya.

                    Dibangun dengan gaya arsitektur khas Melayu yang menggabungkan unsur Timur Tengah, gedung ini menjadi bukti bahwa sistem kesehatan masyarakat sudah terorganisasi bahkan sebelum era modernisasi. Tabib-tabib yang bertugas di gedung ini biasanya merupakan orang pilihan dengan pengetahuan luas dalam ilmu pengobatan warisan leluhur.

                    Kini, Gedung Tabib menjadi bagian penting dari pelestarian sejarah dan kebudayaan, menunjukkan bahwa kesehatan masyarakat telah menjadi perhatian utama sejak zaman dahulu. Bangunannya yang masih terawat menjadi saksi bisu perkembangan ilmu pengobatan tradisional di Pulau Penyengat.

                    Kunjungan ke Gedung Tabib memberikan wawasan tentang bagaimana masyarakat Melayu mengintegrasikan ilmu pengobatan tradisional dalam kehidupan sehari-hari mereka, serta menghargai peran tabib dalam menjaga kesehatan masyarakat.
                """.trimIndent()
            ),
            SejarahItem(
                title = "Makam Engku Putri",
                sejarah = """
                    Makam Engku Putri adalah tempat peristirahatan terakhir dari Engku Putri Raja Hamidah, istri dari Sultan Mahmud Syah III, yang dikenal luas sebagai sosok wanita yang bijaksana, berwibawa, dan sangat berperan dalam perkembangan agama dan kebudayaan Melayu. Beliau dikenal karena perannya dalam pembangunan Masjid Raya Sultan Riau yang megah, serta menjadi tokoh penting dalam pelestarian nilai-nilai Islam dan adat istiadat.

                    Makamnya terletak di kompleks pemakaman bangsawan dan dikelilingi oleh bangunan pelindung yang dihiasi ornamen khas Melayu. Makam ini sering diziarahi oleh masyarakat setempat maupun wisatawan yang ingin mengenal lebih dekat sosok perempuan hebat dalam sejarah Kesultanan Riau-Lingga.

                    Engku Putri menjadi simbol kekuatan perempuan dalam sejarah Melayu dan dikenang atas jasa-jasanya yang abadi. Keberadaan makam ini mengingatkan kita akan peran penting perempuan dalam sejarah dan budaya Melayu.

                    Ziarah ke Makam Engku Putri memberikan kesempatan untuk merenungkan kontribusi beliau dalam membentuk identitas budaya dan spiritual masyarakat Melayu di Pulau Penyengat.
                """.trimIndent()
            ),
            SejarahItem(
                title = "Makam Raja Ali Haji",
                sejarah = """
                    Raja Ali Haji adalah salah satu tokoh intelektual paling berpengaruh dalam sejarah budaya Melayu. Beliau merupakan pengarang dari karya sastra terkenal “Gurindam Dua Belas”, serta tokoh yang pertama kali menyusun sistematika bahasa Melayu baku yang kemudian menjadi fondasi Bahasa Indonesia modern.

                    Makamnya berada di Pulau Penyengat, tempat beliau banyak menghabiskan waktu untuk menulis dan mengajar. Raja Ali Haji dikenal karena dedikasinya dalam memperjuangkan identitas Melayu melalui karya tulis dan pendidikan. Ia juga menjadi penasehat kerajaan dalam urusan sastra dan kebijakan.

                    Kompleks makam Raja Ali Haji kini menjadi situs cagar budaya yang sangat dihormati dan sering dikunjungi oleh para peneliti, pelajar, dan wisatawan. Keberadaan makam ini menjadi simbol kekayaan intelektual dan warisan literasi yang luar biasa bagi bangsa Indonesia.

                    Mengunjungi makam ini memberikan penghormatan kepada seorang tokoh yang telah memberikan kontribusi besar dalam pengembangan bahasa dan sastra Melayu, serta memperkuat identitas budaya bangsa.
                """.trimIndent()
            ),
            SejarahItem(
                title = "Masjid Raya Sultan Riau",
                sejarah = """
                    Masjid Raya Sultan Riau merupakan masjid bersejarah yang dibangun pada tahun 1803 oleh Sultan Mahmud Syah III bersama masyarakat Pulau Penyengat. Keunikan masjid ini terletak pada bahan bangunannya yang menggunakan campuran putih telur sebagai bahan perekat, mencerminkan teknik konstruksi tradisional yang inovatif pada masanya.

                    Masjid ini tidak hanya menjadi tempat ibadah, tetapi juga pusat pendidikan dan dakwah Islam. Masjid dengan warna dominan kuning ini menjadi ikon spiritual Pulau Penyengat dan simbol kejayaan Kesultanan Riau-Lingga dalam menyebarkan agama Islam. Di masjid ini pula dahulu diajarkan ilmu agama, sastra, dan filsafat oleh para ulama terkemuka.

                    Selain itu, masjid ini memiliki arsitektur yang unik dengan 13 kubah dan 4 menara yang melambangkan jumlah rakaat shalat dalam sehari semalam. Di dalam masjid juga terdapat Al-Qur’an tulisan tangan yang menjadi bukti kepedulian terhadap ilmu pengetahuan dan pendidikan.

                    Masjid Raya Sultan Riau kini menjadi situs cagar budaya yang dilestarikan oleh pemerintah dan masyarakat setempat, serta menjadi destinasi wisata religi yang menarik bagi pengunjung dari berbagai daerah.
                """.trimIndent()
            ),
            SejarahItem(
                title = "Rumah Hakim",
                sejarah = """
                    Rumah Hakim adalah kediaman resmi pejabat pengadilan kerajaan pada masa Kesultanan Riau-Lingga yang memiliki peran penting dalam menjalankan hukum adat Melayu. Rumah ini tidak hanya berfungsi sebagai tempat tinggal, tetapi juga sebagai ruang sidang untuk menyelesaikan berbagai perkara adat.

                    Arsitekturnya mencerminkan kemegahan rumah bangsawan Melayu dengan struktur rumah panggung dan ukiran kayu yang penuh filosofi. Rumah ini menjadi simbol bahwa sistem hukum dan keadilan telah berlaku dengan tertib pada masa itu, dengan hakim sebagai tokoh sentral yang dihormati dalam masyarakat.

                    Keberadaan Rumah Hakim menunjukkan bahwa peradaban Melayu sangat menghargai keadilan dan telah menerapkan sistem hukum yang rapi dan manusiawi jauh sebelum pengaruh kolonial datang. Rumah ini juga menjadi tempat pendidikan hukum adat bagi generasi muda.

                    Mengunjungi Rumah Hakim memberikan pemahaman tentang bagaimana masyarakat Melayu mengelola sistem peradilan dan menegakkan hukum berdasarkan nilai-nilai adat dan keadilan.
                """.trimIndent()
            )
        )
    ),
    KategoriDetail(
        category = "Penginapan",
        items = listOf(
            SejarahItem(
                title = "Gedung Tabib",
                sejarah = "Gedung ini dahulu digunakan sebagai tempat pengobatan tradisional di masa kesultanan. Kini menjadi cagar budaya dan museum kecil."
            ),
            SejarahItem(
                title = "Rumah Hakim",
                sejarah = "Dulunya rumah kediaman hakim kerajaan. Desain rumah panggungnya mencerminkan rumah bangsawan Melayu zaman dahulu."
            )
        )
    ),
    KategoriDetail(
        category = "Event & Agenda",
        items = listOf(
            SejarahItem(
                title = "Makam Raja Ali Haji",
                sejarah = "Tempat peristirahatan terakhir dari Raja Ali Haji, pencetus Gurindam Dua Belas dan pelopor bahasa Melayu baku."
            ),
            SejarahItem(
                title = "Makam Engku Putri",
                sejarah = "Engku Putri adalah permaisuri dari Sultan Riau. Makamnya dihormati dan menjadi salah satu situs ziarah di Pulau Penyengat."
            )
        )
    ),
    KategoriDetail(
        category = "Restorant",
        items = listOf(
            SejarahItem(
                title = "Balai Adat Melayu",
                sejarah = "Balai ini digunakan untuk kegiatan musyawarah dan pelestarian adat Melayu. Dihiasi ornamen khas dan sering jadi tempat acara adat."
            )
        )
    )
)