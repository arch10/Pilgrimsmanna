package com.gigaworks.tech.bible.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.gigaworks.tech.bible.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    private static MutableLiveData<String> bookData;
    private static MutableLiveData<String> soundData;

    public LiveData<String> getBookData(String defValue) {
        if (bookData == null) {
            bookData = new MutableLiveData<>();
            bookData.setValue(defValue);

            new AsyncTaskEx().execute(Constants.getBookUrl(), "0");
        }
        return bookData;
    }

    public LiveData<String> getSoundData(String defValue) {
        if(soundData == null) {
            soundData = new MutableLiveData<>();
            soundData.setValue(defValue);

            new AsyncTaskEx().execute(Constants.getSoundUrl(), "1");
        }
        return soundData;
    }

    private static void loadData(String stringData) {
        //async task
        bookData.postValue(stringData);
    }

    private static void loadSoundData(String stringData) {
        //async task
        soundData.postValue(stringData);
    }

    private static class AsyncTaskEx extends AsyncTask<String, Void, String> {

        private String task = "";

        @Override
        protected String doInBackground(String... urls) {
            task = urls[1];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();

            Response response;
            try {
                response = client.newCall(request).execute();
                assert response.body() != null;
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(task.equals("1")) {
                loadSoundData(s);
            } else {
                loadData(s);
            }
        }

    }
}
