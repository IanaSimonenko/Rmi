package ru.ifmo.rmi;
import java.rmi.*;

public interface ConfServer extends Remote {

    int registerConfParticipant(RegistrationInfo registrationInfo) throws RemoteException;

}