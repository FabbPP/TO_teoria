#include "Juego.h"
#include <iostream>
#include <cstdlib>
#include <ctime>

int main() {
    srand(time(0));
    
    Juego miJuego;
    miJuego.iniciarCombate();
    
    return 0;
}