package com.virtualgame.exception;

public record ValidationError(String field, String message) {}
