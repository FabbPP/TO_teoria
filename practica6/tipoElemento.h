#ifndef TIPO_ELEMENTO_H
#define TIPO_ELEMENTO_H

#include <string>
#include <map>
#include <memory>
#include <iostream>

// Clase base genérica para todos los tipos de elementos
class TipoElemento {
protected:
    std::string nombre;
    double fuerza;  // Fuerza genérica
    
public:
    TipoElemento(const std::string& n, double f) : nombre(n), fuerza(f) {}
    virtual ~TipoElemento() = default;

    std::string getNombre() const { return nombre; }
    double getFuerza() const { return fuerza; }

    virtual void mostrarInfo() const {
        std::cout << "Tipo: " << nombre << ", Fuerza: " << fuerza << std::endl;
    }
};

class TipoNormal : public TipoElemento {
public:
    TipoNormal() : TipoElemento("Normal", 50.0) {}  
    void mostrarInfo() const override {
        std::cout << "Tipo Normal - Fuerza: " << fuerza << std::endl;
    }
};

class TipoFuego : public TipoElemento {
public:
    TipoFuego() : TipoElemento("Fuego", 75.0) {} 
    void mostrarInfo() const override {
        std::cout << "Tipo Fuego - Fuerza: " << fuerza << std::endl;
    }
};

class TipoAgua : public TipoElemento {
public:
    TipoAgua() : TipoElemento("Agua", 65.0) {} 
    void mostrarInfo() const override {
        std::cout << "Tipo Agua - Fuerza: " << fuerza << std::endl;
    }
};

class TipoElectrico : public TipoElemento {
public:
    TipoElectrico() : TipoElemento("Electrico", 80.0) {} 
    void mostrarInfo() const override {
        std::cout << "Tipo Electrico - Fuerza: " << fuerza << std::endl;
    }
};


#endif // TIPO_ELEMENTO_H
