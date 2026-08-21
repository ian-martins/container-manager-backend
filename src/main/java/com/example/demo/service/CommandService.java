package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.config.DockerHost;
import com.example.demo.constants.DockerComands;
import com.example.demo.model.Object_Container;
import com.example.demo.model.Object_Image;
import com.example.demo.model.commands.Command_Run;

@Service
public class CommandService extends DockerComands {

    private final ConnectionService connectionService;

    public CommandService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    private BufferedReader make(List<String> dockerCommand) throws IOException {
        DockerHost dockerHost = getRequiredConnection();

        List<String> command = new ArrayList<>();

        if(!dockerHost.isWslLocal()) {
            command.add("WSL");
        }
        command.addAll(dockerCommand);

        String HOST = "tcp://" + dockerHost.getHost() + ":" + dockerHost.getPort();
        ProcessBuilder pb = new ProcessBuilder(command);
        //$env:DOCKER_HOST="tcp://10.211.0.31:2375"   tcp://10.211.0.31:2375


        //Variáveis de ambiente
        pb.environment().put("DOCKER_HOST", HOST);
        // Junta stderr com stdout (opcional)
        //pb.redirectErrorStream(true);

        Process process = pb.start();
        return new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public DockerHost getRequiredConnection() {
        DockerHost dockerHost = connectionService.getActiveConnection();
        if (dockerHost == null) {
            throw new IllegalStateException(
                    "Nenhuma conexão Docker está ativa"
            );
        }
        return dockerHost;
    }

    public boolean container(String ID) {
        try {

            BufferedReader reader = make(List.of(DOCKER, PS, QUIET));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(ID)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * Retorna um objeto Object_Container com o ID informado, podendo retornar
     * um objeto Object_Container vazio
     *
     * @param ID
     * @param Name
     * @return Object_Container
     */
    public Optional<Object_Container> container(String ID, String Name) {
        try {
            List<String> command = new ArrayList<>();
            command.add(DOCKER);
            command.add(PS);
            command.add(FORMAT);
            if (getRequiredConnection().isWslLocal()) {
                command.add(FORMATO_CONTAINER_2);
            } else {
                command.add(FORMATO_CONTAINER_1);
            }
            BufferedReader reader = make(command);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(";");
                if (lines[0].equals(ID) || lines[2].equals(Name)) {
                    return Optional.of(new Object_Container(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5]));
                }
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }

    /**
     * Retorna uma Lista do objeto Containers, podendo retornar uma Lista do
     * objeto Containers vazia
     *
     * @return Optional List Object_Container
     */
    public Optional<List<Object_Container>> containers(boolean all) {
        try {
            List<Object_Container> cs = new ArrayList<>();
            List<String> command = new ArrayList<>();

            command.add(DOCKER);
            command.add(PS);
            command.add(FORMAT);
            if (getRequiredConnection().isWslLocal()) {
                command.add(FORMATO_CONTAINER_2);
            } else {
                command.add(FORMATO_CONTAINER_1);
            }

            if (all) {
                command.add(ALL);
            }

            BufferedReader reader = make(command);
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                i++;
                String[] lines = line.split(";");
                cs.add(new Object_Container(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5]));
            }
            System.out.println(i + " containers carregados");

            return Optional.ofNullable(cs);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Retorna um objeto Object_Image com o ID informado, podendo retornar um
     * objeto Object_Image vazio
     *
     * @param ID
     * @return
     */
    public Optional<Object_Image> image(String ID) {
        try {
            String line;
            BufferedReader reader = make(List.of(DOCKER, IMAGES, FORMAT, FORMATO_IMAGE_1));
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(";");
                if (lines[4].equals(ID)) {
                    return Optional.ofNullable(new Object_Image(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8], lines[9]));
                }
            }
        } catch (IOException e) {
        }
        return Optional.empty();
    }

    /**
     * Retorna uma Lista do objeto Containers, podendo retornar uma Lista do
     * objeto Containers vazia
     *
     * @return List Object_Image
     */
    public Optional<List<Object_Image>> images() {
        try {
            List<Object_Image> images = new ArrayList<>();
            String line;
            BufferedReader reader = make(List.of(DOCKER, IMAGES, FORMAT, FORMATO_IMAGE_1));
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(";");
                images.add(new Object_Image(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8], lines[9]));
            }
            return Optional.ofNullable(images);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Iniciador de Containers
     *
     * @param c Command_Run
     */
    public void run(Command_Run c) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(RUN);

        // booleanos
        if (c.isDetached()) {
            command.add(DETACHED);
        }
        if (c.isInteractive()) {
            command.add(INTERACTIVE);
        }
        if (c.isRemove()) {
            command.add(REMOVE);
        }
        if (c.isTty()) {
            command.add(TERMINAL);
        }

        // Valores Unicos
        if (!c.getName().isEmpty()) {
            command.add(NAME);
            command.add(c.getName());
        }
        if (!c.getCpus().isEmpty()) {
            command.add(CPUS);
            command.add(c.getCpus());
        }
        if (!c.getMemory().isEmpty()) {
            command.add(MEMORY);
            command.add(c.getMemory());
        }
        if (!c.getTimeout().isEmpty()) {
            command.add(TIMEOUT);
            command.add(c.getTimeout());
        }
        if (!c.getSignal().isEmpty()) {
            command.add(SIGNAL);
            command.add(c.getSignal());
        }

        // Valores em lista
        for (String env : c.getEnvironments()) {
            command.add(ENVIRONMENT);
            command.add(env);
        }
        for (String ports : c.getPorts()) {
            command.add(PUBLISH);
            command.add(ports);
        }
        command.add(c.getImage());
        try {
            make(command);
        } catch (IOException ex) {
        }

    }

    public boolean stop(String id) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(STOP);
        command.add(id);
        try {
            String line;
            BufferedReader reader = make(command);
            System.out.println("Comando " + command + " Executado!");
            while ((line = reader.readLine()) != null) {
                System.out.println("ID do Container: " + line);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean remove(String id) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(RM);
        command.add(id);
        try {
            String line;
            BufferedReader reader = make(command);
            System.out.println("Comando " + command + " Executado!");

            while ((line = reader.readLine()) != null) {
                System.out.println("ID do Container: " + line);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean start(String id) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(START);
        command.add(id);
        try {
            String line;
            BufferedReader reader = make(command);
            System.out.println("Comando " + command + " Executado!");

            while ((line = reader.readLine()) != null) {
                System.out.println("ID do Container: " + line);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}
