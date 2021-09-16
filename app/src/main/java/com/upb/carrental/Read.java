package com.upb.carrental;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Read {

    private Context mContext;

    public Read(Context context) {
        this.mContext = context;
    }

    public List<String> readLine(String path) {
        List<String> mLines = new ArrayList<>();

        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mLines;
    }

    public ArrayList<String> readSearch(String path, String city) {
        ArrayList<String> sLines = new ArrayList<>();

        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] variable = line.split("\\s+" );
                // daca gasim orasul
                if(variable[0].trim().equals(city)){
                    for(int i = 0; i < Integer.parseInt(variable[1].trim()); i++){
                        line = reader.readLine();
                        sLines.add(line);
                    }
                    break;
                }
                reader.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sLines;
    }

}
