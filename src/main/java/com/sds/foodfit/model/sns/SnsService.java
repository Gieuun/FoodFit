package com.sds.foodfit.model.sns;

import com.sds.foodfit.domain.Sns;

public interface SnsService {

    public Sns select(int snsIdx);

    public Sns selectByName(String snsName);
}
