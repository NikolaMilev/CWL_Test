package com.milev.nikola.cwl_test.listeners;

import com.milev.nikola.cwl_test.rest_client.charities.Charity;

import java.util.List;

public interface GetCharitiesListener {
    void onGetCharitiesSuccess(List<Charity> charities);
    void onGetCharitiesFail();

}
