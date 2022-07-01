package com.company.structureinventorysystem.utils.io;

import java.util.List;

public interface FileOperation<T> {
    List<T> read(String path);

    void write(String path, List<T> content);
}
