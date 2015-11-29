package be.ipl.android.moviebuzz;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import be.ipl.android.moviebuzz.model.Jeu;

//http://sberfini.developpez.com/tutoriaux/android/bluetooth
//http://developer.android.com/guide/topics/connectivity/bluetooth.html

public class GameOptionsActivity extends AppCompatActivity {

    private AlertDialog.Builder adb;

    private TextView label;
    private TextView value;
    private SeekBar seekBar;
    private Toast toast;

    private int min, max, step;

    // pour permission bluetooth
    ArrayList<BluetoothDevice>deviceList;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private Set<BluetoothDevice> devices;
    private ProgressDialog mProgressDlg;
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        //des quon detecte un divice il fera cette methode
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                deviceList = new ArrayList<BluetoothDevice>();
                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                Intent newIntent = new Intent(getApplicationContext(), DeviceListActivity.class);

                newIntent.putParcelableArrayListExtra("device.list", deviceList);

                startActivity(newIntent);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceList.add(device);
                toaster("Found device " + device.getName());
            }
        }
    };
    private IntentFilter filter ;

    private Intent registerReceiver = null;//a destroy a la fin
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private static final String NAME_INSECURE = "BluetoothInsecure";
    //on linstancie slmnt si clic sur trouverchallenger
    private ServeurBluetooth serveurBluetooth;
    // a lancer de tte facon
    private ClientBluetooth clientBluetooth;
    private static int DISCOVERY_REQUEST = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver = registerReceiver(bluetoothReceiver, filter);
        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setMessage("Scanning...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                bluetoothAdapter.cancelDiscovery();
            }
        });

        setContentView(R.layout.activity_game_options);
    }

    @Override
    public void onPause() {
        if (this.bluetoothAdapter != null) {
            if (this.bluetoothAdapter.isDiscovering()) {
                this.bluetoothAdapter.cancelDiscovery();
            }
        }

        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.verificationBluetooth();
        // on lance le serveur dans tt les cas pour si on recoit une invitation
        serveurBluetooth = new ServeurBluetooth();
    }

    public void onClickContreMontre(View view) {
        constructDialog();
        label.setText(R.string.options_duration);
        constructDurationSeekBar();
        fillDialog(GameActivity.PARAM_JEU_DUREE);
    }

    public void onClickMaxPoint(View view) {
        constructDialog();
        label.setText(R.string.options_points);
        constructPointsSeekBar();
        fillDialog(GameActivity.PARAM_JEU_POINTS);
    }

    public void onClickMaxEpreuve(View view) {
        constructDialog();
        label.setText(R.string.options_questions);
        constructQuestionsSeekBar();
        fillDialog(GameActivity.PARAM_JEU_EPREUVES);
    }

    private void constructDialog() {
        adb = new AlertDialog.Builder(this);
        //On instancie notre layout en tant que View
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_type_partie, null);
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(dialog);
        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        value = (TextView) dialog.findViewById(R.id.gameOptionValue);
        label = (TextView) dialog.findViewById(R.id.gameOptionLabel);
        seekBar = (SeekBar) dialog.findViewById(R.id.gameOptionSeekBar);
    }

    private void fillDialog(final String key) {

        final Intent gameIntent = new Intent(this, GameActivity.class);

        // Bouton "Commencer"
        adb.setPositiveButton("Commencer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                gameIntent.putExtra(key, (seekBar.getProgress() + min / step) * step);
                findChallenger();
           //     startActivity(gameIntent);
            }
        });

        // Bouton "Annuler"
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Lorsque l'on cliquera sur annuler on quittera l'application
                //finish();
            }
        });

        adb.show();
    }

    private void constructDurationSeekBar() {
        min = 30; // secondes
        max = 600; // secondes
        step = 10; // secondes

        seekBar.setMax((max - min) / step);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = (progress + min / step) * step;
                int minutes = progress / 60;
                int secondes = progress % 60;
                value.setText(String.format("%02d:%02d", minutes, secondes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // On remplit la valeur par défaut de la seekbar
        seekBar.setProgress((Jeu.DEF_DUREE - min) / step);
    }

    private void constructPointsSeekBar() {
        min = 300; // points
        max = 5000; // points
        step = 100; // points

        seekBar.setMax((max - min) / step);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value.setText(String.format("%d points", (progress + min / step) * step));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // On remplit la valeur par défaut de la seekbar
        seekBar.setProgress((Jeu.DEF_NOMBRE_POINTS - min) / step);
    }

    private void constructQuestionsSeekBar() {
        min = 5; // épreuves
        max = 30; // épreuves
        step = 1; // épreuves

        seekBar.setMax((max - min) / step);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value.setText(String.format("%d épreuves", (progress + min / step) * step));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // On remplit la valeur par défaut de la seekbar
        seekBar.setProgress((Jeu.DEF_NOMBRE_EPREUVES - min) / step);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void verificationBluetooth() {
        if (bluetoothAdapter == null)
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String message;

        if (bluetoothAdapter == null) {
            message = "Votre appareil ne supporte pas le bluetooth";
        } else {
            message = "Ok vous vous posssedez le bluetooth";
        }
        toaster(message);
        //si son bluetooth n'est pas activer je demande permission de lactiver
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_CODE_ENABLE_BLUETOOTH);
        }

    }

    public void toaster(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void findChallenger() {
        //retest bluetooth
        if (!bluetoothAdapter.isEnabled())
            verificationBluetooth();
        // affichage de tt peripherik bluetooth kon connait... DEBBUG
        devices = bluetoothAdapter.getBondedDevices();
        if (devices == null || devices.size() == 0)
            toaster("No Paired Devices Found");
        else
            for (BluetoothDevice blueDevice : devices) {
                toaster("Device = " + blueDevice.getName());
            }
        //recherche des peripherique inconnu
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
        //une fois que jai choisi le bon je dois initialiser mon client et me connecter
        //COCO
        // clientBluetooth=new ClientBluetooth();

    }


    //surcharge necessaire pour avoir la reponse du user pour activer le bluetooth
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //si c pas pour le bluetooth on fait rien
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        // si il a dit oui
        if (resultCode == RESULT_OK) {
            toaster("bluetooth ACTIVER");
            rendreVisibleEnBluetooth();
        } else {
            toaster("bluetooth NON activer");
        }
    }

    public void rendreVisibleEnBluetooth() {
        if (bluetoothAdapter.isEnabled()) {
            //une fois le bluetooth activer alor on peut demarer le serveur
            // if (!serveurBluetooth.getState().equals(BluetoothAdapter.STATE_OFF) && !serveurBluetooth.getState().equals(BluetoothAdapter.STATE_TURNING_OFF))
            //   serveurBluetooth.start();
            //rendre mon peripherique visible
            toaster("rendre visible");
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
            // affichage de tt peripherik bluetooth kon connait... DEBBUG
            devices = bluetoothAdapter.getBondedDevices();
            for (BluetoothDevice blueDevice : devices) {
                toaster("Device = " + blueDevice.getName());
            }

        }
    }

    //surcharge necessaire pour le cancelDiscovery() et unregisterReceiver
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothAdapter.cancelDiscovery();
        bluetoothAdapter.disable();
        unregisterReceiver(bluetoothReceiver);
    }

    //class intern pour le serveur
    // un serveur est lance des le debut de lactivity
    private class ServeurBluetooth extends Thread {
        private final BluetoothServerSocket blueServerSocket;

        public ServeurBluetooth() {
            // On utilise un objet temporaire qui sera assigné plus tard à blueServerSocket car blueServerSocket est "final"
            BluetoothServerSocket tmp = null;
            try {
                // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté client également !
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
            } catch (IOException e) {
            }
            blueServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket blueSocket = null;
            // On attend une erreur ou une connexion entrante
            while (true) {
                try {
                    blueSocket = blueServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // Si une connexion est acceptée
                if (blueSocket != null) {
                    // On fait ce qu'on veut de la connexion (dans un thread séparé), à vous de la créer
                    //COCO
                    // manageConnectedSocket(blueSocket);
                    try {
                        blueServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        // On stoppe l'écoute des connexions et on tue le thread
        public void cancel() {
            try {
                blueServerSocket.close();
            } catch (IOException e) {
            }
        }
    }

    // le client est lancer seulement kan on click sur trouver challenger
    private class ClientBluetooth extends Thread {
        private final BluetoothSocket blueSocket;
        private final BluetoothDevice blueDevice;

        public ClientBluetooth(BluetoothDevice device) {
            // On utilise un objet temporaire car blueSocket et blueDevice sont "final"
            BluetoothSocket tmp = null;
            blueDevice = device;

            // On récupère un objet BluetoothSocket grâce à l'objet BluetoothDevice
            try {
                // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté serveur également !
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
            } catch (IOException e) {
            }
            blueSocket = tmp;
        }

        public void run() {
            // On annule la découverte des périphériques (inutile puisqu'on est en train d'essayer de se connecter)
            bluetoothAdapter.cancelDiscovery();

            try {
                // On se connecte. Cet appel est bloquant jusqu'à la réussite ou la levée d'une erreur
                blueSocket.connect();
            } catch (IOException connectException) {
                // Impossible de se connecter, on ferme la socket et on tue le thread
                try {
                    blueSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }

            // Utilisez la connexion (dans un thread séparé) pour faire ce que vous voulez
            //COCO
            // manageConnectedSocket(blueSocket);
        }

        // Annule toute connexion en cours et tue le thread
        public void cancel() {
            try {
                blueSocket.close();
            } catch (IOException e) {
            }
        }
    }


}