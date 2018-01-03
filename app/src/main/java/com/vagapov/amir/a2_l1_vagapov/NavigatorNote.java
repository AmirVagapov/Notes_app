package com.vagapov.amir.a2_l1_vagapov;


import java.util.UUID;

public interface NavigatorNote {

    void openNote(UUID id);

    void editNote(UUID id);

    void createNote();

    void backToList();

}
