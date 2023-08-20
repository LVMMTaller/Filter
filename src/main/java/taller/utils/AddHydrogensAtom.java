package taller.utils;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import java.util.Properties;

public class AddHydrogensAtom {
    private boolean generate2DCoordenates;

    public AddHydrogensAtom(boolean generate2DCoordenates) {
        this.generate2DCoordenates = generate2DCoordenates;
    }

    public void setGenerate2DCoordenates(boolean generate2DCoordenates) {
        this.generate2DCoordenates = generate2DCoordenates;
    }

    public IAtomContainer executeAlgorithm(IAtomContainer molecule) throws Exception {
        AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(molecule);
        CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance((molecule).getBuilder());
        adder.addImplicitHydrogens(molecule);
        AtomContainerManipulator.convertImplicitToExplicitHydrogens(molecule);
        if (this.generate2DCoordenates) {
            StructureDiagramGenerator sdg = new StructureDiagramGenerator(molecule);
            sdg.generateCoordinates();
            molecule = sdg.getMolecule();
        }
        return molecule;
    }
}
