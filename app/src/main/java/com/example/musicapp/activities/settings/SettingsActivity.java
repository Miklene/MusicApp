package com.example.musicapp.activities.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.R;
import com.example.musicapp.activities.main.MainActivity;
import com.example.musicapp.activities.wave_tuner.WaveTunerActivity;
import com.example.musicapp.activities.wave_tuner.WaveTunerPresenter;
import com.example.musicapp.activities.wave_tuner.WaveTunerView;
import com.example.musicapp.buffer.WaveBuffer;
import com.example.musicapp.common.Settings;
import com.example.musicapp.common.TypeOfBuffer;
import com.example.musicapp.sound_effect.AmplitudeDynamics;
import com.example.musicapp.sound_effect.SoundEffectsStatus;

import java.security.spec.ECField;

import static com.example.musicapp.common.TypeOfBuffer.MULTI;
import static com.example.musicapp.common.TypeOfBuffer.SINGLE;


public class SettingsActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    TypeOfBuffer single = SINGLE;
    TypeOfBuffer multi = MULTI;

    private CheckBox checkBoxAmplitudeDynamic, checkBoxFrequencyDynamic,
            checkBoxNormalization, checkBoxReverberation, checkBoxStereo;
    private EditText durationEditText;
    private RadioButton radioButtonSingle, radioButtonMulti;
    private RadioGroup radioGroup;
    private ImageButton imageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        imageButton = findViewById(R.id.toolbar_image_button_settings_activity);

        radioButtonSingle = findViewById(R.id.radio_button_single);
        radioButtonMulti = findViewById(R.id.radio_button_multi);
        radioGroup = findViewById(R.id.single_multi_radio_group);

        durationEditText = findViewById(R.id.durationEditText);
        durationEditText.setText(String.valueOf(Settings.duration));

        checkBoxAmplitudeDynamic = findViewById(R.id.check_box_amplitude_dynamic);
        checkBoxFrequencyDynamic = findViewById(R.id.check_box_frequency_dynamic);
        checkBoxNormalization = findViewById(R.id.check_box_normalization);
        checkBoxReverberation = findViewById(R.id.check_box_reverberation);
        checkBoxStereo = findViewById(R.id.check_box_stereo);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        initRadioButton();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_single:
                        Settings.currentWaveBuffer = single;
                        break;
                    case R.id.radio_button_multi:
                        Settings.currentWaveBuffer = multi;
                        break;
                    default:
                        break;
                }
            }
        });

        initCheckBoxes();
        checkBoxAmplitudeDynamic.setOnCheckedChangeListener(this);
        checkBoxFrequencyDynamic.setOnCheckedChangeListener(this);
        checkBoxNormalization.setOnCheckedChangeListener(this);
        checkBoxStereo.setOnCheckedChangeListener(this);
        checkBoxReverberation.setOnCheckedChangeListener(this);

        durationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().matches("^0")) {
                    durationEditText.setError(" Zero is not valid input");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (Integer.valueOf(s.toString()) <= 0)
                        durationEditText.setError(" Zero is not valid input");
                    else
                        Settings.duration = Integer.valueOf(s.toString());
                } catch (Exception ex) {
                    durationEditText.setError("Cannot be empty");
                }
            }
        });
    }


    private void initRadioButton() {
        if (Settings.currentWaveBuffer.equals(single)) {
            radioButtonSingle.setChecked(true);
            return;
        }
        radioButtonMulti.setChecked(true);
    }

    private void initCheckBoxes() {
        checkBoxAmplitudeDynamic.setChecked(SoundEffectsStatus.amplitudeDynamic);
        checkBoxNormalization.setChecked(SoundEffectsStatus.normalization);
        checkBoxFrequencyDynamic.setChecked(SoundEffectsStatus.frequencyDynamic);
        checkBoxReverberation.setChecked(SoundEffectsStatus.reverberation);
        checkBoxStereo.setChecked(SoundEffectsStatus.stereo);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_box_amplitude_dynamic:
                SoundEffectsStatus.amplitudeDynamic = isChecked;
                break;
            case R.id.check_box_frequency_dynamic:
                SoundEffectsStatus.frequencyDynamic = isChecked;
                break;
            case R.id.check_box_reverberation:
                SoundEffectsStatus.reverberation = isChecked;
                break;
            case R.id.check_box_normalization:
                SoundEffectsStatus.normalization = isChecked;
                break;
            case R.id.check_box_stereo:
                SoundEffectsStatus.stereo = isChecked;
                break;
        }
    }
}