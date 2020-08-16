package me.lukeben.verification;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import me.lukeben.backend.json.logic.DiskUtil;
import me.lukeben.backend.json.logic.PersistentFile;
import me.lukeben.verification.utils.PurchasedResource;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerData extends PersistentFile {

    @Getter
    private transient static final BuyerData instance = new BuyerData();

    File file = getFile(true, "buyer_data");

    @Override
    public void load() {
        try {
            Object o = gson.fromJson(DiskUtil.read(file), new TypeToken<HashMap<String, List<PurchasedResource>>>() {}.getType());
            if(o != null) {
                VerificationManager.getInstance().getPendingVerification().putAll((Map<? extends String, ? extends List<PurchasedResource>>) o);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            save(true, VerificationManager.getInstance().getPendingVerification(), file);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
