package com.ingsoft.trueque.dto.response;

import java.io.Serializable;

public record GetReputacion(
        Long idUsuario,
        Double reputacionPromedio,
        Long totalResenhas)
implements Serializable {}

