package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.Utils;
import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Client extends Utils {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) throws JAXBException, FileNotFoundException, IOException {

        Config loadedConfig = loadConfig("config/config.xml", createJAXBContext());
        
        RemoteConfig remote = loadedConfig.getRemote();
        
        Socket s = new Socket(remote.getHost(), remote.getPort());

    	DataInputStream dis = new DataInputStream(s.getInputStream());
    	
    	JAXBContext studentJaxb = JAXBContext.newInstance(Student.class);
        Unmarshaller unmarshaller = studentJaxb.createUnmarshaller();

        Student student = (Student) unmarshaller.unmarshal(dis);
        
        System.out.println(student.toString());
        
        s.close();
        
    }
}
