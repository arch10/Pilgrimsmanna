package com.gigaworks.tech.bible;

public class Constants {

    private static String HOME_URL = "http://biblebook.pilgrimsmanna.com/";
    private static String AUDIO_HOME = HOME_URL + "biblebook/";
    private static String ANDROID_HOME = AUDIO_HOME + "android/";
    private static String GET_SOUND_URL = ANDROID_HOME +"getSound.php";
    private static String GET_BOOK_URL = ANDROID_HOME + "getBook.php";
    private static String YESHU_KA_JEEVAN = "Yeshu Masih Ka Jeevan";
    private static String MONTHLY_BREAD = "Sharing God's Secrets";
    private static String GUJARATI_BIBLE = "Bible";
    private static String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.gigaworks.tech.bible&hl=en";
    private static String DAILY_DEVOTIONAL = "Daily Devotionals";
    private static String DAILY_DEVOTION = "Daily Devotion";
    private static String AUDIO_BOOKS = "Audio Books";

    public static String BOOK_TITLE = "book.name";
    public static String BOOK_DATA = "book.data";
    public static String BOOK_CHAPTERS = "book.chapters";

    public static String getSoundUrl() {
        return GET_SOUND_URL;
    }

    public static String getBookUrl() {
        return GET_BOOK_URL;
    }

    public static String getAudioHome() {
        return AUDIO_HOME;
    }

    public static String getYeshuKaJeevan() {
        return YESHU_KA_JEEVAN;
    }

    public static String getMonthlyBread() {
        return MONTHLY_BREAD;
    }

    public static String getGujaratiBible() {
        return GUJARATI_BIBLE;
    }

    public static String getPlayStoreLink() {
        return PLAY_STORE_LINK;
    }

    public static String getDailyDevotional() {
        return DAILY_DEVOTIONAL;
    }

    public static String getDailyDevotion() {
        return DAILY_DEVOTION;
    }

    public static String getAudioBooks() {
        return AUDIO_BOOKS;
    }
}
