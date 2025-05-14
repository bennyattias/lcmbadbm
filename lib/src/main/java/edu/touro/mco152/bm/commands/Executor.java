package edu.touro.mco152.bm.commands;

public class Executor {
    private Command command;

   public  void setCommand(Command command) {
        this.command = command;
    }

    public void runTest() {
        command.execute();
    }
}
