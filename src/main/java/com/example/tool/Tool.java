package com.example.tool;

import java.util.Map;

public interface Tool {
    String getName();
    String getDescription();
    Object execute(Map<String, Object> parameters);
}