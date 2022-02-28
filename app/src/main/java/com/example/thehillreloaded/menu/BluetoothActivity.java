package com.example.thehillreloaded.menu;

import android.app.ActivityOptions;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


public class BluetoothActivity extends Animazioni {

    //variabili
    Button  cerca, gioca;
    ImageView indietro;
    ListView listaDispositivi;
    TextView status;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;

    static final int STATO_SELEZIONA_GIOCATORE = 1;
    static final int STATO_CONNESSIONE = 2;
    static final int STATO_CONNESSO = 3;
    static final int STATO_CONNESSIONE_FALLITA = 4;

    int REQUEST_ENABLE_BLUETOOTH = 1;

    private static final String APP_NAME = "The Hill Reloaded";
    private static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        //Trova le view tramite l'id e le assegna alle variabili
        listaDispositivi = findViewById(R.id.listaDispositivi);
        status = findViewById(R.id.status);
        cerca =  findViewById(R.id.cerca);
        gioca =  findViewById(R.id.gioca);
        indietro =  findViewById(R.id.indietro);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(cerca);
        clickButtonAnimation(gioca);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Richiede l'attivazione del bluetooth se disattivato
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BLUETOOTH);
        }

        implementListeners();

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        gioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BluetoothActivity.this, SchermataCaricamentoActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(BluetoothActivity.this).toBundle();
                startActivity(i, b);
                finish();
            }
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BluetoothActivity.this, MultigiocatoreActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(BluetoothActivity.this).toBundle();
                startActivity(i, b);
                finish();
            }
        });
    }

    private void implementListeners() {

        //Mostra la lista dei dispositivi con bluetooth attivo alla pressione del pulsante
        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice>
                        bt = bluetoothAdapter.getBondedDevices();
                String[] strings= new String[bt.size()];
                btArray= new BluetoothDevice[bt.size()];
                int index=0;

                if(bt.size()>0)
                {
                    for(BluetoothDevice device : bt)
                    {
                        btArray[index] = device;
                        strings[index] = device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    listaDispositivi.setAdapter(arrayAdapter);
                }
            }
        });

        ServerClass serverClass = new ServerClass();
        serverClass.start();

        //Connette il dispositivo attuale con quello selezionato dalla lista
        listaDispositivi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass = new ClientClass(btArray[i]);
                clientClass.start();

                status.setText(R.string.connessione);
                gioca.setVisibility(Button.GONE);
                indietro.setVisibility(Button.VISIBLE);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case STATO_SELEZIONA_GIOCATORE:
                    status.setText(R.string.seleziona_giocatore);
                    gioca.setVisibility(Button.GONE);
                    indietro.setVisibility(Button.VISIBLE);
                    break;
                case STATO_CONNESSIONE:
                    status.setText(R.string.connessione);
                    gioca.setVisibility(Button.GONE);
                    indietro.setVisibility(Button.VISIBLE);
                    break;
                case STATO_CONNESSO:
                    status.setText(R.string.connesso);
                    gioca.setVisibility(Button.VISIBLE);
                    indietro.setVisibility(Button.GONE);
                    break;
                case STATO_CONNESSIONE_FALLITA:
                    status.setText(R.string.connessione_fallita);
                    gioca.setVisibility(Button.GONE);
                    indietro.setVisibility(Button.VISIBLE);
                    break;
            }
            return true;
        }
    });

    private class ServerClass extends Thread
    {
        private BluetoothServerSocket serverSocket;

        public ServerClass(){
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Imposta lo stato della connessione
        public void run()
        {
            BluetoothSocket socket = null;

            while (true)
            {
                try {
                    Message message=Message.obtain();
                    message.what = STATO_SELEZIONA_GIOCATORE;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message=Message.obtain();
                    message.what=STATO_CONNESSIONE_FALLITA;
                    handler.sendMessage(message);
                }

                if(socket!=null)
                {
                    Message message=Message.obtain();
                    message.what=STATO_CONNESSO;
                    handler.sendMessage(message);

                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread
    {
        private BluetoothSocket socket;

        public ClientClass (BluetoothDevice device1)
        {

            try {
                socket= device1.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Imposta lo stato della connessione
        public void run()
        {
            try {
                socket.connect();
                Message message=Message.obtain();
                message.what=STATO_CONNESSO;
                handler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
                Message message=Message.obtain();
                message.what=STATO_CONNESSIONE_FALLITA;
                handler.sendMessage(message);
            }
        }
    }


}
