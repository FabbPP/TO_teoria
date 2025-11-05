#ifndef ITEM_H
#define ITEM_H

#include <string>
#include "Pokemon.h"

using namespace std;

class Item {
private:
    string nombre;
    int efectoCuracion;
    int usos;

public:
    Item(string n, int e, int u);

    // Getters
    string getNombre() const;
    int getEfecto() const;
    int getUsos() const;
    bool disponible() const;

    void usar(Pokemon& p);
    void mostrar() const;
};

#endif // ITEM_H