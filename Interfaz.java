import java.rmi.Remote;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.net.*;
import java.util.*;

public interface Interfaz extends Remote {
    void request(int id, int req) throws RemoteException;
    Token waitToken(int id) throws RemoteException;
    void takeToken(Token toquen) throws RemoteException;
    //void kill() throws RemoteException;
}
