/**
 * Noms des étudiants : // Axel Vallon, Matthieu Godi
 */

package ch.heigvd.ser.labo2;

import ch.heigvd.ser.labo2.coups.*;
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
    private static void writePGNfiles(List<Element> tournois) throws Exception {
        for(Element tournoi : tournois){

            String nomTournoi = tournoi.getAttributeValue("nom").replaceAll("[ ',.]", "_");
            List parties = tournoi.getChild("parties").getChildren();
            for (int noPartie = 0; noPartie < parties.size(); noPartie++){
                File fichierPGN = new File(nomTournoi + "_" + noPartie + ".pgn");
                PrintWriter os = null;
                os = new PrintWriter(new FileWriter(fichierPGN));

                List coups = ((Element) parties.get(noPartie)).getChild("coups").getChildren();
                String notationPGN;
                for (int noCoup = 0; noCoup < coups.size(); noCoup++){

                    CoupSpecial coup_special = convertCoupSpecial
                            (((Element) coups.get(noCoup)).getAttributeValue("coup_special"));

                    Element typeCoup = ((Element) coups.get(noCoup)).getChild("deplacement");
                    if (typeCoup != null) { // on a un coup normal
                        //on convertit les déplacements dans les types appropriés
                        TypePiece piece = convertTypePiece(typeCoup.getAttributeValue("piece"));
                        Case case_depart = convertCase(typeCoup.getAttributeValue("case_depart"));
                        Case case_arrivee = convertCase(typeCoup.getAttributeValue("case_arrivee"));
                        TypePiece elimination = convertTypePiece(typeCoup.getAttributeValue("elimination"));
                        TypePiece promotion = convertTypePiece(typeCoup.getAttributeValue("promotion"));
                        Deplacement deplacement = new Deplacement(piece,elimination,promotion,coup_special,
                                case_depart,case_arrivee);
                        notationPGN = deplacement.notationPGN();

                    }
                    else { //on a un roque
                        typeCoup = ((Element) coups.get(noCoup)).getChild("roque");
                        TypeRoque typeRoque = convertRoque(typeCoup.getAttributeValue("type"));
                        Roque roque = new Roque(coup_special,typeRoque);
                        notationPGN = roque.notationPGN();
                    }

                    if (noCoup % 2 == 0){
                        os.print(noCoup/2 + " " + notationPGN);
                    }
                    else{
                        os.println(" " + notationPGN);
                    }
                }
                os.close();
            }
        }

    }
    private static TypePiece convertTypePiece(String piece){
        if (piece == null)
            return null;
        switch (piece){
            case "Tour": return TypePiece.Tour;
            case "Cavalier": return TypePiece.Cavalier;
            case "Fou": return TypePiece.Fou;
            case "Dame": return TypePiece.Dame;
            case "Roi": return TypePiece.Roi;
            case "Pion": return TypePiece.Pion;
            default : return null;
        }
    }

    private static Case convertCase(String case_str){
        if (case_str == null){
            return null;
        }
        return new Case(case_str.charAt(0),case_str.charAt(1)-'0');
    }

    private static CoupSpecial convertCoupSpecial(String coup_special){
        if (coup_special == null)
            return null;
        switch (coup_special){
            case "mat": return CoupSpecial.MAT;
            case "echec": return CoupSpecial.ECHEC;
            case "nulle": return CoupSpecial.NULLE;
            default : return null;
        }
    }

    private static TypeRoque convertRoque(String roque) {
        if (roque == null)
            return null;
        switch (roque) {
            case "petit_roque":
                return TypeRoque.PETIT;
            case "grand_roque":
                return TypeRoque.GRAND;
            default:
                return null;
        }
    }

}