package ru.ifmo.rmi;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;
import java.sql.*;

public class ConfServerImpl extends UnicastRemoteObject implements ConfServer {

    public ConfServerImpl() throws RemoteException {
        super();
    }

    public int registerConfParticipant (RegistrationInfo registrationInfo) throws RemoteException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine");
            PreparedStatement st = con.prepareStatement("insert into registra-tion_info "
                    + "(first_name, last_name, organization, "
                    + "report_theme, email) "
                    + "values (?, ?, ?, ?, ?)");

            st.setString(1, registrationInfo.getFirstName());
            st.setString(2, registrationInfo.getLastName());
            st.setString(3, registrationInfo.getOrganization());
            st.setString(4, registrationInfo.getReportTheme());
            st.setString(5, registrationInfo.getEmail());
            st.executeUpdate();
            st.close();

            Statement st1 = con.createStatement();
            int count = 0;
            ResultSet rs = st1.executeQuery("Select count(*) from reg-istration_info");

            if(rs.next()) {
                count = rs.getInt(1);
            }
            st1.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public static void main (String args[]) {
        try{
            System.setProperty("java.rmi.server.codebase","file:///F:/Eclipse/workspace/Rmi/bin/");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager() {
                    public void checkConnect(String host, int port,Object context) {

                    }public void checkConnect(String host, int port) {

                    }public void checkPermission(Permission perm) {

                    }});
            }
            ConfServerImpl instance = new ConfServerImpl();
            Naming.rebind("ConfServer", instance);

            System.out.println("Сервис зарегистрирован");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
