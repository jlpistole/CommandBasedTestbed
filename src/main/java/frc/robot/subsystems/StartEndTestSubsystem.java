package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Commands;

public class StartEndTestSubsystem extends SubsystemBase {
    boolean triFinished = false;

    @Override
    public void periodic() {

    }

    public Command runTriCommand() {
        triFinished = false;
        Command retval = new StartEndCommand(() -> { System.err.println("Triangle Command");}, 
        () -> { System.err.println("End Triangle Command");});
        retval.addRequirements(this);
       // retval = 
        return retval.until(this::isTriFinished).alongWith(Commands.waitSeconds(5).andThen(Commands.runOnce(() -> { System.err.println("Waiting done"); triFinished = true;})));
    }

    public Command runSquareCommand() {
        Command retval = new StartEndCommand(() -> {System.err.println("Square Command");},
        () -> { System.err.println("End Square Command");});
        retval.addRequirements(this);
        return retval;
      // return this.runOnce(() -> retval);
    }
/* 
    public Command setTriFinished() {
        Thread.sleep(5000);
        runOnce(() -> { triFinished = true; })
    }*/

    public boolean isTriFinished() {
        return triFinished;
    }
}
