package taller.filters;

import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import taller.utils.GenerateCanonicalSmile;

import java.util.LinkedHashMap;
import java.util.Map;

public class RemoveMixtureCompounds {

    private RemoveMixtureCompounds() {
    }

    public static Map<String, IAtomContainerSet> clean(IAtomContainerSet molecules) throws Exception {
        Map<String,IAtomContainerSet> sets = new LinkedHashMap<>();
        sets.put("complete",new AtomContainerSet());
        sets.put("mixture",new AtomContainerSet());
        for(int i = 0 ; i < molecules.getAtomContainerCount() ; i++){
            IAtomContainer mol = molecules.getAtomContainer(i);
            if(isMixtureMolecule(mol)) {
                System.out.printf("Molecule with title %s and canonical smile %s removed cause " +
                        "is a mixture compound\n", mol.getTitle(), GenerateCanonicalSmile.getCanonicalSmile(mol));
                sets.get("mixture").addAtomContainer(mol);
            }else
                sets.get("complete").addAtomContainer(mol);
        }
        return sets;
    }

    private static boolean isMixtureMolecule(IAtomContainer molecule) {
        if (!ConnectivityChecker.isConnected(molecule)) {
            if (isNeutral(molecule) || !haveOppositeChargesAmongFragments(molecule)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNeutral(IAtomContainer molecule) {
        double negativeCharge = (double) AtomContainerManipulator.getTotalNegativeFormalCharge(molecule);
        double positiveCharge = (double)AtomContainerManipulator.getTotalPositiveFormalCharge(molecule);
        return negativeCharge == 0.0 && positiveCharge == 0.0;
    }

    private static boolean haveOppositeChargesAmongFragments(IAtomContainer molecule) {
        if (ConnectivityChecker.isConnected(molecule)) {
            return false;
        } else {
            IAtomContainerSet molSet = ConnectivityChecker.partitionIntoMolecules(molecule);
            boolean[][] charges = new boolean[molSet.getAtomContainerCount()][2];

            int i;
            for(i = 0; i < molSet.getAtomContainerCount(); ++i) {
                IAtomContainer mol = molSet.getAtomContainer(i);
                charges[i][0] = AtomContainerManipulator.getTotalNegativeFormalCharge(mol) != 0;
                charges[i][1] = AtomContainerManipulator.getTotalPositiveFormalCharge(mol) != 0;
            }

            for(i = 0; i < molSet.getAtomContainerCount() - 1; ++i) {
                for(int j = i + 1; j < molSet.getAtomContainerCount(); ++j) {
                    if (charges[i][0] && charges[j][1] || charges[i][1] && charges[j][0]) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
