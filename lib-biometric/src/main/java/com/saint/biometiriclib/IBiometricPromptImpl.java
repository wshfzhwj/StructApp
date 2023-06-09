package com.saint.biometiriclib;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

public interface IBiometricPromptImpl {
    void authenticate(@NonNull CancellationSignal cancel,
                      @NonNull BiometricPromptManager.OnBiometricIdentifyCallback callback);
}
