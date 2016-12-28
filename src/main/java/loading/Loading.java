package loading;

import loading.dictionary.*;
import loading.semanticNetwork.SemanticNetwork;

import java.sql.*;

public class Loading {
    public static void load() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db")) {
            Class.forName("org.sqlite.JDBC");

            loadDictionary(connection);
            loadSemanticNetwork(connection);

            System.out.println("Waiting for requests...");

        } catch (ClassNotFoundException e) {
            System.out.println("DB driver wasn't loaded!");
        } catch (SQLException e) {
            System.out.println("Loading has failed!");
        }
    }

    // Метод загружает словарь и показывает время загрузки
    private static void loadDictionary(Connection connection) throws SQLException {
        System.out.println("Dictionary is loading...");
        long startTime = System.currentTimeMillis();

        Dictionary.create(connection);

        long finishTime = System.currentTimeMillis();
        System.out.println("Loading time: " + String.format("%,5d", (finishTime - startTime) / 1000) + " s");
        System.out.println("Dictionary is loaded!");
    }


    // Метод загружает семантическую сеть и показывает время загрузки
    private static void loadSemanticNetwork(Connection connection) throws SQLException {
        System.out.println("Semantic network is loading...");
        long startTime = System.currentTimeMillis();

        SemanticNetwork.create(connection);

        long finishTime = System.currentTimeMillis();
        System.out.println("Loading time: " + String.format("%,5d", (finishTime - startTime) / 1000) + " s");
        System.out.println("Semantic network is loaded!");
    }
}
