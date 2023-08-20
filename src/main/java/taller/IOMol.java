package taller;

import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.io.iterator.DefaultIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;

public class IOMol {

    private IOMol() {
    }

    public static IAtomContainerSet readSdf(String path) throws FileNotFoundException {
        IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
        FileInputStream sdfStream = new FileInputStream(path);
        DefaultIteratingChemObjectReader<IAtomContainer> reader = new IteratingSDFReader(sdfStream, builder);

        IAtomContainerSet set = new AtomContainerSet();
        while (reader.hasNext()) {
            IAtomContainer molecule = reader.next();
            set.addAtomContainer(molecule);
        }
        return set;
    }

    public static void writeMolsToMolfile(IAtomContainerSet mols, Writer out) throws IOException, CDKException {
        try (SDFWriter writer = new SDFWriter(out)) {
            writer.write(mols);
        }
    }
}
