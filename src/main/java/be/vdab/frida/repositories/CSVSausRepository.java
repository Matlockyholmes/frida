package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.SausRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CSVSausRepository implements SausRepository{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String pad;

    public CSVSausRepository(@Value("${sauzencsv}")String pad){
        this.pad = pad;
    }

    @Override
    public List<Saus> findall() {
        try(BufferedReader br = new BufferedReader(new FileReader(pad))){
          return br.lines().filter(line -> ! line.isEmpty())
                    .map(line -> maakSaus(line))
                    .collect(Collectors.toList());
        } catch (IOException ex){
            String fout = "De connectie is niet geslaagd";
            logger.error(fout, ex);
            throw new SausRepositoryException(fout);
        }
    }

    private Saus maakSaus(String line){
        String[] lineArray = line.split(",");
        int nummer = Integer.parseInt(lineArray[0]);
        String naam = lineArray[1];
        String[] ingredienten = new String[lineArray.length-2];
        for(int i = 2; i < lineArray.length; i++){
            ingredienten[i-2] = lineArray[i];
        }
        return new Saus(nummer,naam,ingredienten);
    }
}
