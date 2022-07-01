package com.company.structureinventorysystem.utils.converter;

import com.company.structureinventorysystem.utils.io.FileOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlScriptExtractor {

    private final FileOperation<String> operation;

    public SqlScriptExtractor(FileOperation<String> operation) {
        this.operation = operation;
    }

    public List<String> get(String path) {
        return operation.read(path);
    }

    public List<String> extract(String path, int attributeIndex) {
        if (attributeIndex < 0) throw new IllegalArgumentException("Attribute index must not be less than 0");
        List<String> input = operation.read(path);
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*\\((.+)\\)\\s*[,;]");
        for (String i : input) {
            for (String s : i.split("\\s*\\)\\s*[,;]\\s*")) {
                if (!s.endsWith(")")) {
                    s = s.replace("()", "[]");
                    s = s.concat("),");
                }
                Matcher matcher = pattern.matcher(s);
                if (matcher.matches()) {
                    String attrSeq = matcher.group(1).replace("'", "");
                    attrSeq = attrSeq.replace("[]", "()");
                    String[] attributes = attrSeq.split(",");
                    int attrLastIdx = attributes.length - 1;
                    if (attrLastIdx < attributeIndex)
                        throw new IllegalArgumentException(String.format("Attribute index %s is out of range. Last attributes array index is %s", attributeIndex, attrLastIdx));
                    results.add(attributes[attributeIndex].trim());
                }
            }
        }
        return results;
    }

}
