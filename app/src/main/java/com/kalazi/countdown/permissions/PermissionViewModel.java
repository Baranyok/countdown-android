package com.kalazi.countdown.permissions;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PermissionViewModel extends ViewModel {
    private final MutableLiveData<Boolean> permsGranted = new MutableLiveData<>(false);

    ////// Public getters

    @NonNull
    public LiveData<Boolean> getPermsGranted() {
        return permsGranted;
    }

    public void setPermsGranted(boolean value) {
        permsGranted.setValue(value);
    }

    public void checkPerms(Context ctx) {
        permsGranted.postValue(PermissionManager.checkPermissions(ctx));
    }
}
