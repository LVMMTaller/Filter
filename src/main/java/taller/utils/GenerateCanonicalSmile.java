package taller.utils;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;

public class GenerateCanonicalSmile {
    private GenerateCanonicalSmile() {
    }

    public static String getCanonicalSmile(IAtomContainer molecule) throws Exception {
        AddHydrogensAtom addHydrogensAtom = new AddHydrogensAtom(true);
        molecule = addHydrogensAtom.executeAlgorithm(molecule);
        SmilesGenerator smilesGen = new SmilesGenerator(SmiFlavor.Canonical);
        return smilesGen.create(molecule);
    }
}
