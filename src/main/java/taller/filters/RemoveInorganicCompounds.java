package taller.filters;

import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import taller.utils.GenerateCanonicalSmile;

import java.util.LinkedHashMap;
import java.util.Map;

public class RemoveInorganicCompounds {
    protected RemoveInorganicCompounds() {
    }

    public static Map<String,IAtomContainerSet> clean(IAtomContainerSet molecules) throws Exception {

        Map<String,IAtomContainerSet> sets = new LinkedHashMap<>();
        sets.put("organic",new AtomContainerSet());
        sets.put("inorganic",new AtomContainerSet());
        for(int i = 0 ; i < molecules.getAtomContainerCount() ; i++){
            IAtomContainer mol = molecules.getAtomContainer(i);
            if(!haveCarbonAtom(mol)) {
                System.out.printf("Molecule with title %s and canonical smile %s removed cause " +
                        "is an inorganic compound%n", mol.getTitle(), GenerateCanonicalSmile.getCanonicalSmile(mol));
                sets.get("inorganic").addAtomContainer(mol);
            }else
                sets.get("organic").addAtomContainer(mol);
        }
        return sets;
    }

    private static boolean haveCarbonAtom(IAtomContainer molecule) {
        for(int i = 0; i < molecule.getAtomCount(); ++i) {
            if (molecule.getAtom(i).getSymbol().equalsIgnoreCase("c")) {
                return true;
            }
        }
        return false;
    }
}
