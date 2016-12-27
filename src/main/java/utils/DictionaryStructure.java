package utils;

import java.io.IOException;
import java.sql.*;

class DictionaryStructure {
    private static final String selectSql = "select lemma_id,group_id,word,tag from dictionary order by group_id";
//    private static final String insertSql = "insert into dictionary_new(id,lemma_id,group_id,word,part_of_speech,part_of_speech_id,tag,prefix,tail,base_id,base_group_id,word_type_id,used_rarely,group_num,shared_word,shared_tag) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String insertSql = "insert into dic_word_groups(lemma_id,group_id,group_word,group_tag) values (?,?,?,?)";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseSite() throws IOException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement selectPS = con.prepareStatement(selectSql);
             PreparedStatement insertPS = con.prepareStatement(insertSql);
             ResultSet rs = selectPS.executeQuery()) {

            con.setAutoCommit(false);

            rs.next();
            int prevLemmaId = rs.getInt("lemma_id");
            int prevGroupId = rs.getInt("group_id");
            String groupWord = rs.getString("word");
            String groupTag = rs.getString("tag");

            int count = 0;

            while(rs.next()) {

                int lemmaId = rs.getInt("lemma_id");
                int groupId = rs.getInt("group_id");
                String word = rs.getString("word");
                String tag = rs.getString("tag");

                if(groupId == prevGroupId) {
                    groupWord = getGroupPart(groupWord, word).trim();
                    groupTag = getGroupPart(groupTag, tag).trim();

                } else {
                    System.out.println(prevGroupId + ". слово: " + groupWord + "     тег: " + groupTag);
                    insert(insertPS, prevLemmaId, prevGroupId, groupWord, groupTag);

                    count ++;

                    prevLemmaId = lemmaId;
                    prevGroupId = groupId;
                    groupWord = word;
                    groupTag = tag;
                }

//                insertRow(id,lemmaId,groupId,word,partOfSpeech,partOfSpeechId,tag,prefix,tail,baseId,baseGroupId,wordTypeId,usedRarely,groupNum,sharedWord,sharedTag);

                if (count == 100000) {
                    insertPS.executeBatch();
                    con.commit();
                    count = 0;
                }
            }

            System.out.println(prevGroupId + ". слово: " + groupWord + "     тег: " + groupTag);
            insert(insertPS, prevLemmaId, prevGroupId, groupWord, groupTag);

            insertPS.executeBatch();
            con.commit();

            } catch (SQLException e) {
            System.out.println("Processing wasn't finished!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getGroupPart(String word1, String word2) {
        if (word1 == null) {
            if (word2 == null) {
                return null;
            } else {
                return word2;
            }
        } else {
            if (word2 == null) {
                return word1;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < word1.length() && i < word2.length(); i ++) {
            char ch = word1.charAt(i);
            if (ch == word2.charAt(i)) {
                sb.append(ch);
            } else {
                break;
            }
        }

        return sb.toString();
    }

    private void insert(PreparedStatement ps, int lemmaId, int groupId, String groupWord, String groupTag) throws SQLException {
        ps.setInt(1, lemmaId);
        ps.setInt(2, groupId);
        ps.setString(3, groupWord);
        ps.setString(4, groupTag);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        DictionaryStructure structureDictionary = new DictionaryStructure();
        structureDictionary.loadDBDriver();
        structureDictionary.parseSite();
    }
}