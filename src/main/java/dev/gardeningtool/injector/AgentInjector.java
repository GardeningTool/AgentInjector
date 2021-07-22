package dev.gardeningtool.injector;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.util.Scanner;

public class AgentInjector {

    public AgentInjector(String[] args) {
        loadAttachDll();
        String processId;
        String attachFile;

        if (args.length != 2) {
            VirtualMachine.list().forEach(vm -> {
                System.out.println("Found VM on PID " + vm.id());
                System.out.println("VM displayname: " + vm.displayName());
            });
            Scanner scanner = new Scanner(System.in);
            System.out.println("Target VM PID: ");
            processId = scanner.next();
            System.out.println("Target agent file: ");
            attachFile = scanner.next();
        } else {
            processId = args[0];
            attachFile = args[1];
        }

        try {
            attach(attachFile, processId);
        } catch (IOException | AttachNotSupportedException | AgentLoadException | AgentInitializationException exc) {
            System.out.println("An unexpected error occured while attempting to load the target agent!");
            exc.printStackTrace();
        }
    }

    private void loadAttachDll() {
        System.setProperty("java.library.path", System.getProperty("user.dir"));
        System.loadLibrary("attach");
    }

    private void attach(String fileName, String processId) throws IOException, AttachNotSupportedException,
            AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(processId);
        virtualMachine.loadAgent(fileName);
        virtualMachine.detach();
    }

    public static void main(String[] args) {
        new AgentInjector(args);
    }
}
