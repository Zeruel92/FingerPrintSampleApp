package it.pspgt.fingerprintsampleapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    private TextView textView;

    public FingerPrintHandler(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "Impronta letta", Toast.LENGTH_LONG).show();
        Log.i("FHandler", "Impronta riconosciuta");
        textView.setText("Impronta riconosciuta!");
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(context, "Qualcosa è andato storto errID: " + errMsgId, Toast.LENGTH_SHORT).show();
        Log.e("FHandler", errString.toString());
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Impronta non riconosciuta", Toast.LENGTH_LONG).show();
        Log.i("FHandler", "Impronta non associata al dispositivo");
    }
}
