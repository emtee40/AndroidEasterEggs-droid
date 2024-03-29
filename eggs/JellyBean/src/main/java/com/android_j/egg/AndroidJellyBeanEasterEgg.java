package com.android_j.egg;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import com.dede.basic.provider.BaseEasterEgg;
import com.dede.basic.provider.ComponentProvider;
import com.dede.basic.provider.EasterEgg;
import com.dede.basic.provider.EasterEggProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoSet;
import kotlin.ranges.IntRange;

@Module
@InstallIn(SingletonComponent.class)
public class AndroidJellyBeanEasterEgg implements EasterEggProvider, ComponentProvider {

    @IntoSet
    @Provides
    @Singleton
    @Override
    public BaseEasterEgg provideEasterEgg() {
        return new EasterEgg(
                R.drawable.j_android_logo,
                R.string.j_egg_name,
                R.string.j_android_nickname,
                new IntRange(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.JELLY_BEAN_MR2),
                false
        ) {
            @Override
            public Class<? extends Activity> provideEasterEgg() {
                return PlatLogoActivity.class;
            }

            @Override
            public SnapshotProvider provideSnapshotProvider() {
                return new SnapshotProvider();
            }
        };
    }

    @IntoSet
    @Provides
    @Singleton
    @Override
    public Component provideComponent() {
        return new Component(
                R.drawable.j_redbean2,
                R.string.j_jelly_bean_dream_name,
                R.string.j_android_nickname,
                new IntRange(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.JELLY_BEAN_MR2)
        ) {
            @Override
            public boolean isSupported() {
                return true;
            }

            @Override
            public boolean isEnabled(@NonNull Context context) {
                ComponentName cn = new ComponentName(context, BeanBagDream.class);
                return Component.isEnabled(cn, context);
            }

            @Override
            public void setEnabled(@NonNull Context context, boolean enable) {
                ComponentName cn = new ComponentName(context, BeanBagDream.class);
                Component.setEnable(cn, context, enable);
            }
        };
    }
}
