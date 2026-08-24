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
import com.example.demo.entity.Container;
import com.example.demo.entity.Image;
import com.example.demo.entity.commands.Command_Run;

@Service
public class CommandService extends DockerComands {

    private BufferedReader make(List<String> dockerCommand, DockerHost dockerHost) throws IOException {
        List<String> command = new ArrayList<>();

        if (!dockerHost.isWslLocal()) {
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

    public boolean container(String ID, DockerHost dockerHost) {
        try {

            BufferedReader reader = make(List.of(DOCKER, PS, QUIET), dockerHost);

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
    public Optional<Container> container(String ID, String Name, DockerHost dockerHost) {
        try {
            List<String> command = new ArrayList<>();
            command.add(DOCKER);
            command.add(PS);
            command.add(FORMAT);
            if (dockerHost.isWslLocal()) {
                command.add(FORMATO_CONTAINER_2);
            } else {
                command.add(FORMATO_CONTAINER_1);
            }
            BufferedReader reader = make(command, dockerHost);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(";");
                if (lines[0].equals(ID) || lines[2].equals(Name)) {
                    return Optional.of(new Container(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5]));
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
    public Optional<List<Container>> containers(boolean all, DockerHost dockerHost) {
        try {
            List<Container> cs = new ArrayList<>();
            List<String> command = new ArrayList<>();

            command.add(DOCKER);
            command.add(PS);
            command.add(FORMAT);
            if (dockerHost.isWslLocal()) {
                command.add(FORMATO_CONTAINER_2);
            } else {
                command.add(FORMATO_CONTAINER_1);
            }

            if (all) {
                command.add(ALL);
            }

            BufferedReader reader = make(command, dockerHost);
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                i++;
                String[] lines = line.split(";");
                cs.add(new Container(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5]));
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
    public Optional<Image> image(String ID, DockerHost dockerHost) {
        try {
            List<String> command = new ArrayList<>();

            command.add(DOCKER);
            command.add(IMAGES);
            command.add(FORMAT);
            if (dockerHost.isWslLocal()) {
                command.add(FORMATO_IMAGE_2);
            } else {
                command.add(FORMATO_IMAGE_1);
            }
            BufferedReader reader = make(command, dockerHost);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(";");
                if (lines[4].equals(ID)) {
                    return Optional.ofNullable(new Image(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8], lines[9]));
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
    public Optional<List<Image>> images(DockerHost dockerHost) {
        try {
            List<Image> images = new ArrayList<>();
            List<String> command = new ArrayList<>();

            command.add(DOCKER);
            command.add(IMAGES);
            command.add(FORMAT);
            if (dockerHost.isWslLocal()) {
                command.add(FORMATO_IMAGE_2);
            } else {
                command.add(FORMATO_IMAGE_1);
            }
            String line;
            BufferedReader reader = make(command, dockerHost);
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(";");
                images.add(new Image(lines[0], lines[1], lines[2], lines[3], lines[4], lines[5], lines[6], lines[7], lines[8], lines[9]));
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
    public void run(Command_Run c, DockerHost dockerHost) {
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
            make(command, dockerHost);
        } catch (IOException ex) {
        }

    }

    public boolean stop(String id, DockerHost dockerHost) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(STOP);
        command.add(id);
        try {
            String line;
            BufferedReader reader = make(command, dockerHost);
            System.out.println("Comando " + command + " Executado!");
            while ((line = reader.readLine()) != null) {
                System.out.println("ID do Container: " + line);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean remove(String id, DockerHost dockerHost) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(RM);
        command.add(id);
        try {
            String line;
            BufferedReader reader = make(command, dockerHost);
            System.out.println("Comando " + command + " Executado!");

            while ((line = reader.readLine()) != null) {
                System.out.println("ID do Container: " + line);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean start(String id, DockerHost dockerHost) {
        List<String> command = new ArrayList<>();
        command.add(DOCKER);
        command.add(START);
        command.add(id);
        try {
            String line;
            BufferedReader reader = make(command, dockerHost);
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
