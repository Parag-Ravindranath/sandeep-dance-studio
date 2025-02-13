package com.project.sds.service;

import com.project.sds.models.Batch;

import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

public interface BatchService {

    Batch save(Batch batch);

    List<Batch> listAll();
}
