/**
 * Noms des étudiants : // TODO : A compléter...
 */

package ch.heigvd.ser.labo2;

import org.jdom2.Document;
import org.jdom2.Element;

import java.io.*;
import java.util.List;

import org.jdom2.JDOMException;

// TODO : Vous avez le droit d'ajouter des instructions import si cela est nécessaire

class Main {

    public static void main(String... args) throws Exception {

        Document document = readDocument(new File("tournois_fse.xml"));

        // TODO : Compléter en une ligne l'initialisation de cette liste d'éléments (c'est la seule ligne que vous pouvez modifier ici.
        Element root = document.getRootElement();
        List<Element> tournois = null /* A compléter en utilisant la variable root */;

        writePGNfiles(tournois);

    }

    /**
     * Cette méthode doit parser avec SAX un fichier XML (file) et doit le transformer en Document JDOM2
     * @param file
     */
    private static Document readDocument(File file) throws JDOMException, IOException {

        // TODO : A compléter... (ne doit pas faire plus d'une ligne). Vous êtes autorisés à créer des méthodes utilitaires, mais pas des classes
        return null;

    }

    /**
     * Cette méthode doit générer un fichier PGN pour chaque partie de chaque tournoi recu en paramètre comme indiqué dans la donnée
     *
     * Le nom d'un fichier PGN doit contenir le nom du tournoi ainsi que le numéro de la partie concernée
     *
     * Nous vous conseillons d'utiliser la classe PrinterWriter pour écrire dans les fichiers PGN
     *
     * Vous devez utiliser les classes qui sont dans le package coups pour générer les notations PGN des coups d'une partie
     *
     * @param tournois Liste des tournois pour lesquelles écrire les fichiers PGN
     *
     *                 (!!! Un fichier par partie, donc cette méthode doit écrire plusieurs fichiers PGN !!!)
     */
    private static void writePGNfiles(List<Element> tournois) {

        // TODO : A compléter... selon ce qui est indiqué dans la donnée... vous devez comprendre la DTD fournie avant de faire ceci !

    }

}