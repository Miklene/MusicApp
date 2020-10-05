package com.example.musicapp.wave_dialog;

import android.content.Context;
import android.widget.Toast;

import com.example.musicapp.common.Type;
import com.example.musicapp.model.Waves;
import com.example.musicapp.wave.WaveFactory;

import org.apache.commons.math3.exception.NullArgumentException;

public class WaveDialogPresenter {

    private WaveDialogView waveDialogView;
    private Context context;
    private Waves waves = Waves.getInstance();

    public WaveDialogPresenter(WaveDialogView waveDialogView, Context context) {
        this.waveDialogView = waveDialogView;
        this.context = context;

    }

    public void onNegativeButtonClicked(){
       waveDialogView.finishActivity();
    }

    public void onPositiveButtonClicked() {
        Type type;
        float frequency;
        int harmonicsNumber;
        try {
            type =checkType(waveDialogView.getType());
            frequency = checkFrequency(waveDialogView.getFrequencyFromEditText());
            harmonicsNumber = checkHarmonicsNumber(waveDialogView.getHarmonicsNumberFromEditText());
        } catch(Exception e){
            return;
        }
        waves.addWave(type,frequency,harmonicsNumber);
        waveDialogView.finishActivity();
    }

    private Type checkType(Type type) throws NullPointerException{
        if(type == null) {
            showMessage("Укажите тип волны!");
            throw new NullPointerException();
        }
        return type;
    }

    private int checkHarmonicsNumber(String harmonicsNumber){
        try {
            throwExceptionIfMoreThan31(Integer.parseInt(harmonicsNumber));
        }catch (NumberFormatException e){
            showMessage("Введите количество гармоник!");
            throw new NumberFormatException();
        } catch (IllegalArgumentException e){
            showMessage("Количество гармоник должно быть меньше 32!");
            throw new IllegalArgumentException();
        }
        return Integer.parseInt(harmonicsNumber);
    }

    private float checkFrequency(String frequency){
        try {
            throwExceptionIfZero(Float.parseFloat(frequency));
            throwExceptionIfMoreThan3500(Float.parseFloat(frequency));
        }catch (NumberFormatException e){
            showMessage("Введите частоту!");
            throw new NumberFormatException();
        } catch (NullArgumentException e){
            showMessage("Частота не может быть 0!");
            throw new NullArgumentException();
        }
        catch (IllegalArgumentException e){
            showMessage("Частота не может быть больше 3500 Гц!");
            throw new IllegalArgumentException();
        }
        return Float.parseFloat(frequency);
    }

    private void throwExceptionIfMoreThan31(int harmonicsNumber) throws IllegalArgumentException{
        if(harmonicsNumber>31)
            throw new IllegalArgumentException();
    }

    private void throwExceptionIfZero(float frequency)  throws NullArgumentException{
        if (frequency == 0) {
           throw new NullArgumentException();
        }
    }

    private void throwExceptionIfMoreThan3500(float frequency) throws IllegalArgumentException{
        if(frequency>3500){
            throw new IllegalArgumentException();
        }
    }

    private void showMessage(String message){
        Toast toast = Toast.makeText((Context) waveDialogView, message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

}
