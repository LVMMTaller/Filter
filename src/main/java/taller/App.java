package taller;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import taller.filters.RemoveDuplicatesCompounds;
import taller.filters.RemoveInorganicCompounds;
import taller.filters.RemoveMixtureCompounds;
import taller.filters.RemoveOrganometalicsCompounds;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        String path = "C:\\Users\\potter\\OneDrive - CICESE\\Documentos\\" +
                "Doctorado\\TALLER_UNAM\\CONJUNTOS\\remove\\remove.sdf";

        IAtomContainerSet molecules = IOMol.readSdf(path);

        Map<String, IAtomContainerSet> subsets = RemoveInorganicCompounds.clean(molecules);

        File folderContain = new File(path).getParentFile();

        IOMol.writeMolsToMolfile(subsets.get("organic"),new FileWriter(new File(folderContain,"just_organic.sdf")));
        IOMol.writeMolsToMolfile(subsets.get("inorganic"),new FileWriter(new File(folderContain,"inorganic.sdf")));

        subsets = RemoveMixtureCompounds.clean(subsets.get("organic"));

        IOMol.writeMolsToMolfile(subsets.get("complete"),new FileWriter(new File(folderContain,"without_mixture_and_organic.sdf")));
        IOMol.writeMolsToMolfile(subsets.get("mixture"),new FileWriter(new File(folderContain,"mixtures.sdf")));

        subsets = RemoveOrganometalicsCompounds.clean(subsets.get("complete"));

        IOMol.writeMolsToMolfile(subsets.get("complete"),new FileWriter(new File(folderContain,"without_mixture_organometalics_and_organic.sdf")));
        IOMol.writeMolsToMolfile(subsets.get("organometalics"),new FileWriter(new File(folderContain,"organometalics.sdf")));

        subsets = RemoveDuplicatesCompounds.clean(subsets.get("complete"));

        IOMol.writeMolsToMolfile(subsets.get("withoutDuplicated"),new FileWriter(new File(folderContain,"without_mixture_organometalics_duplicated_and_organic.sdf")));
        IOMol.writeMolsToMolfile(subsets.get("duplicated"),new FileWriter(new File(folderContain,"organometalics.sdf")));

    }
}
