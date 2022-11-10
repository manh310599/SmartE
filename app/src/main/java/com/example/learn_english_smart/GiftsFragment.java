package com.example.learn_english_smart;



import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.learn_english_smart.Model.APIResponse;
import com.example.learn_english_smart.SearchImage.API_Image;
import com.example.learn_english_smart.SearchImage.GetImage;
import com.example.learn_english_smart.Translate.MeaningAdapter;
import com.example.learn_english_smart.Translate.OnFetchDataListener;
import com.example.learn_english_smart.Translate.PhoneticAdapter;
import com.example.learn_english_smart.Translate.RequestManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.nl.translate.Translator;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiftsFragment extends Fragment {



    public GiftsFragment() {

    }

    SearchView search_view;
    TextView textView_word,meaning;
    RecyclerView recycler_phonetics, recycler_meanings;
    ProgressDialog dialog;
   PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;
    Toolbar toolbar;
    PackageInfo mPackageInfo;
    ImageView image;



    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_gifts, container, false);
        search_view = view.findViewById(R.id.search_view);
        textView_word = view.findViewById(R.id.textView_word);
        recycler_phonetics = view.findViewById(R.id.recycler_phonetics);
        recycler_meanings = view.findViewById(R.id.recycler_meanings);
        toolbar = view.findViewById(R.id.toolbar);
        dialog = new ProgressDialog(getActivity());
        meaning = view.findViewById(R.id.Meaning);
        image = view.findViewById(R.id.image);

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.VIETNAMESE)
                        .build();
        final Translator englishGermanTranslator =
                (Translator) Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

        try {
            mPackageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            setupVersionInfo();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        RequestManager manager = new RequestManager(getActivity());
        manager.getWordMeaning(listener, "hello");


        Glide.with(getContext()).load("https://pixabay.com/get/g745d5be54f7d32ccd223aba6c67c15a37f996725b6e4c12a898d587eecc7623c10cc56aee14cd0763130a5f2b6e6d499c835b2536a7d4451c7f38e3c71f95adf_1280.jpg").into(image);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {




                RequestManager manager = new RequestManager(getActivity());
                manager.getWordMeaning(listener, query);

                englishGermanTranslator.translate(query)
                        .addOnSuccessListener(
                                new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        meaning.setText(translatedText);
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error.
                                        // ...
                                    }
                                });

                API_Image.API_IMAGE.GET_IMAGE_CALL("30364055-ba526c17b5bca96e736c16990",query,"photo",true).enqueue(new Callback<GetImage>() {
                    @Override
                    public void onResponse(Call<GetImage> call, Response<GetImage> response) {
                        GetImage getImage = response.body();
                        if (getImage != null)
                        {

                            try {
                                int random = ThreadLocalRandom.current().nextInt(0, 10);
                                Picasso.get().load(getImage.getHits().get(random).getWebformatURL()).into(image);
                            }catch (Exception ex)
                            {
                                Toast.makeText(getActivity(), "Không có hình ảnh này", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<GetImage> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                    }
                });



                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return view;
    }



    private final OnFetchDataListener listener = new OnFetchDataListener() {


        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            dialog.dismiss();
            if (apiResponse==null){
                Toast.makeText(context, "No Data Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(apiResponse);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(APIResponse response){
        textView_word.setText("Word: " + response.getWord());

        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(context, 1));
        phoneticAdapter = new PhoneticAdapter(context, response.getPhonetics());
        recycler_phonetics.setAdapter(phoneticAdapter);

        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(context, 1));
        meaningAdapter = new MeaningAdapter(context, response.getMeanings());
        recycler_meanings.setAdapter(meaningAdapter);
    }

    private void setupVersionInfo() {
        if (mPackageInfo != null) {
            String vinfo = String.format("V: %s", mPackageInfo.versionName);
            toolbar.setSubtitle(vinfo);


        }
    }
}