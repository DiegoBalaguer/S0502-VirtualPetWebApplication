package com.virtualgame.exception;

import java.util.List;

public record ValidationResponseError(List<ValidationError> message) {}
