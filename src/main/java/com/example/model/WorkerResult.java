package com.example.model;

public record WorkerResult(
    String workerType,
    String result,
    boolean success
) {}