/*

Guia 9 Relaciones Entre Clases Ejercicio 1

 Realizar el juego de la ruleta rusa de agua en Java. Como muchos saben, el juego se
trata de un número de jugadores, que, con un revolver de agua, el cual posee una sola
carga de agua, se dispara y se moja. Las clases a hacer del juego son las siguientes:
 -Clase Revolver de agua: esta clase posee los siguientes atributos: posición actual 
(posición del tambor que se dispara, puede que esté el agua o no) y posición agua (la 
posición del tambor donde se encuentra el agua). Estas dos posiciones, se generarán 
aleatoriamente.
Métodos:
• llenarRevolver(): le pone los valores de posición actual y de posición del agua. Los
valores deben ser aleatorios.
• mojar(): devuelve true si la posición del agua coincide con la posición actual
• siguienteChorro(): cambia a la siguiente posición del tambor
• toString(): muestra información del revolver (posición actual y donde está el agua)
 -Clase Jugador: esta clase posee los siguientes atributos: id (representa el número del
jugador), nombre (Empezara con Jugador más su ID, “Jugador 1” por ejemplo) y mojado
(indica si está mojado o no el jugador). El número de jugadores será decidido por el
usuario, pero debe ser entre 1 y 6. Si no está en este rango, por defecto será 6.
Métodos:
• disparo(Revolver r): el método, recibe el revolver de agua y llama a los métodos de
mojar() y siguienteChorro() de Revolver. El jugador se apunta, aprieta el gatillo y si el
revolver tira el agua, el jugador se moja. El atributo mojado pasa a false y el método
devuelve true, sino false.
 -Clase Juego: esta clase posee los siguientes atributos: Jugadores (conjunto de 
Jugadores) y Revolver
Métodos: 
• llenarJuego(ArrayList<Jugador>jugadores, Revolver r): este método recibe los
jugadores y el revolver para guardarlos en los atributos del juego.
• ronda(): cada ronda consiste en un jugador que se apunta con el revolver de agua y
aprieta el gatillo. Sí el revolver tira el agua el jugador se moja y se termina el juego,
sino se moja, se pasa al siguiente jugador hasta que uno se moje. Si o si alguien se
tiene que mojar. Al final del juego, se debe mostrar que jugador se mojó.
Pensar la lógica necesaria para realizar esto, usando los atributos de la clase Juego.

 */
package guia.pkg9.colecciones.ejercicio.pkg2;

import Entidades.Juego;
import Entidades.Jugador;
import Entidades.Revolver;
import Servicios.ServiciosJuego;
import Servicios.ServiciosRevolver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Guia9RelacionesEntreClasesEjercicio2 {

    public static void main(String[] args) {

        Scanner Leer = new Scanner(System.in).useDelimiter("\n");

        ArrayList<Jugador> jugadores = new ArrayList();

        ServiciosJuego sjo = new ServiciosJuego();

        Juego jo = new Juego();

        ServiciosRevolver sr = new ServiciosRevolver();

        // Crea los jugadores
        boolean valid = false;
        int numJugadores;
        do {
            if (valid == true) {
                System.out.println("Numero invalido.");
            }
            valid = true;
            System.out.println("¿Que cantidad de jugadores desea crear? (Numero entre '6' y '2' inclusives)");
            numJugadores = Leer.nextInt();
        } while (numJugadores < 2 || numJugadores > 6);

        int id = 0;
        for (int i = 0; i < numJugadores; i++) {
            if (i == 0) {
                id = 1;
            } else {
                id++;
            }
            String nombre = "Jugador " + id;
            Boolean mojado = false;
            Jugador j = new Jugador(id, nombre, mojado);
            jugadores.add(j);
        }

        // Crea el revolver
        Revolver r;
        Integer posicionActual = 1; // Siempre empezo desde el 1 porque considero que es la primera. 
        // Pero podria ser cualquier ortra posicion entre 1 y 6 siempre y cuando se agrege codigo que detecte cuando llega a la posicion 6.
        // para que en ese momento la siguiente posicion sea 1.
        Integer posicionAgua = (int) (Math.random() * (6 - 1)); //
        r = new Revolver(posicionActual, posicionAgua);

        jo = sjo.llenarJuego(jugadores, r); // Lleno el juego a traves del metodo en su clase servicio 
        // Si, se que es ridiculo ir y volver con los datos asi. Pero la estructura que plantea el enunciado tiene algunas inconcordancias.

        // Podriamos decir que aca comienza el juego. Los jugadors se pasan el revolver de agua hasta que alguno se moje.
        Jugador j = new Jugador(0, "Jugador 0", false);
        do {
            for (Iterator<Jugador> iterator = jugadores.iterator(); iterator.hasNext();) {
                /* Recorremos la lista de jugadores (La que ya tenemos aca. que deberia ser la misma que le pasamos al juego) */
                Jugador next = iterator.next();
                next = sjo.ronda(next, jo.getR());
                if (next.getMojado().equals(true)) {
                    j = next;
                    break;
                }

                // Segun el enunciado, el metodo siguienteChorro() esta dentro de el metoro disparo(Revolver r) de la clase ServiciosJugador.
                // El problema con esto es que inmplicaria retornar mas de un dato por metodo. 
                // Para poder retornar, no solo, el boolean que indica si esta mojado. Ademas se deberia retornar el revolver que contiene 
                // la nueva posicion actual. Hasta donde tengo entendido esto solo se podria hacer con un array de objetos. 
                // Por practicidad y tiempo prefiro modificar la estructura pasa lograr el resultado deseado de una forma mas limpia y clara.
                jo.setR(sr.siguienteChorro(jo.getR())); // Ya se, no queda muy limpio y claro pero es la forma de hacer que funcione 
                //medio siguiendo la estructura del enunciado jajajajaj. ¯\_( ͡❛. ͡❛)_/¯
            }
        } while (j.getMojado().equals(false));
        /*Este bucle esta para que alguien se moje si son menos de 6 jugadores*/
        
        System.out.println("====================================================");
        System.out.println("El " + j.getNombre() + " se mojo.");
        System.out.println("Por lo que perdio el juego."); 
    }

}
