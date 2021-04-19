/**
 * Noms des étudiants : // Axel Vallon, Matthieu Godi
 */

package ch.heigvd.ser.labo2;

import org.jdom2.Document;
import org.jdom2.Element;

import java.io.*;
import java.util.List;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

// TODO : Vous avez le droit d'ajouter des instructions import si cela est nécessaire

class Main {

    public static void main(String... args) throws Exception {

        Document document = readDocument(new File("tournois_fse.xml"));

        Element root = document.getRootElement();
        List<Element> tournois = root.getChild("tournois").getChildren();

        writePGNfiles(tournois);

    }

    /**
     * Cette méthode doit parser avec SAX un fichier XML (file) et doit le transformer en Document JDOM2
     * @param file
     */
    private static Document readDocument(File file) throws JDOMException, IOException {
        return new SAXBuilder().build(file); //build en 1 ligne
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
        for(Element tournoi : tournois){

            String nomTournoi = tournoi.getAttributeValue("nom").replaceAll("[ ',.]", "_");
            List parties = tournoi.getChild("parties").getChildren();
            for (int noPartie = 0; noPartie < parties.size(); noPartie++){
                File fichierPGN = new File(nomTournoi + "_" + noPartie + ".pgn");
                PrintWriter os = null;
                try {
                    os = new PrintWriter(new FileWriter(fichierPGN));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                List coups = ((Element) parties.get(noPartie)).getChild("coups").getChildren();
                for (int noCoup = 0; noCoup < coups.size(); noCoup++){
                    String ligneToWrite = String.valueOf(noCoup/2) + " ";

                    String coup_special = ((Element) coups.get(noCoup)).getAttributeValue("coup_special");
                    Element typeCoup = ((Element) coups.get(noCoup)).getChild("deplacement");
                    if (typeCoup != null) { // on a un coup normal
                        String piece = typeCoup.getAttributeValue("piece");
                        String case_depart = typeCoup.getAttributeValue("case_depart");
                        String case_arrivee = typeCoup.getAttributeValue("case_arrivee");
                        String elimination = typeCoup.getAttributeValue("elimination");
                        String promotion = typeCoup.getAttributeValue("promotion");
                        if (piece != null && case_arrivee != null)
                            System.out.println(piece + " vers " + case_arrivee);
                    }
                    else { //on a un roque
                        typeCoup = ((Element) coups.get(noCoup)).getChild("roque");
                        String typeRoque = typeCoup.getAttributeValue("type");
                    }
                }
            }
        }
    }

}