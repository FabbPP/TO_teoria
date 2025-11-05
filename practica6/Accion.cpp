#include "Accion.h"

Accion::Accion(const std::string& nombre) : nombre(nombre) {}

std::string Accion::getNombre() const {
    return nombre;
}