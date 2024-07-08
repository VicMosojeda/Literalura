package com.alura.literatura.service.impl;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

