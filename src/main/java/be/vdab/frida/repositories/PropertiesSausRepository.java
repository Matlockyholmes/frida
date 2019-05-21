package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Qualifier("PropertySauce")
public class PropertiesSausRepository implements SausRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String pad;

    public PropertiesSausRepository(@Value("${sauzenproperties}") String pad) {
        this.pad = pad;
    }

    @Override
    public List<Saus> findall() {
        try(BufferedReader br = new BufferedReader(new FileReader(pad))){
            return br.lines().filter(line -> ! line.isEmpty())
                    .map(line -> maakSaus(line))
                    .collect(Collectors.toList());
        } catch (IOException ex){
            String fout = "Kan properties file niet lezen";
            logger.error(fout, ex);
            throw new SausRepositoryException(fout);
        }
    }

    private Saus maakSaus(String line){
        try {
            String[] lineArr = line.split(":");
            int nummer = Integer.parseInt(lineArr[0]);
            String naam = lineArr[1].split(",")[0];
            String[] ingredienten = new String[lineArr[1].split(",").length-1];
            for(int i = 1; i < lineArr[1].split(",").length; i++){
                ingredienten[i-1] = lineArr[1].split(",")[i];
            }
            return new Saus(nummer,naam,ingredienten);
        } catch (NumberFormatException e) {
            String fout = "We hebben de saus niet kunnen aanmaken!";
            logger.error(fout, e);
        }
        return null;
    }
}
