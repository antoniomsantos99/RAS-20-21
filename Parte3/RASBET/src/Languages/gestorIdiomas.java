package Languages;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class gestorIdiomas
{
    private String currentLingua = "pt";
    private HashMap<String,HashMap<String,String>> langMap;

    public gestorIdiomas() throws IOException {
        File langfile = new File("Parte3\\RASBET\\src\\Languages\\lang.txt");
        FileReader fr=new FileReader(langfile);
        BufferedReader br=new BufferedReader(fr);
        String line,current = "pt";
        String[] keyvalue;

        this.langMap = new HashMap<>();

        while((line=br.readLine())!=null){
            if(line.length() != 0)
                if(line.startsWith("-")){
                    current = line.substring(1);
                    this.langMap.put(current,new HashMap<String,String>());
                }
                else{
                    keyvalue = line.split(" ",2);
                    this.langMap.get(current).put(keyvalue[0],keyvalue[1]);
                }


        }
    }

    public String[] getIdiomasDisponiveis(){
        return this.langMap.keySet().toArray(new String[0]);
    }

    public void setIdioma(String idioma){
        this.currentLingua = idioma;
    }

    public String getTexto(String key){
        return this.langMap.get(this.currentLingua).get(key);
    }
}
