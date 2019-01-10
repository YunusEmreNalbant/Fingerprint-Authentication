package com.example.yunus.fingerprint;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.Key;

public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadingLabel = (TextView) findViewById(R.id.headingLabel);
        mFingerprintImage=(ImageView) findViewById(R.id.fingerprintImage);
        mParaLabel=(TextView) findViewById(R.id.paraLabel);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // version marshmallow dan büyük esit ise

            fingerprintManager=(FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager=(KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager .isHardwareDetected()) { // cihazada parmak izi tarayıcı var mı

                mParaLabel.setText("Fingerprint Scanner not detected in device");

            }else if (ContextCompat.checkSelfPermission(this,Manifest.permission.USE_FINGERPRINT)!=PackageManager.PERMISSION_GRANTED) { // izin verilmediyse

                mParaLabel.setText("Permission not granted use Fingerprint Scanner");

            }else if(!keyguardManager.isKeyguardSecure()) {

                mParaLabel.setText("Add lock to your phone in settings");
            }else if(!fingerprintManager.hasEnrolledFingerprints()) { // kayıtlı parmak izi yok ise
                mParaLabel.setText("You should add atleast 1 Fingerprint to use this Feature");
            }else {

                mParaLabel.setText("Place your finger on scanner to Access the app");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager,null);
            }
        }

    }
}
