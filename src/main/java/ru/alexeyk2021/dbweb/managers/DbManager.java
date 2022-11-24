package ru.alexeyk2021.dbweb.managers;


import ru.alexeyk2021.dbweb.DbwebApplication;
import ru.alexeyk2021.dbweb.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DbManager {
    private final String dbUrl = "194.87.239.99"; //194.87.239.99 - 127.0.0.1
    private final String dbPort = "3306"; //3306 - 3305
    private final String dbUser = "alexey";
    private final String dbPassword = "Alexey2002";
    private final String DbName = "mireaDB";
    private static DbManager instance;

    private final String connectionString;

    private DbManager() {
        connectionString = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + DbName + "?user=" + dbUser + "&password=" + dbPassword;
    }

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

//    public void connect() throws SQLException {


//            ResultSet resultSet = statement.executeQuery("SELECT * FROM client;");
//            ArrayList<Client> clients = new ArrayList<>();
//            while (resultSet.next()) {
//                clients.add(new Client(resultSet));
//            }
//            for (Client client : clients) {
//                System.out.println(client.toString());
//            }


//List<User> users=new ArrayList<User>();
//
//while(rs.next()) {
//   User user = new User();
//   user.setUserId(rs.getString("UserId"));
//   user.setFName(rs.getString("FirstName"));
//  ...
//  ...
//  ...
//
//
//  users.add(user);
//}

//        } catch (SQLException e) {
//            // handle any errors
//            System.out.println("SQLException: " + e.getMessage());
//            System.out.println("SQLState: " + e.getSQLState());
//            System.out.println("VendorError: " + e.getErrorCode());
//        } catch (Exception e) {
//            System.out.println("EXCEPTION " + e.getMessage());
//        }
//        }
//    }

//    public ArrayList<ClientPersonalInfo> getAllClientsData() {
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + DbName + "?user=" + user + "&password=" + password)) {
//            Statement statement = conn.createStatement();
//
//            statement.executeUpdate("USE test_mirea_db;");
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM personal_info;");
//            System.out.println(resultSet.toString());
//            ArrayList<ClientPersonalInfo> personalInfos = new ArrayList<>();
//            while (resultSet.next()) {
//                personalInfos.add(
//                        new ClientPersonalInfo(
//                                clientId, resultSet.getString("full_name"),
//                                resultSet.getString("passport_data"),
//                                resultSet.getString("login"),
//                                resultSet.getString("password")
//                        )
//                );
//            }
//            return personalInfos;
//        } catch (SQLException e) {
//            // handle any errors
//            System.out.println("SQLException: " + e.getMessage());
//            System.out.println("SQLState: " + e.getSQLState());
//            System.out.println("VendorError: " + e.getErrorCode());
//        } catch (Exception e) {
//            System.out.println("EXCEPTION " + e.getMessage());
//        }
//        return null;
//    }

    public Client approveEnter(String login, String password) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("call approveEnter(?, ?);");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            return findByPhoneNumber(resultSet.getString(1));

        } catch (Exception e) {
            System.out.println("ERROR:" + e.getMessage());
        }
        return null;
    }

    public ArrayList<String> findByPartNumber(String phone_number) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("call getLikeNumbersList(?);");
            statement.setString(1, "%" + phone_number + "%");
            ResultSet numbers = statement.executeQuery();

            ArrayList<String> numberList = new ArrayList<>();
            while (numbers.next()) {
                numberList.add(numbers.getString("phone_number"));
            }
            return numberList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Client findByPhoneNumber(String phone_number) { // не назначается тариф и имя
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("call selectClientByNumber(?);");
            statement.setString(1, phone_number);
            ResultSet user = statement.executeQuery();
            user.next(); ///????
            Client client = new Client(user);

            statement = conn.prepareStatement("call selectPersonalInfoByNumber(?);");
            statement.setString(1, phone_number);
            ResultSet personal_info = statement.executeQuery();
            personal_info.next();
            client.setPersonalInfo(new ClientPersonalInfo(personal_info));

            statement = conn.prepareStatement("call selectTariffByNumber(?);");
            statement.setString(1, phone_number);
            ResultSet client_tariff = statement.executeQuery();
            client_tariff.next(); ///????
            client.setTariff(new Tariff(client_tariff));

            statement = conn.prepareStatement("call selectAddServicesByNumber(?);");
            statement.setString(1, phone_number);
            ResultSet addServices = statement.executeQuery();
            ArrayList<AddService> adds = new ArrayList<>();

            while (addServices.next()) {
                adds.add(new AddService(addServices));
            }
            client.setAddServiceList(adds);

            statement = conn.prepareStatement("call selectReportsByNumber(?);");
            statement.setString(1, phone_number);
            ResultSet reports = statement.executeQuery();

            ArrayList<Report> reps = new ArrayList<>();
            while (reports.next()) {
                Report report = new Report(reports);
                report.setCategory(new Category(reports));
                reps.add(report);
            }
            client.setReportList(reps);

            return client;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Tariff> getAllTariffs() {
        ArrayList<Tariff> tariff = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM tariff;");
            ResultSet tariffs = statement.executeQuery();
            while (tariffs.next()) tariff.add(new Tariff(tariffs));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tariff;
    }

    public ArrayList<Client> getAllClients() {
        ArrayList<Client> client = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT phone_number FROM client;");
            ResultSet clients = statement.executeQuery();
            while (clients.next()) {
                client.add(findByPhoneNumber(clients.getString("phone_number")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public ArrayList<AddService> getAllAdds() {
        ArrayList<AddService> add = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM add_service;");
            ResultSet adds = statement.executeQuery();
            while (adds.next()) add.add(new AddService(adds));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return add;
    }

    ////////////////////////////////////////////////////
    //TODO getPopularTariffs and getPopularAdds       //
    ////////////////////////////////////////////////////
    public ArrayList<String> getPopularTariffs() {
        ArrayList<String> popularTariffs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("call popularTariffs();");
            ResultSet tariffs = statement.executeQuery();
            while (tariffs.next()) {
                popularTariffs.add(tariffs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return popularTariffs;
    }

    public ArrayList<String> getPopularAdds() {
        ArrayList<String> popularAdds = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("call popularAdds();");
            ResultSet adds = statement.executeQuery();
            while (adds.next()) {
                popularAdds.add(adds.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return popularAdds;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //TODO getActiveClientsCount, getClientsCount, getTariffsCount, getAddsCount  //
    ////////////////////////////////////////////////////////////////////////////////

    public int getActiveClientsCount() {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) as count FROM client WHERE client.account_state = true;");
            ResultSet clients = statement.executeQuery();
            clients.next();
            return clients.getInt("count");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getClientsCount() {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) as count FROM client;");
            ResultSet clients = statement.executeQuery();
            clients.next();
            return clients.getInt("count");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getTariffsCount() {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) as count FROM tariff;");
            ResultSet clients = statement.executeQuery();
            clients.next();
            return clients.getInt("count");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getAddsCount() {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) as count FROM add_service;");
            ResultSet clients = statement.executeQuery();
            clients.next();
            return clients.getInt("count");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
