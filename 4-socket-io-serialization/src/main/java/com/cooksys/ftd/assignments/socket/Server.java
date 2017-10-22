package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Server extends Utils {
    private static ServerSocket ss;
    private static Config loadedConfig;
    
    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException, FileNotFoundException {
    	return (Student) jaxb.createUnmarshaller().unmarshal(new File(studentFilePath));
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    
    public static void init() throws JAXBException, FileNotFoundException, IOException {
    	loadedConfig = loadConfig("config/config.xml", createJAXBContext());
        ss = new ServerSocket(loadedConfig.getLocal().getPort());
    }
    
    public static void run(Config loadedConfig, ServerSocket ss) throws JAXBException, FileNotFoundException, IOException {
        System.out.println("Waiting of a connection...");
        
        Socket outgoingSocket = ss.accept();
        
        System.out.println("Connection Success!");

        DataOutputStream outStream = new DataOutputStream(outgoingSocket.getOutputStream());

        Student loadedStudent = loadStudent(loadedConfig.getStudentFilePath(), createJAXBContext());
        Marshaller marshaller = createJAXBContext().createMarshaller();
        
        marshaller.marshal(loadedStudent, outStream);
        
        ss.setSoTimeout(15000);
        outgoingSocket.close();
    	
    }
    public static void main(String[] args) throws JAXBException, FileNotFoundException, IOException {
    	init();
        run(loadedConfig, ss);
        boolean running = true;
        while (running) {
        	try {
        		run(loadedConfig, ss);
        	} catch (SocketTimeoutException e) {
        		System.out.println("Connection Timed Out!");
        		running = false;
        		ss.close();
        	}
        }
    }
}
