package ru.alexeyk2021.dbweb.managers;


import ru.alexeyk2021.dbweb.models.*;
import ru.alexeyk2021.dbweb.transfer.AddFund;
import ru.alexeyk2021.dbweb.transfer.CreateClient;
import ru.alexeyk2021.dbweb.transfer.EditingClient;

import java.sql.*;
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

    public void newClient(CreateClient client) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("SELECT new_client(?,?,?,?,?,?) AS clientId");
            statement.setString(1, client.getFullName());
            statement.setString(2, client.getPassport());
            statement.setString(3, client.getLogin());
            statement.setString(4, client.getPassword());
            statement.setString(5, client.getPhoneNumber());
            statement.setInt(6, client.getTariffId());

            ResultSet cid = statement.executeQuery();
            int clientId = -1;
            while (cid.next()) {
                clientId = cid.getInt("clientId");
            }

            for (int add : client.getAddsIds()) {
                statement = conn.prepareStatement("SELECT create_client_add_service(?,?)");
                statement.setInt(1, add);
                statement.setInt(2, clientId);
                statement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateClient(EditingClient client){
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("UPDATE client SET balance = ?, phone_number = ?, account_state = ?, tariff_id = ? WHERE client_id = ?;");
            statement.setDouble(1, client.getBalance());
            statement.setString(2, client.getPhoneNumber());
            statement.setBoolean(3, client.getAccountState());
            statement.setInt(4, client.getTariffId());
            statement.setInt(5, client.getClientId());
            statement.executeUpdate();

            statement = conn.prepareStatement("UPDATE personal_info SET full_name=?, passport_data=?, login=?, password=? WHERE client_id=?;");
            statement.setString(1, client.getFullName());
            statement.setString(2, client.getPassport());
            statement.setString(3, client.getLogin());
            statement.setString(4, client.getPassword());
            statement.setInt(5, client.getClientId());
            statement.executeUpdate();

            statement = conn.prepareStatement("DELETE FROM client_add_service WHERE client_id = ?;");
            statement.setInt(1, client.getClientId());
            statement.execute();

            for (int add : client.getAddsIds()) {
                statement = conn.prepareStatement("SELECT create_client_add_service(?,?)");
                statement.setInt(1, add);
                statement.setInt(2, client.getClientId());
                statement.executeQuery();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClient(int clientId){
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM personal_info WHERE client_id = ?;");
            statement.setInt(1, clientId);
            statement.execute();

            statement = conn.prepareStatement("DELETE FROM client_add_service WHERE client_id = ?;");
            statement.setInt(1, clientId);
            statement.execute();

            statement = conn.prepareStatement("DELETE FROM client WHERE client_id = ?;");
            statement.setInt(1, clientId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getFreePhoneNumbers() {
        ArrayList<String> phones = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("CALL get5numbers;");
            ResultSet phone = statement.executeQuery();
            while (phone.next()) {
                phones.add(phone.getString("phone_number"));
            }
            return phones;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void newTariff(Tariff tariff) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO tariff(name, cost, description, internet_size, minutes_size, sms_size) VALUE (?,?,?,?,?,?);");
            statement.setString(1, tariff.getName());
            statement.setDouble(2, tariff.getCost());
            statement.setString(3, tariff.getDescription());
            statement.setDouble(4, tariff.getInternetSize());
            statement.setInt(5, tariff.getMinutesSize());
            statement.setInt(6, tariff.getSmsSize());
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTariff(Tariff tariff) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("UPDATE tariff SET name=?, cost=?, description=?, internet_size=?, minutes_size=?, sms_size=? WHERE tariff_id=?;");
            statement.setString(1, tariff.getName());
            statement.setDouble(2, tariff.getCost());
            statement.setString(3, tariff.getDescription());
            statement.setDouble(4, tariff.getInternetSize());
            statement.setInt(5, tariff.getMinutesSize());
            statement.setInt(6, tariff.getSmsSize());
            statement.setInt(7, tariff.getTariffId());
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteTariff(int tariffId){
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM tariff WHERE tariff_id = ?;");
            statement.setInt(1, tariffId);
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void newAddService(AddService add) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO add_service(name, cost, description, internet_size, minutes_size, sms_size) VALUE (?,?,?,?,?,?);");
            statement.setString(1, add.getName());
            statement.setDouble(2, add.getCost());
            statement.setString(3, add.getDescription());
            statement.setDouble(4, add.getInternetSize());
            statement.setInt(5, add.getMinutesSize());
            statement.setInt(6, add.getSmsSize());
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAddService(AddService add) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("UPDATE add_service SET name=?, cost=?, description=?, internet_size=?, minutes_size=?, sms_size=? WHERE add_service_id=?;");
            statement.setString(1, add.getName());
            statement.setDouble(2, add.getCost());
            statement.setString(3, add.getDescription());
            statement.setDouble(4, add.getInternetSize());
            statement.setInt(5, add.getMinutesSize());
            statement.setInt(6, add.getSmsSize());
            statement.setInt(7, add.getAddServiceId());
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAddService(int id) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM add_service WHERE add_service_id = ?;");
            statement.setInt(1, id);
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFunds(double addFund, int clientId) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = conn.prepareStatement("UPDATE client SET balance=? WHERE client_id=?;");
            statement.setDouble(1, addFund);
            statement.setInt(2, clientId);
            boolean status = statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
