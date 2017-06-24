package com.example.liebherr_365_gesundheitsapp.ModulCoffee;

/**
 * Created by mpadmin on 24.06.2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

/**
 * A {@link android.preference.Preference} that displays a number picker as a dialog.
 */
public class NumberPickerPreferenceCoffee extends DialogPreference {

    // allowed range
    private static final int MAX_VALUE = 10;
    private static final int MIN_VALUE = 2;
    // enable or disable the 'circular behavior'
    private static final boolean WRAP_SELECTOR_WHEEL = false;

    private NumberPicker picker;
    private int value;

    public NumberPickerPreferenceCoffee(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerPreferenceCoffee(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        picker.setMinValue(MIN_VALUE);
        picker.setMaxValue(MAX_VALUE);
        picker.setWrapSelectorWheel(WRAP_SELECTOR_WHEEL);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            picker.clearFocus();
            int newValue = picker.getValue();
            if (callChangeListener(newValue)) {
                setValue(newValue);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return ModulCoffee.getMaxGlasses();
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(ModulCoffee.getMaxGlasses());
    }

    public void setValue(int value) {
        this.value = value;
        ModulCoffee.setMaxGlasses(value);
    }

    public int getValue() {
        return this.value;
    }
}