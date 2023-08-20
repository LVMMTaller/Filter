package taller.filters;

import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import taller.utils.GenerateCanonicalSmile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RemoveOrganometalicsCompounds {
    private RemoveOrganometalicsCompounds() {
    }

    public static Map<String, IAtomContainerSet> clean(IAtomContainerSet molecules) throws Exception {
        Map<String,IAtomContainerSet> sets = new LinkedHashMap<>();
        sets.put("complete",new AtomContainerSet());
        sets.put("organometalics",new AtomContainerSet());
        for(int i = 0 ; i < molecules.getAtomContainerCount() ; i++){
            IAtomContainer mol = molecules.getAtomContainer(i);
            if(isOrganometalic(mol)) {
                System.out.printf("Molecule with title %s and canonical smile %s removed cause " +
                        "is an organometalics compound\n", mol.getTitle(), GenerateCanonicalSmile.getCanonicalSmile(mol));
                sets.get("organometalics").addAtomContainer(mol);
            }else
                sets.get("complete").addAtomContainer(mol);
        }
        return sets;
    }

    protected static boolean isOrganometalic(IAtomContainer molecule) throws Exception {
        if (ConnectivityChecker.isConnected(molecule)) {
            if (haveMetalAtom(molecule)) {
                return true;
            }
        }
        return false;
    }

    protected static final boolean haveMetalAtom(IAtomContainer molecule) {
        for(int i = 0; i < molecule.getAtomCount(); ++i) {
            if (!getNoMetals().contains(molecule.getAtom(i).getSymbol())) {
                return true;
            }
        }

        return false;
    }

    private static ArrayList getNoMetals() {
        ArrayList<String> noMetals = new ArrayList();
        noMetals.add("H");
        noMetals.add("He");
        noMetals.add("C");
        noMetals.add("N");
        noMetals.add("O");
        noMetals.add("F");
        noMetals.add("Ne");
        noMetals.add("P");
        noMetals.add("S");
        noMetals.add("Cl");
        noMetals.add("Ar");
        noMetals.add("Se");
        noMetals.add("Br");
        noMetals.add("Kr");
        noMetals.add("I");
        noMetals.add("Xe");
        noMetals.add("At");
        noMetals.add("Rn");
        return noMetals;
    }
}
