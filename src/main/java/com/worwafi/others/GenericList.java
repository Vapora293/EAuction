package com.worwafi.others;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//TODO genericka trieda
public class GenericList<T> {
    private ObservableList<T> list;

    public GenericList() {
        list = FXCollections.observableArrayList();
    }

    public ObservableList<T> getList() {
        return list;
    }

    public String[] allINames() {
        String[] names = new String[list.size()];
        int i = 0;
        for (Object o : list) {
            names[i] = ((Starter) o).getName();
            i++;
        }
        return names;
    }

    public String[] getAllData() {
        String[] data = new String[list.size()];
        int i = 0;
        for (Object o : list) {
            data[i] = ((Starter) o).getAllData();
        }
        return data;
    }
}
