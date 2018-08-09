package com.penta.games.freshtorgesoundboardpro.tabs;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.penta.games.freshtorgesoundboardpro.MainActivity;
import com.penta.games.freshtorgesoundboardpro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ratan on 7/29/2015.
 */
public class Clarissa extends Fragment {
    GridView myGridView;
    int position;
    View layout;
    //    File soundfile;
    File directory;
    String filename;
    public String[] items1 ={"Das geht nicht", "Hallo? 1", "Hallo? 2", "Ich will nichts", "Ja bitte?", "Ja Mutti?", "jetzt?", "Justin Bieber Fan", "Justin Bieber ist Schei*e", "Klamotten vom Körper"
            , "Merkwürdiges Geräusch 1", "Merkwürdiges Geräusch 2", "Merkwürdiges Geräusch 3", "Nein 1", "Nein 2", "Nein 3", "Oke", "One Direction Fan", "Saat für morgen", "Sami Slimani Fan"
            , "Justin Bieber Songtext", "Stimm garnicht", "Super oder?", "Bist du Traurig?", "Waaas?", "Reisebüro", "Yes", "Was ein Zufall"};

    public static int[] soundfiles ={
    /*5*/                           R.raw.dasgehtnichtclarissa,R.raw.hallo1clarissa, R.raw.halloclarissa, R.raw.ichwillnichtsclarissa, R.raw.jabitteclarissa,
    /*5*/                           R.raw.jamutticlarissa, R.raw.jetztclarissa, R.raw.justinbieberclarissa, R.raw.justinistscheisseclarissa, R.raw.klamottenclarissa,
    /*5*/                           R.raw.komischesgereusch1clarissa, R.raw.komischesgereusch2clarissa, R.raw.komischesgereuschclarissa, R.raw.nein2clarissa, R.raw.nein3clarissa,
    /*5*/                           R.raw.neinclarissa, R.raw.okayclarissa, R.raw.onedirectionclarissa, R.raw.saatfuermorgenclarissa, R.raw.samislimanifanclarissa,
    /*5*/                           R.raw.sorryclarissa, R.raw.stimmtnichtclarissa, R.raw.superoderclarissa, R.raw.traurigclarissa, R.raw.wasclarissa,
    /*2*/                           R.raw.weltrettenclarissa, R.raw.yesclarissa, R.raw.zufallclarissa
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.clarissa,container,false);

        layout=rootView.findViewById(R.id.clarissa);
        File storage = Environment.getExternalStorageDirectory();
        directory = new File(storage.getAbsolutePath() + "/freshtorge_pro/");
//        soundfile=new File(directory, filename);

        myGridView = (GridView)rootView.findViewById(R.id.clarissaGridView);
        myGridView.setAdapter(new Clarissa.CustomGridAdapter(getActivity(), items1));
        myGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {

                position=pos;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
                builder.setItems(new CharSequence[]{"Sound teilen", "Sound setzen als..."}, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){





                        // Decide on the users choice which information will be send to a method that handles the settings for all kinds of system audio
                        switch (which){


                            case 0:
                                savefile(pos,true);
                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory().toString() +"/freshtorge_pro/"+ items1[position]+".mp3"));
                                share.setType("audio/mp3");
                                startActivity(Intent.createChooser(share, "Sound teilen über..."));
                                break;

                            case 1:

                                requestPermissions();

                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        if(Settings.System.canWrite(getContext())){
                                            buildalertdielog_withpermissions();
                                            savefile(pos,false);
                                        }
                                    }else{
                                        buildalertdielog_withpermissions();
                                        savefile(pos, false);
                                    }
                                }


                                break;
                        }
                    }
                });
                builder.create();
                builder.show();



                return true;

            }
        });
        return rootView;
    }




    public class CustomGridAdapter extends BaseAdapter {

        private Context context;
        private String[] items;
        LayoutInflater inflater;

        public CustomGridAdapter(Context c, String[] items) {
            this.context = c;
            this.items = items;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }




        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.single_item, null);
            }
            Button button = (Button) convertView.findViewById(R.id.button);
            button.setText(items[position]);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).clarissaItemClicked(position);
                    }
                }
            });

            return convertView;
        }


    }

    private void requestPermissions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            // Check if the permission to write and read the users external storage is not granted
            // You need this permission if you want to share sounds via WhatsApp or the like
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                // You can log this little text if you want to see if this method works in your Android Monitor
                //Log.i(LOG_TAG, "Permission not granted");

                // If the permission is not granted request it
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }

            // Check if the permission to write the users settings is not granted
            // You need this permission to set a sound as ringtone or the like
            if(!Settings.System.canWrite(getContext())){

                // Displays a little bar on the bottom of the activity with an OK button that will open a so called permission management screen
                Snackbar.make(layout, "The app needs access to your settings", Snackbar.LENGTH_INDEFINITE).setAction("OK",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Context context = v.getContext();
                                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).show();
            }

        }


    }
    public void buildalertdielog_withpermissions(){
        AlertDialog.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        } else{
            builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);

        }
        builder.setItems(new CharSequence[]{"Klingelton", "Nachrichtenton", "Alarmton"}, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){

                // Decide on the users choice which information will be send to a method that handles the settings for all kinds of system audio
                switch (which){

                    // Ringtone
                    case 0:
                        Toast.makeText(getContext(), "Klingelton", Toast.LENGTH_SHORT).show();
                        setTone(1);
                        break;
                    // Notification
                    case 1:
                        Toast.makeText(getContext(), "Nachrichtenton", Toast.LENGTH_SHORT).show();
                        setTone(2);
                        break;
                    // Alarmton
                    case 2:
                        Toast.makeText(getContext(), "Alarmton", Toast.LENGTH_SHORT).show();
                        setTone(3);
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }


    public void savefile(int pos, boolean sharing){
        File file;
        // Get the path to the users external storage
        File storage = Environment.getExternalStorageDirectory();
        // Define the directory path to the soundboard apps folder
        // Change my_soundboard to whatever you want as your folder but keep the slash
        // TODO: When changing the path be sure to also modify the path in filepaths.xml (res/xml/filepaths.xml)
        File directory = new File(storage.getAbsolutePath() + "/freshtorge_pro/");
        // Creates the directory if it doesn't exist
        // mkdirs() gives back a boolean. You can use it to do some processes as well but we don't really need it.
        directory.mkdirs();

        // Finally define the file by giving over the directory and the filename
        if(sharing){
            file = new File(directory, items1[position]+".mp3");
        }else{
            file = new File(directory, items1[position]);
        }


        // Define an InputStream that will read the data from your sound-raw.mp3 file into a buffer
        InputStream in = this.getResources().openRawResource(soundfiles[pos]);

        try{
            if(MainActivity.isTesting){
                Toast.makeText(getContext(),"Sound Saved", Toast.LENGTH_SHORT).show();
            }
            // Log the name of the sound that is being saved
            Log.e("Saving sound ","#############");

            // Define an OutputStream/FileOutputStream that will write the buffer data into the sound.mp3 on the external storage
            OutputStream out = new FileOutputStream(file);
            // Define a buffer of 1kb (you can make it a little bit bigger but 1kb will be adequate)
            byte[] buffer = new byte[1024];

            int len;
            // Write the data to the sound.mp3 file while reading it from the sound-raw.mp3
            // if (int) InputStream.read() returns -1 stream is at the end of file
            while ((len = in.read(buffer, 0, buffer.length)) != -1){
                out.write(buffer, 0 , len);
            }

            // Close both streams
            in.close();
            out.close();



        } catch (IOException e){

            // Log error if process failed
            Log.e("Failed to save file: " ,"####################");
        }
    }

    public void setTone(int action){
        File soundfile=new File(directory, items1[position]);
        try{

            // Put all informations about the audio into ContentValues
            ContentValues values = new ContentValues();

            // DATA stores the path to the file on disk
            values.put(MediaStore.MediaColumns.DATA, soundfile.getAbsolutePath());
            // TITLE stores... guess what? Right, the title. GENIUS
            values.put(MediaStore.MediaColumns.TITLE, items1[position]);
            // MIME_TYPE stores the type of the data send via the MediaProvider
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");

            switch (action){

                // Ringtone
                case 1:
                    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
                    values.put(MediaStore.Audio.Media.IS_ALARM, false);
                    break;
                // Notification
                case 2:
                    values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
                    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                    values.put(MediaStore.Audio.Media.IS_ALARM, false);
                    break;
                // Alarm
                case 3:
                    values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
                    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
                    values.put(MediaStore.Audio.Media.IS_ALARM, true);
                    break;
            }

            values.put(MediaStore.Audio.Media.IS_MUSIC, false);

            // Define a link(Uri) to the saved file and modify this link a little bit
            // DATA is set by ContenValues and therefore has to be replaced
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(soundfile.getAbsolutePath());
            getContext().getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + soundfile.getAbsolutePath() + "\"", null);
            // Fill the Uri with all the information from ContentValues
            Uri finalUri = getContext().getContentResolver().insert(uri, values);

            // Finally set the audio as one of the system audio types
            switch (action){

                // Ringtone
                case 1:
                    RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_RINGTONE, finalUri);
                    break;
                // Notification
                case 2:
                    RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_NOTIFICATION, finalUri);
                    break;
                // Alarm
                case 3:
                    RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_ALARM, finalUri);
                    break;
            }

        } catch (Exception e){

            // Log error if process failed
            Log.e( "Failed to save: ", "######");
        }
    }

}