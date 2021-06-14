package com.estimote.proximity.estimote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class ProximityContentManager {

    private Context context;
    private EstimoteCloudCredentials cloudCredentials;
    private ProximityObserver.Handler proximityObserverHandler;
    public LinkedBlockingDeque<String> queue = new LinkedBlockingDeque();

    public ProximityContentManager(Context context, EstimoteCloudCredentials cloudCredentials) {
        this.context = context;
        this.cloudCredentials = cloudCredentials;
    }

    public void start() {

        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("hyun-sun-suh-s-proximity-f-ote")
                .inCustomRange(1)
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {

                        for (ProximityZoneContext proximityContext : contexts) {
                            switch (proximityContext.getDeviceId()) {
                                case "1d9141470c04e7f17e9091a66414d21d":
                                    queue.add("Entrance");
                                    break;
                                case "0242e87e1b6fa79ca59fa639ed06d62b":
                                    queue.add("Tiger Gym");
                                    break;
                                case "bedb2ff6f3346a8f0371b8801a246b36":
                                    queue.add("Auditorium");
                                    break;
                                case "3755cdfd3d61aede385d10ec88a5991e":
                                    queue.add("Gwangju");
                                    break;
                            }
                            break;
                        }
                        return null;
                    }
                })
                .build();

        proximityObserverHandler = proximityObserver.startObserving(zone);
    }

    public void stop() {
        proximityObserverHandler.stop();
    }
}
