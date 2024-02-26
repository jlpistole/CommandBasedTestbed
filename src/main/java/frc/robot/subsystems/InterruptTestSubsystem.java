package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

public class InterruptTestSubsystem extends SubsystemBase {
    
    @Override
    public void periodic() {

    }

    public Command runLongCommand() {
        Command thingToRun = Commands.waitSeconds(20);
        thingToRun.addRequirements(this);

        return thingToRun.andThen(() -> { System.out.println("Waited 20 seconds"); });
            //.until(interruptTrigger);
    }

    public Command runShortCommand() {
        return runOnce(() -> { System.out.println("ran a thing"); });
    }
}

    

