package com.unicornstudio.lanball.server.commons;

import com.esotericsoftware.kryo.Kryo;
import com.google.common.reflect.ClassPath;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;

import java.util.ArrayList;
import java.util.HashSet;

public class NetworkClassRegisterer {

    public static void register(Kryo kryo) {
        kryo.register(byte[].class);
        kryo.register(ArrayList.class);
        kryo.register(HashSet.class);
        ClassLoader loader = NetworkObject.class.getClassLoader();
        try {
            ClassPath.from(loader).getTopLevelClassesRecursive("com.unicornstudio.lanball.network.model")
                    .forEach(classInfo -> {
                        try {
                            kryo.register(loader.loadClass(classInfo.getName()));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
