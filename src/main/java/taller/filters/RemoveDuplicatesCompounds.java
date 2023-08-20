package taller.filters;

import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import taller.utils.GenerateCanonicalSmile;

import java.util.*;

public class RemoveDuplicatesCompounds {

    public static Map<String, IAtomContainerSet> clean(IAtomContainerSet molecules) throws Exception {

        List<String> uniqueMolecules = new LinkedList<>();;
        Map<String,IAtomContainerSet> sets = new LinkedHashMap<>();
        sets.put("withoutDuplicated",new AtomContainerSet());
        sets.put("duplicated",new AtomContainerSet());

        for(int i = 0 ; i < molecules.getAtomContainerCount() ; i++){
            IAtomContainer mol = molecules.getAtomContainer(i);
            String canonical = GenerateCanonicalSmile.getCanonicalSmile(mol);
            if (uniqueMolecules.contains(canonical)) {
                 System.out.printf("Molecule with title %s and canonical smile %s removed cause " +
                        "is an duplicated compound%n", mol.getTitle(), GenerateCanonicalSmile.getCanonicalSmile(mol));
                sets.get("duplicated").addAtomContainer(mol);
            }else {
                sets.get("withoutDuplicated").addAtomContainer(mol);
                uniqueMolecules.add(canonical);
            }
        }
        return sets;
    }
}
