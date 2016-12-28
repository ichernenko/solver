package loading.semanticNetwork;

import loading.dictionary.*;
import semanticAnalysis.SemanticObjectStructure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemanticNetwork {
    volatile private static boolean isCreatedSemanticNetwork = false;
    private static Connection connection;
    private static Map<Integer, SemanticObjectStructure> semanticNetwork;

    synchronized public static void create(Connection connection) throws SQLException {
        if (!isCreatedSemanticNetwork) {
            SemanticNetwork.connection = connection;

            createSemanticNetwork();

            isCreatedSemanticNetwork = true;
        }
    }

    // Метод создает Map со структурой семантической сети
    private static void createSemanticNetwork() throws SQLException {
        semanticNetwork = new HashMap<>(getWordCount());

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select word,lemma_id,part_of_speech,tag from dictionary where word_type_id=1 order by word")) {

            rs.next();
            String word = rs.getString("word");
            String oldWord = word;
            homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            while (rs.next()) {
                word = rs.getString("word");
                if (!word.equals(oldWord)) {
                    wordDictionary.put(oldWord, new Homonym(homonyms.toArray(new WordProperty[homonyms.size()])));
                    homonyms.clear();
                    oldWord = word;
                }
                homonyms.add(new WordProperty(rs.getInt("lemma_id"), rs.getString("part_of_speech"), rs.getString("tag")));
            }
            wordDictionary.put(word, new Homonym(homonyms.toArray(new WordProperty[homonyms.size()])));
        }
    }



    public SemanticObjectStructure getSemanticObjectStructure() {
        return null;
    }




}
