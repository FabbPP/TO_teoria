#ifndef INTERFACES_H
#define INTERFACES_H
//se creo esta clase para aplicar lode ISP (Las interfaces deben ser específicas para los clientes.)
#include <string>

// Para mostrar mensajes 
class IMostrador {
public:
    virtual ~IMostrador() = default;
    virtual void mostrarMensaje(const std::string& mensaje, const std::string& color = "") const = 0;
    virtual void mostrarMensajeCombate(const std::string& mensaje) const = 0;
};

// Para calcular daño
class ICalculadorDano {
public:
    virtual ~ICalculadorDano() = default;
    virtual int calcularDano(int ataqueBase, int potencia, int defensaObjetivo, double multiplicador) const = 0;
};

// Para calcular multiplicadores de tipo
class ICalculadorMultiplicador {
public:
    virtual ~ICalculadorMultiplicador() = default;
    virtual double calcularMultiplicador(double fuerzaAtaque, double fuerzaDefensa) const = 0;
};

#endif // INTERFACES_H