package com.example.blesdktest

interface Oprate {
    companion object {
        const val PWD_COMFIRM = "1.Kata Sandi Perangkat - Verifikasi"
        const val PERSONINFO_SYNC = "2.Informasi Pribadi - Pengaturan"
        const val SETTING_FIRST = "<-- Operasikan 1, 2 terlebih dahulu"
        const val PWD_MODIFY = "Kata Sandi Perangkat - Ubah"
        const val TEMPTURE_DETECT_START = "Mengukur Suhu - Mulai"
        const val TEMPTURE_DETECT_STOP = "Mengukur Suhu - Selesai"
        const val HEART_DETECT_START = "Mengukur Detak Jantung - Mulai"
        const val HEART_DETECT_STOP = "Mengukur Detak Jantung - Selesai"
        const val BP_DETECT_START = "Ukur tekanan darah - mulai"
        const val BP_DETECT_STOP = "Mengukur Tekanan Darah - Selesai"
        const val BP_DETECTMODEL_SETTING = "Mode Tekanan Darah - Pengaturan"
        const val BP_DETECTMODEL_SETTING_ADJUSTE =
            "Mode Tekanan Darah [Penyesuaian Dinamis] - Pengaturan"
        const val BP_DETECTMODEL_SETTING_ADJUSTE_CANCEL =
            "Mode Tekanan Darah [Penyesuaian Dinamis] - Batal"
        const val BP_DETECTMODEL_READ = "Mode Tekanan Darah - Baca"
        const val SPORT_CURRENT_READ = "Jumlah langkah saat ini - baca"
        const val CAMERA_START = "Mode Foto - Mulai"
        const val CAMERA_STOP = "Mode Foto - Berhenti"
        const val ALARM_SETTING = "Jam Alarm - Pengaturan"
        const val ALARM_READ = "jam alarm - baca"
        const val ALARM_NEW_READ = "Jam Alarm Baru - Baca"
        const val ALARM_NEW_ADD = "Jam Alarm Baru - Ditambahkan"
        const val ALARM_NEW_MODIFY = "Jam Alarm Baru - Ubah"
        const val ALARM_NEW_DELETE = "Alarm Baru - Hapus"
        const val ALARM_NEW_LISTENER = "Jam alarm baru - dengarkan perubahan status"
        const val LONGSEAT_SETTING_OPEN = "menetap - terbuka"
        const val LONGSEAT_SETTING_CLOSE = "menetap - mati"
        const val LONGSEAT_READ = "menetap - membaca"
        const val LANGUAGE_CHINESE = "Pengaturan Bahasa - Cina"
        const val LANGUAGE_ENGLISH = "Pengaturan Bahasa - Bahasa Inggris"
        const val BATTERY = "Status Baterai - Baca"
        const val NIGHT_TURN_WRIST_OPEN = "Rotasi Pergelangan Malam - Buka"
        const val NIGHT_TURN_WRIST_CLOSE = "Rotasi Pergelangan Malam - Tertutup"
        const val NIGHT_TURN_WRIST_READ = "Putaran Pergelangan Malam - Baca"
        const val NIGHT_TURN_WRIST_CUSTOM_TIME =
            "Rotasi pergelangan tangan di malam hari - waktu khusus"
        const val NIGHT_TURN_WRIST_CUSTOM_TIME_LEVEL = "Pergelangan Malam - Waktu dan Level Kustom"
        const val FINDPHONE = "Ponsel anti hilang"
        const val CHECK_WEAR_SETING_OPEN = "Deteksi keausan - buka"
        const val CHECK_WEAR_SETING_CLOSE = "Deteksi keausan - mati"
        const val FINDDEVICE_SETTING_OPEN = "Perangkat anti-hilang-terbuka"
        const val FINDDEVICE_SETTING_CLOSE = "Perangkat anti hilang"
        const val FINDDEVICE_READ = "Perangkat anti-hilang-baca"
        const val DEVICE_COUSTOM_READ = "personalisasi-baca"
        const val DEVICE_COUSTOM_SETTING = "Personalisasi - Pengaturan"
        const val DEVICE_ECG_ALWAYS_OPEN = "EKG Biasanya On-On"
        const val DEVICE_ECG_ALWAYS_CLOSE = "EKG biasanya on-off"
        const val SOCIAL_MSG_SETTING = "Pengingat Pesan Sosial 1 Paket - Pengaturan"
        const val SOCIAL_MSG_SETTING2 = "Pengingat Pesan Sosial 2 Paket - Penyiapan"
        const val SOCIAL_MSG_READ = "Pengingat Pesan Sosial - Baca Pengaturan"
        const val SOCIAL_MSG_SEND = "Pengingat Pesan Sosial - Kirim Konten"
        const val SOCIAL_PHONE_IDLE_OR_OFFHOOK = "Pengingat Pesan Sosial - Panggilan Diterima"
        const val DEVICE_CONTROL_PHONE = "Gelang pemantau - tutup telepon, bisu"
        const val HEARTWRING_READ = "Alarm Detak Jantung - Baca"
        const val HEARTWRING_OPEN = "Alarm Detak Jantung - Aktif"
        const val HEARTWRING_CLOSE = "Alarm Denyut Jantung - Mati"
        const val SPO2H_OPEN = "Oksigen Darah - Baca"
        const val SPO2H_CLOSE = "oksigen darah - akhir"
        const val SPO2H_AUTO_DETECT_READ = "Deteksi otomatis oksigen darah - baca"
        const val SPO2H_AUTO_DETECT_OPEN = "Deteksi otomatis oksigen darah - terbuka"
        const val SPO2H_AUTO_DETECT_CLOSE = "Deteksi otomatis oksigen darah - mati"
        const val FATIGUE_OPEN = "Kelelahan - Baca"
        const val FATIGUE_CLOSE = "Kelelahan - Akhir"
        const val WOMEN_SETTING = "Status Perempuan - Pengaturan"
        const val WOMEN_READ = "Status Wanita - Baca"
        const val COUNT_DOWN_WATCH_CLOSE_UI =
            "Countdown - jam tangan digunakan sendiri (tutup antarmuka)"
        const val COUNT_DOWN_WATCH_OPEN_UI = "Hitung mundur - jam tangan digunakan sendiri (buka antarmuka)"
        const val COUNT_DOWN_APP = "Hitung mundur - Penggunaan aplikasi"
        const val COUNT_DOWN_APP_READ = "hitungan mundur - baca"
        const val GPS_KAABA = "Zona Waktu GPS & Ka'bah"
        const val GPS_REPORT_START = "pelaporan GPS"
        const val READ_CHANTING = "membaca nyanyian"
        const val SCREEN_LIGHT_SETTING = "Penyesuaian Layar - Pengaturan"
        const val SCREEN_LIGHT_READ = "Penyesuaian Layar - Baca"
        const val SCREEN_STYLE_READ = "gaya layar - baca"
        const val SCREEN_STYLE_SETTING = "Gaya Layar - Pengaturan"
        const val AIM_SPROT_CALC = "Langkah Sasaran - Perhitungan"
        const val INSTITUTION_TRANSLATION = "Metrik ke Konversi Imperial"
        const val READ_TEMPTURE_DATA = "membaca data suhu"
        const val READ_HEALTH_DRINK = "Membaca Data Kesehatan - Minum Alkohol"
        const val READ_HEALTH_SLEEP = "Baca Data Kesehatan - Tidur"
        const val READ_HEALTH_SLEEP_FROM = "Baca data kesehatan - tidur - sejak hari itu"
        const val READ_HEALTH_SLEEP_SINGLEDAY = "baca data kesehatan - tidur - baca hari ini"
        const val READ_HEALTH_ORIGINAL = "Baca data kesehatan - 5 menit"
        const val READ_HEALTH_ORIGINAL_FROM = "Baca data kesehatan - sejak hari itu"
        const val READ_HEALTH_ORIGINAL_SINGLEDAY = "Baca Data Kesehatan - Baca Hari Ini"
        const val READ_HEALTH = "membaca data kesehatan - semua"
        const val OAD = "meningkatkan firmware"
        const val SHOW_SP = "tunjukkan sp"
        const val SPORT_MODE_ORIGIN_READ = "Baca data - mode olahraga"
        const val SPORT_MODE_ORIGIN_READSTAUTS = "Baca Status - Mode Olahraga"
        const val SPORT_MODE_ORIGIN_START = "Aktif - Mode Olahraga"
        const val SPORT_MODE_START_INDOOR = "ON - BERJALAN DI DALAM RUANGAN"
        const val SPORT_MODE_ORIGIN_END = "AKHIR - MODE OLAHRAGA"
        const val SPO2H_ORIGIN_READ = "Membaca data – data oksigen darah"
        const val HRV_ORIGIN_READ = "Baca data - data HRV"
        const val CLEAR_DEVICE_DATA = "Hapus data"
        const val DISCONNECT = "Sambungkan-putuskan sambungan Bluetooth"
        const val DETECT_PTT = "PTT"
        const val DETECT_START_ECG = "Mulailah mengukur EKG"
        const val DETECT_STOP_ECG = "Akhiri pengukuran EKG"
        const val LOW_POWER_READ = "Daya Rendah - Baca"
        const val LOW_POWER_OPEN = "Daya Rendah - Aktif"
        const val LOW_POWER_CLOSE = "Daya Rendah - Mati"
        const val S22_READ_DATA = ""
        const val S22_READ_STATE = "S22-Status dibaca"
        const val S22_SETTING_STATE_OPEN = "Pengaturan status S22 (terbuka)"
        const val S22_SETTING_STATE_CLOSE = "Pengaturan S22-Status (mati)"
        const val BP_FUNCTION_READ = "Status tekanan darah (baca)"
        const val BP_FUNCTION_SETTING = "Status Tekanan Darah (Pengaturan)"
        const val WEATHER_READ_STATUEINFO = "status cuaca (baca)"
        const val SET_WATCH_TIME = "Atur waktu"
        const val WEATHER_SETTING_STATUEINFO = "Status Cuaca (Pengaturan)"
        const val WEATHER_SETTING_DATA = "Data Cuaca (Pengaturan)"
        const val LIANSUO_SOS = "Lianshuo-Monitor SOS"
        const val LIANSUO_SEND_ORDER = "Perintah Lianshuo-Kirim"
        const val LIANSUO_SEND_CONTENT = "Lianshuo-Kirim konten"
        const val UI_UPDATE_AGPS = "Peningkatan UI-AGPS"
        const val UI_UPDATE_CUSTOM = "Peningkatan UI - panggilan khusus"
        const val UI_UPDATE_SERVER = "Peningkatan UI - panggilan server"
        const val SYNC_MUSIC_INFO = "Sinkronkan informasi musik"
        const val UI_UPDATE_G15IMG = "Peningkatan UI - transfer gambar G15"
        const val TEXT_ALARM_ADD = "Alarm teks ditambahkan"
        const val TEXT_ALARM_MODIFY = "Modifikasi alarm teks"
        const val TEXT_ALARM_READ = "Pembacaan alarm teks"
        const val TEXT_ALARM_DELETE = "Penghapusan Alarm Teks"
        const val TEXT_ALARM = "jam alarm teks"
        const val ORIGIN_LOG = "catatan data mentah"
        const val RR = "RR bingkai demi lompatan"
        const val G15_QR_CODE = "Kode QR G15"
        const val ECG_AUTO_REPORT_TEXT = "EKG biasanya membuka pemantauan data"
        const val NONE = "NONE"

        val oprateStr = arrayOf(
            PWD_COMFIRM,
            PERSONINFO_SYNC,
            SETTING_FIRST,
            PWD_MODIFY,
            GPS_KAABA,
            GPS_REPORT_START,
            READ_CHANTING,
            HEART_DETECT_START,
            HEART_DETECT_STOP,
            TEMPTURE_DETECT_START,
            TEMPTURE_DETECT_STOP,
            READ_TEMPTURE_DATA,
            BP_DETECT_START,
            BP_DETECT_STOP,
            BP_DETECTMODEL_SETTING,
            BP_DETECTMODEL_READ,
            BP_DETECTMODEL_SETTING_ADJUSTE_CANCEL,
            BP_DETECTMODEL_SETTING_ADJUSTE,
            SPORT_CURRENT_READ,
            CAMERA_START,
            CAMERA_STOP,
            ALARM_SETTING,
            ALARM_READ,
            ALARM_NEW_READ,
            ALARM_NEW_ADD,
            ALARM_NEW_MODIFY,
            ALARM_NEW_DELETE,
            ALARM_NEW_LISTENER,
            LONGSEAT_SETTING_OPEN,
            LONGSEAT_SETTING_CLOSE,
            LONGSEAT_READ,
            LANGUAGE_CHINESE,
            LANGUAGE_ENGLISH,
            BATTERY,
            NIGHT_TURN_WRIST_OPEN,
            NIGHT_TURN_WRIST_CLOSE,
            NIGHT_TURN_WRIST_READ,
            NIGHT_TURN_WRIST_CUSTOM_TIME,
            NIGHT_TURN_WRIST_CUSTOM_TIME_LEVEL,
            DEVICE_COUSTOM_READ,
            DEVICE_COUSTOM_SETTING,
            DEVICE_ECG_ALWAYS_OPEN,
            DEVICE_ECG_ALWAYS_CLOSE,
            FINDPHONE,
            CHECK_WEAR_SETING_OPEN,
            CHECK_WEAR_SETING_CLOSE,
            FINDDEVICE_SETTING_OPEN,
            FINDDEVICE_SETTING_CLOSE,
            FINDDEVICE_READ,
            SOCIAL_MSG_SETTING,
            SOCIAL_MSG_SETTING2,
            SOCIAL_MSG_READ,
            SOCIAL_MSG_SEND,
            DEVICE_CONTROL_PHONE,
            SOCIAL_PHONE_IDLE_OR_OFFHOOK,
            HEARTWRING_READ,
            HEARTWRING_OPEN,
            HEARTWRING_CLOSE,
            SPO2H_OPEN,
            SPO2H_CLOSE,
            SPO2H_AUTO_DETECT_READ,
            SPO2H_AUTO_DETECT_OPEN,
            SPO2H_AUTO_DETECT_CLOSE,
            FATIGUE_OPEN,
            FATIGUE_CLOSE,
            WOMEN_SETTING,
            WOMEN_READ,
            COUNT_DOWN_WATCH_CLOSE_UI,
            COUNT_DOWN_WATCH_OPEN_UI,
            COUNT_DOWN_APP_READ,
            SCREEN_LIGHT_SETTING,
            SCREEN_LIGHT_READ,
            SCREEN_STYLE_READ,
            SCREEN_STYLE_SETTING,
            AIM_SPROT_CALC,
            INSTITUTION_TRANSLATION,
            READ_HEALTH_SLEEP,
            READ_HEALTH_SLEEP_FROM,
            READ_HEALTH_SLEEP_SINGLEDAY,
            READ_HEALTH_DRINK,
            READ_HEALTH_ORIGINAL,
            READ_HEALTH_ORIGINAL_FROM,
            READ_HEALTH_ORIGINAL_SINGLEDAY,
            READ_HEALTH,
            SET_WATCH_TIME,
            OAD,
            SHOW_SP,
            SPORT_MODE_ORIGIN_READ,
            SPORT_MODE_ORIGIN_READSTAUTS,
            SPORT_MODE_START_INDOOR,
            SPORT_MODE_ORIGIN_START,
            SPORT_MODE_ORIGIN_END,
            SPO2H_ORIGIN_READ,
            HRV_ORIGIN_READ,
            CLEAR_DEVICE_DATA,
            DISCONNECT,
            DETECT_START_ECG,
            DETECT_STOP_ECG,
            NONE,
            LOW_POWER_READ,
            LOW_POWER_OPEN,
            LOW_POWER_CLOSE,
            S22_READ_DATA,
            S22_READ_STATE,
            S22_SETTING_STATE_OPEN,
            S22_SETTING_STATE_CLOSE,
            DETECT_PTT,
            BP_FUNCTION_READ,
            BP_FUNCTION_SETTING,
            WEATHER_READ_STATUEINFO,
            WEATHER_SETTING_STATUEINFO,
            WEATHER_SETTING_DATA,
            LIANSUO_SOS,
            LIANSUO_SEND_ORDER,
            LIANSUO_SEND_CONTENT,
            UI_UPDATE_AGPS,
            UI_UPDATE_CUSTOM,
            UI_UPDATE_SERVER,
            UI_UPDATE_G15IMG,
            SYNC_MUSIC_INFO,  /*TEXT_ALARM_READ,TEXT_ALARM_ADD,TEXT_ALARM_MODIFY,TEXT_ALARM_DELETE,*/
            TEXT_ALARM,
            ORIGIN_LOG,
            RR,
            G15_QR_CODE,
            ECG_AUTO_REPORT_TEXT
        )
    }
}