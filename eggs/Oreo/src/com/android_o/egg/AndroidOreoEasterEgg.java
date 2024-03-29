package com.android_o.egg;

import android.app.Activity;
import android.os.Build;

import com.dede.basic.provider.BaseEasterEgg;
import com.dede.basic.provider.EasterEgg;
import com.dede.basic.provider.EasterEggGroup;
import com.dede.basic.provider.EasterEggProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoSet;

@Module
@InstallIn(SingletonComponent.class)
public class AndroidOreoEasterEgg implements EasterEggProvider {

    @IntoSet
    @Provides
    @Singleton
    @Override
    public BaseEasterEgg provideEasterEgg() {
        return new EasterEggGroup(
                new EasterEgg(
                        R.drawable.o_android_logo,
                        R.string.o_app_name,
                        R.string.o_android_nickname,
                        Build.VERSION_CODES.O,
                        true
                ) {
                    @Override
                    public Class<? extends Activity> provideEasterEgg() {
                        return PlatLogoActivity.class;
                    }

                    @Override
                    public SnapshotProvider provideSnapshotProvider() {
                        return new SnapshotProvider();
                    }
                },
                new EasterEgg(
                        R.drawable.o_android_logo,
                        R.string.o_app_name,
                        R.string.o_android_nickname,
                        Build.VERSION_CODES.O_MR1,
                        true
                ) {
                    @Override
                    public Class<? extends Activity> provideEasterEgg() {
                        return PlatLogoActivity.Point1.class;
                    }

                    @Override
                    public SnapshotProvider provideSnapshotProvider() {
                        return new SnapshotProvider(true);
                    }
                }
        );
    }
}
