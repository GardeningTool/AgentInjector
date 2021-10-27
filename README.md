# AgentInjector
 A simple injector to dynamically attach a Java agent

Usage

```
java -jar AgentInjector.jar (PID) (agent file path)
```

Additionally, you can also execute this without the PID and agent file arguments in order to list the process IDs
and displaynames of all active virtual machines. From there, you'll be asked to provide the process ID as well
as the agent path.

# Requirements

Have attach.dll present in the directory the program is being executed from
