#include "Item.h"
#include <iostream>
#include "global.h"

Item::Item(string n, int e, int u) : nombre(n), efectoCuracion(e), usos(u) {}

string Item::getNombre() const { return nombre; }
int Item::getEfecto() const { return efectoCuracion; }
int Item::getUsos() const { return usos; }
bool Item::disponible() const { return usos > 0; }

void Item::usar(Pokemon& p) {
    if (usos > 0) {
        cout << GREEN << "Se usa " << nombre << " en " 
             << p.getNombre() << " +" << efectoCuracion 
             << " de vida!" << RESET << endl;
        p.curar(efectoCuracion);
        usos--;
    } else {
        cout << RED << "No quedan " << nombre << "!" << RESET << endl;
    }
}

void Item::mostrar() const {
    cout << nombre << " (cura " << efectoCuracion 
         << ", usos: " << usos << ")";
}